package io.nicolaszurbuchen.pop_know.feature.home.domain.usecase

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.AnswerStats
import io.nicolaszurbuchen.pop_know.feature.home.domain.repository.HomeRepository

class GetAnswerStatsUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(): AnswerStats? = repository.getAnswerStats()
}
