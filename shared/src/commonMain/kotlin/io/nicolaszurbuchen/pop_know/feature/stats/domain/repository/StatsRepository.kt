package io.nicolaszurbuchen.pop_know.feature.stats.domain.repository

import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.FullStats

interface StatsRepository {
    suspend fun getFullStats(): FullStats?

    suspend fun clearAllStats()
}
