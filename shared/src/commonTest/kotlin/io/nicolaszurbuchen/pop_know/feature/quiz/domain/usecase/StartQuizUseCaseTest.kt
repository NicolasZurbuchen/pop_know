package io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.fake.FakeQuizRepository
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuestionProgress
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StartQuizUseCaseTest {

    @Test
    fun invoke_requestsTenQuestionsFromRepository() = runTest {
        val repository = FakeQuizRepository().apply { fetchQuestionsResult = listOf(sampleQuestion()) }
        val useCase = StartQuizUseCase(repository)

        useCase()

        assertEquals(10, repository.lastRequestedAmount)
    }

    @Test
    fun invoke_wrapsEachQuestionAsUnanswered() = runTest {
        val questions = listOf(sampleQuestion(), sampleQuestion())
        val repository = FakeQuizRepository().apply { fetchQuestionsResult = questions }
        val useCase = StartQuizUseCase(repository)

        val session = useCase()

        assertEquals(questions.size, session.questionStates.size)
        assertTrue(session.questionStates.all { it is QuestionProgress.Unanswered })
    }

    @Test
    fun invoke_startsAtIndexZero() = runTest {
        val repository = FakeQuizRepository().apply { fetchQuestionsResult = listOf(sampleQuestion()) }
        val useCase = StartQuizUseCase(repository)

        val session = useCase()

        assertEquals(0, session.currentIndex)
    }

    private fun sampleQuestion() = TriviaQuestion(
        questionType = QuestionType.MULTIPLE,
        difficulty = Difficulty.EASY,
        category = Category(id = 1, category = "General Knowledge"),
        question = "Q?",
        correctAnswer = "A",
        incorrectAnswers = listOf("B", "C", "D"),
    )
}