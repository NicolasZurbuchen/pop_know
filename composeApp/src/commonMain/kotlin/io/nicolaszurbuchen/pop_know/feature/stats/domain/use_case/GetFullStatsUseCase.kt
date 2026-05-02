package io.nicolaszurbuchen.pop_know.feature.stats.domain.use_case

import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.FullStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.repository.StatsRepository

class GetFullStatsUseCase(
    private val repository: StatsRepository
) {
    suspend operator fun invoke(): FullStats? = repository.getFullStats()
}
