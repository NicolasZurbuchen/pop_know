package io.nicolaszurbuchen.pop_know.feature.quizz.domain.usecase

import io.nicolaszurbuchen.pop_know.feature.quizz.domain.repository.QuizRepository

class GetLastGameResultUseCase(
    private val repository: QuizRepository,
) {
    suspend operator fun invoke() = repository.getLastGameResult()
}
