package io.nicolaszurbuchen.pop_know.feature.quizz.domain.usecase

import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.TriviaQuestion
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.repository.QuizRepository

class SaveAnswerUseCase(
    private val repository: QuizRepository,
) {
    suspend operator fun invoke(
        gameId: Long,
        question: TriviaQuestion,
        selectedAnswer: String?,
        status: AnswerStatus,
    ) = repository.saveAnswer(gameId, question, selectedAnswer, status)
}
