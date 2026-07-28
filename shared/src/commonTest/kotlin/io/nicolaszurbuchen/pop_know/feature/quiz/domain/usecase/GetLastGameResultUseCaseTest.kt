package io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.fake.FakeQuizRepository
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnsweredQuestionResult
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.GameResult
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetLastGameResultUseCaseTest {

    @Test
    fun invoke_delegatesDirectlyToRepository() = runTest {
        val expected = GameResult(
            questions = listOf(
                AnsweredQuestionResult(
                    question = "Q?", correctAnswer = "A", selectedAnswer = "A",
                    status = AnswerStatus.CORRECT, categoryName = "General Knowledge",
                    difficulty = Difficulty.EASY,
                ),
            ),
        )
        val repository = FakeQuizRepository().apply { lastGameResult = expected }
        val useCase = GetLastGameResultUseCase(repository)

        assertEquals(expected, useCase())
    }

    @Test
    fun invoke_noLastGame_returnsNull() = runTest {
        val repository = FakeQuizRepository()
        val useCase = GetLastGameResultUseCase(repository)

        assertNull(useCase())
    }
}