package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote

import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.error.AppException
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.api.QuizApi
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.CategoryDto
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.CategoryResponseDto
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.TriviaQuestionDto
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.TriviaResponseDto
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class QuizRemoteDataSourceImplTest {

    // region fetchCategories — happy path & generic failure

    @Test
    fun fetchCategories_returnsTriviaCategoriesUnchanged() = runTest {
        val categories = listOf(CategoryDto(id = 9, category = "General Knowledge"))
        val api = FakeQuizApi(categoriesResult = { CategoryResponseDto(triviaCategories = categories) })
        val dataSource = QuizRemoteDataSourceImpl(api)

        val result = dataSource.fetchCategories()

        assertEquals(categories, result)
    }

    @Test
    fun fetchCategories_apiThrows_wrapsInNetworkUnavailable() = runTest {
        val api = FakeQuizApi(categoriesResult = { throw RuntimeException("boom") })
        val dataSource = QuizRemoteDataSourceImpl(api)

        val exception = assertFailsWith<AppException> { dataSource.fetchCategories() }

        assertEquals(AppError.Network.Unavailable, exception.error)
    }

    @Test
    fun fetchCategories_apiThrowsCancellation_propagatesUnwrapped() = runTest {
        val api = FakeQuizApi(categoriesResult = { throw CancellationException("cancelled") })
        val dataSource = QuizRemoteDataSourceImpl(api)

        // Must be the real CancellationException, not wrapped in AppException — this is
        // what the dedicated catch block above the generic one exists to guarantee.
        assertFailsWith<CancellationException> { dataSource.fetchCategories() }
    }

    // endregion

    // region fetchQuestions — happy path & param passthrough

    @Test
    fun fetchQuestions_successResponseCode_returnsResults() = runTest {
        val questions = listOf(sampleQuestionDto())
        val api = FakeQuizApi(
            questionsResult = { _, _, _ -> TriviaResponseDto(responseCode = 0, results = questions) },
        )
        val dataSource = QuizRemoteDataSourceImpl(api)

        val result = dataSource.fetchQuestions(amount = 10, categoryId = null, difficulty = null)

        assertEquals(questions, result)
    }

    @Test
    fun fetchQuestions_passesParamsThroughToApi() = runTest {
        var capturedAmount: Int? = null
        var capturedCategoryId: Int? = null
        var capturedDifficulty: String? = null
        val api = FakeQuizApi(
            questionsResult = { amount, categoryId, difficulty ->
                capturedAmount = amount
                capturedCategoryId = categoryId
                capturedDifficulty = difficulty
                TriviaResponseDto(responseCode = 0, results = emptyList())
            },
        )
        val dataSource = QuizRemoteDataSourceImpl(api)

        dataSource.fetchQuestions(amount = 15, categoryId = 9, difficulty = "hard")

        assertEquals(15, capturedAmount)
        assertEquals(9, capturedCategoryId)
        assertEquals("hard", capturedDifficulty)
    }

    // endregion

    // region fetchQuestions — response-code error mapping (per real toAppError values)

    @Test
    fun fetchQuestions_responseCodeOne_throwsNoResults() = runTest {
        val exception = fetchQuestionsWithResponseCode(1)

        assertEquals(AppError.Trivia.NoResults, exception.error)
    }

    @Test
    fun fetchQuestions_responseCodeTwo_throwsInvalidParameter() = runTest {
        val exception = fetchQuestionsWithResponseCode(2)

        assertEquals(AppError.Trivia.InvalidParameter, exception.error)
    }

    @Test
    fun fetchQuestions_responseCodeFive_throwsRateLimit() = runTest {
        val exception = fetchQuestionsWithResponseCode(5)

        assertEquals(AppError.Trivia.RateLimit, exception.error)
    }

    @Test
    fun fetchQuestions_unmappedResponseCode_fallsBackToNetworkHttpWithCode() = runTest {
        // 3 and 4 (session-token codes) and anything else fall through toAppError's
        // else branch. Documenting that this data source doesn't special-case them —
        // relevant since this API integration doesn't send a session token at all,
        // so 3/4 should never actually occur, but the fallback still needs a defined shape.
        val exception = fetchQuestionsWithResponseCode(4)

        assertEquals(AppError.Network.Http(4), exception.error)
    }

    @Test
    fun fetchQuestions_appExceptionFromResponseCodeCheck_propagatesUnwrapped() = runTest {
        // Guards against a regression where reordering catch blocks causes the AppException
        // thrown for a bad response code to get re-wrapped as Network.Unavailable.
        val exception = fetchQuestionsWithResponseCode(2)

        assertEquals(AppError.Trivia.InvalidParameter, exception.error)
    }

    private suspend fun fetchQuestionsWithResponseCode(code: Int): AppException {
        val api = FakeQuizApi(
            questionsResult = { _, _, _ -> TriviaResponseDto(responseCode = code, results = emptyList()) },
        )
        val dataSource = QuizRemoteDataSourceImpl(api)

        return assertFailsWith<AppException> {
            dataSource.fetchQuestions(amount = 10, categoryId = null, difficulty = null)
        }
    }

    // endregion

    // region fetchQuestions — generic failure & cancellation

    @Test
    fun fetchQuestions_apiThrows_wrapsInNetworkUnavailable() = runTest {
        val api = FakeQuizApi(questionsResult = { _, _, _ -> throw RuntimeException("boom") })
        val dataSource = QuizRemoteDataSourceImpl(api)

        val exception = assertFailsWith<AppException> {
            dataSource.fetchQuestions(amount = 10, categoryId = null, difficulty = null)
        }

        assertEquals(AppError.Network.Unavailable, exception.error)
    }

    @Test
    fun fetchQuestions_apiThrowsCancellation_propagatesUnwrapped() = runTest {
        val api = FakeQuizApi(questionsResult = { _, _, _ -> throw CancellationException("cancelled") })
        val dataSource = QuizRemoteDataSourceImpl(api)

        assertFailsWith<CancellationException> {
            dataSource.fetchQuestions(amount = 10, categoryId = null, difficulty = null)
        }
    }

    // endregion

    private fun sampleQuestionDto() = TriviaQuestionDto(
        type = "multiple",
        difficulty = "easy",
        category = "General Knowledge",
        question = "Q?",
        correctAnswer = "A",
        incorrectAnswers = listOf("B", "C", "D"),
    )

    private class FakeQuizApi(
        private val categoriesResult: (suspend () -> CategoryResponseDto)? = null,
        private val questionsResult: (suspend (Int, Int?, String?) -> TriviaResponseDto)? = null,
    ) : QuizApi {
        override suspend fun getCategories(): CategoryResponseDto =
            categoriesResult?.invoke() ?: error("categoriesResult not configured for this test")

        override suspend fun getQuestions(amount: Int, categoryId: Int?, difficulty: String?): TriviaResponseDto =
            questionsResult?.invoke(amount, categoryId, difficulty)
                ?: error("questionsResult not configured for this test")
    }
}