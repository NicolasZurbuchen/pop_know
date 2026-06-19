package io.nicolaszurbuchen.pop_know.feature.stats.domain.usecase

import io.nicolaszurbuchen.pop_know.feature.stats.domain.repository.StatsRepository

class ClearStatsUseCase(
    private val statsRepository: StatsRepository,
) {
    suspend operator fun invoke() {
        statsRepository.clearAllStats()
    }
}
