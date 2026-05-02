package io.nicolaszurbuchen.pop_know.feature.quizz.domain.usecase

import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.QuizSession
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.repository.QuizRepository

class SaveQuizSessionUseCase(private val repository: QuizRepository) {
    suspend operator fun invoke(session: QuizSession) = repository.saveSession(session)
}
