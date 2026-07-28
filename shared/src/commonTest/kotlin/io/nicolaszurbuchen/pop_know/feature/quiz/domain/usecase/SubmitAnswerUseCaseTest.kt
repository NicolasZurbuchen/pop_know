package io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase

import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.error.AppException
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.fake.FakeQuizRepository
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuestionProgress
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuizSession
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SubmitAnswerUseCaseTest {

    // region happy path

    @Test
    fun invoke_correctAnswer_persistsAndReturnsUpdatedSession() = runTest {
        val repository = FakeQuizRepository()
        val useCase = SubmitAnswerUseCase(repository)
        val session = sessionOf(question(correctAnswer = "Paris"))

        val result = useCase(session, "Paris")

        assertEquals(1, repository.savedAnswers.size)
        assertEquals(AnswerStatus.CORRECT, repository.savedAnswers[0].status)
        val answered = result.questionStates[0] as QuestionProgress.Answered
        assertEquals(AnswerStatus.CORRECT, answered.status)
    }

    @Test
    fun invoke_timeoutAnswer_persistsTimeoutStatus() = runTest {
        val repository = FakeQuizRepository()
        val useCase = SubmitAnswerUseCase(repository)
        val session = sessionOf(question(correctAnswer = "Paris"))

        useCase(session, null)

        assertEquals(AnswerStatus.TIMEOUT, repository.savedAnswers.single().status)
    }

    @Test
    fun invoke_calledTwiceOnSameQuestion_persistsTwice_bugDocumentation() = runTest {
        val repository = FakeQuizRepository()
        val useCase = SubmitAnswerUseCase(repository)
        val session = sessionOf(question(correctAnswer = "Paris"))

        val afterFirst = useCase(session, "Paris")
        useCase(afterFirst, "Lyon")

        assertEquals(2, repository.savedAnswers.size)
    }

    @Test
    fun invoke_repositoryThrows_wrapsInDatabaseInsertFailed() = runTest {
        val repository = FakeQuizRepository().apply { saveAnswerError = RuntimeException("disk full") }
        val useCase = SubmitAnswerUseCase(repository)
        val session = sessionOf(question(correctAnswer = "Paris"))

        val exception = assertFailsWith<AppException> { useCase(session, "Paris") }

        assertEquals(true, exception.error is AppError.Database.InsertFailed)
    }

    // endregion

    private fun sessionOf(vararg questions: TriviaQuestion) = QuizSession(
        questionStates = questions.map { QuestionProgress.Unanswered(it) },
    )

    private fun question(correctAnswer: String) = TriviaQuestion(
        questionType = QuestionType.MULTIPLE,
        difficulty = Difficulty.EASY,
        category = Category(id = 1, category = "General Knowledge"),
        question = "Sample question?",
        correctAnswer = correctAnswer,
        incorrectAnswers = listOf("Wrong1", "Wrong2", "Wrong3"),
    )
}