package io.nicolaszurbuchen.pop_know.feature.quiz.data.repository

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.CategoryEntity
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.GetLastGame
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local.QuizLocalDataSource
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.QuizRemoteDataSource
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.CategoryDto
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.TriviaQuestionDto
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class QuizRepositoryImplTest {

    // no-op decoder — this suite is testing cache/branching logic, not HTML decoding,
    // which is already covered by the mapper test suites.
    private val noOpDecode: (String) -> String = { it }

    // region fetchQuestions — cache branching

    @Test
    fun fetchQuestions_localCategoriesEmpty_fetchesAndSavesRemoteCategories() = runTest {
        val local = FakeQuizLocalDataSource(categories = emptyList())
        val remote = FakeQuizRemoteDataSource(
            categories = listOf(CategoryDto(id = 9, category = "General Knowledge")),
            questions = listOf(sampleQuestionDto(category = "General Knowledge")),
        )
        val repository = QuizRepositoryImpl(remote, local, decodeHtml = noOpDecode)

        repository.fetchQuestions(amount = 1)

        assertEquals(1, remote.fetchCategoriesCallCount)
        assertEquals(
            listOf(CategoryEntity(id = 9L, name = "General Knowledge")),
            local.savedCategories,
        )
    }

    @Test
    fun fetchQuestions_localCategoriesNonEmpty_skipsRemoteCategoryFetch() = runTest {
        val local = FakeQuizLocalDataSource(
            categories = listOf(CategoryEntity(id = 9L, name = "General Knowledge")),
        )
        val remote = FakeQuizRemoteDataSource(
            categories = listOf(CategoryDto(id = 999, category = "Should not be used")),
            questions = listOf(sampleQuestionDto(category = "General Knowledge")),
        )
        val repository = QuizRepositoryImpl(remote, local, decodeHtml = noOpDecode)

        repository.fetchQuestions(amount = 1)

        assertEquals(0, remote.fetchCategoriesCallCount)
        assertEquals(0, local.savedCategories.size)
    }

    @Test
    fun fetchQuestions_localCategoriesNonEmpty_usesLocalCategoriesForMapping() = runTest {
        val local = FakeQuizLocalDataSource(
            categories = listOf(CategoryEntity(id = 42L, name = "Science")),
        )
        val remote = FakeQuizRemoteDataSource(
            categories = emptyList(),
            questions = listOf(sampleQuestionDto(category = "Science")),
        )
        val repository = QuizRepositoryImpl(remote, local, decodeHtml = noOpDecode)

        val result = repository.fetchQuestions(amount = 1)

        assertEquals(Category(id = 42, category = "Science"), result.first().category)
    }

    @Test
    fun fetchQuestions_categoryMatchingUsesDecodedComparison() = runTest {
        // Regression coverage for the recent fix: `categories.find { it.category ==
        // decodeHtml(category) }` — matching must go through the decoder, not compare
        // the raw encoded DTO field directly against decoded cached names.
        val local = FakeQuizLocalDataSource(
            categories = listOf(CategoryEntity(id = 5L, name = "Entertainment: Video Games")),
        )
        val remote = FakeQuizRemoteDataSource(
            categories = emptyList(),
            questions = listOf(sampleQuestionDto(category = "Entertainment: Video Games&amp;")),
        )
        val decodeStrippingAmp: (String) -> String = { it.replace("&amp;", "") }
        val repository = QuizRepositoryImpl(remote, local, decodeHtml = decodeStrippingAmp)

        val result = repository.fetchQuestions(amount = 1)

        assertEquals(Category(id = 5, category = "Entertainment: Video Games"), result.first().category)
    }

    @Test
    fun fetchQuestions_passesAmountThroughToRemote() = runTest {
        val local = FakeQuizLocalDataSource(categories = listOf(CategoryEntity(id = 1L, name = "History")))
        val remote = FakeQuizRemoteDataSource(
            categories = emptyList(),
            questions = listOf(sampleQuestionDto()),
        )
        val repository = QuizRepositoryImpl(remote, local, decodeHtml = noOpDecode)

        repository.fetchQuestions(amount = 7)

        assertEquals(7, remote.lastRequestedAmount)
    }

    // endregion

    // region saveAnswer

    @Test
    fun saveAnswer_mapsQuestionFieldsAndDelegatesToLocal() = runTest {
        val local = FakeQuizLocalDataSource(categories = emptyList())
        val remote = FakeQuizRemoteDataSource(categories = emptyList(), questions = emptyList())
        val repository = QuizRepositoryImpl(remote, local, decodeHtml = noOpDecode)
        val question = TriviaQuestion(
            questionType = QuestionType.MULTIPLE,
            difficulty = Difficulty.EASY,
            category = Category(id = 9, category = "General Knowledge"),
            question = "Q?",
            correctAnswer = "A",
            incorrectAnswers = listOf("B", "C", "D"),
        )

        repository.saveAnswer(gameId = 1L, question = question, selectedAnswer = "A", status = AnswerStatus.CORRECT)

        val saved = local.savedAnswers.single()
        assertEquals(1L, saved.gameId)
        assertEquals("multiple", saved.type)
        assertEquals("easy", saved.difficulty)
        assertEquals(9L, saved.categoryId)
        assertEquals("Q?", saved.question)
        assertEquals("A", saved.correctAnswer)
        assertEquals(listOf("B", "C", "D"), saved.incorrectAnswers)
        assertEquals("A", saved.selectedAnswer)
        assertEquals("CORRECT", saved.status)
    }

    // endregion

    // region getLastGameResult

    @Test
    fun getLastGameResult_emptyRows_returnsNull() = runTest {
        val local = FakeQuizLocalDataSource(categories = emptyList(), lastGame = emptyList())
        val remote = FakeQuizRemoteDataSource(categories = emptyList(), questions = emptyList())
        val repository = QuizRepositoryImpl(remote, local, decodeHtml = noOpDecode)

        val result = repository.getLastGameResult()

        assertNull(result)
    }

    @Test
    fun getLastGameResult_nonEmptyRows_mapsToGameResultWithCorrectComputedStats() = runTest {
        val row = GetLastGame(
            id = 1L, game_id = 1L, type = "multiple", difficulty = "easy",
            question = "Q?", correct_answer = "A", incorrect_answers = listOf("B"),
            selected_answer = "A", status = "CORRECT", answered_at = 0L,
            category_name = "General Knowledge",
        )
        val local = FakeQuizLocalDataSource(categories = emptyList(), lastGame = listOf(row))
        val remote = FakeQuizRemoteDataSource(categories = emptyList(), questions = emptyList())
        val repository = QuizRepositoryImpl(remote, local, decodeHtml = noOpDecode)

        val result = repository.getLastGameResult()

        assertEquals(1, result?.questions?.size)
        assertEquals(1, result?.correctCount)
        assertEquals(0, result?.incorrectCount)
        assertEquals(0, result?.timeoutCount)
        assertEquals(1f, result?.score?.accuracy)
    }

    // endregion

    private fun sampleQuestionDto(category: String = "General Knowledge") = TriviaQuestionDto(
        type = "multiple",
        difficulty = "easy",
        category = category,
        question = "Q?",
        correctAnswer = "A",
        incorrectAnswers = listOf("B", "C", "D"),
    )

    private class FakeQuizLocalDataSource(
        private val categories: List<CategoryEntity>,
        private val lastGame: List<GetLastGame> = emptyList(),
    ) : QuizLocalDataSource {
        val savedCategories = mutableListOf<CategoryEntity>()
        val savedAnswers = mutableListOf<SavedAnswer>()

        override fun getCategories(): List<CategoryEntity> = categories
        override fun saveCategories(categories: List<CategoryEntity>) { savedCategories += categories }
        override fun getLastGame(): List<GetLastGame> = lastGame

        override fun saveAnswer(
            gameId: Long, type: String, difficulty: String, categoryId: Long,
            question: String, correctAnswer: String, incorrectAnswers: List<String>,
            selectedAnswer: String?, status: String,
        ) {
            savedAnswers += SavedAnswer(
                gameId, type, difficulty, categoryId, question,
                correctAnswer, incorrectAnswers, selectedAnswer, status,
            )
        }

        data class SavedAnswer(
            val gameId: Long, val type: String, val difficulty: String, val categoryId: Long,
            val question: String, val correctAnswer: String, val incorrectAnswers: List<String>,
            val selectedAnswer: String?, val status: String,
        )
    }

    private class FakeQuizRemoteDataSource(
        private val categories: List<CategoryDto>,
        private val questions: List<TriviaQuestionDto>,
    ) : QuizRemoteDataSource {
        var fetchCategoriesCallCount = 0
            private set
        var lastRequestedAmount: Int? = null
            private set

        override suspend fun fetchCategories(): List<CategoryDto> {
            fetchCategoriesCallCount++
            return categories
        }

        override suspend fun fetchQuestions(amount: Int, categoryId: Int?, difficulty: String?): List<TriviaQuestionDto> {
            lastRequestedAmount = amount
            return questions
        }
    }
}