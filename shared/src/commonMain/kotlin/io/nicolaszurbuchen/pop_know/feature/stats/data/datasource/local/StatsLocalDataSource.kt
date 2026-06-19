package io.nicolaszurbuchen.pop_know.feature.stats.data.datasource.local

import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.CategoryStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.DifficultyStats

interface StatsLocalDataSource {
    fun countAll(): Long
    fun countCorrect(): Long
    fun statsByDifficulty(): List<DifficultyStats>
    fun statsByCategory(): List<CategoryStats>
    fun clearAll()
}
