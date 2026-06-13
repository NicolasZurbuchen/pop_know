package io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase

import io.nicolaszurbuchen.pop_know.feature.quiz.domain.repository.QuizRepository

class GetLastGameResultUseCase(
    private val repository: QuizRepository,
) {
    suspend operator fun invoke() = repository.getLastGameResult()
}
