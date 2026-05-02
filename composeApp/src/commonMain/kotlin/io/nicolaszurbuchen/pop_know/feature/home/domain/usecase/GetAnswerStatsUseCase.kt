package io.nicolaszurbuchen.pop_know.feature.home.domain.usecase

import io.nicolaszurbuchen.pop_know.core.domain.AnswerStats
import io.nicolaszurbuchen.pop_know.feature.home.domain.repository.HomeRepository

class GetAnswerStatsUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(): AnswerStats? = repository.getAnswerStats()
}
