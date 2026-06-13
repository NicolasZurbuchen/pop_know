package io.nicolaszurbuchen.pop_know.feature.stats.domain.model

data class FullStats(
    val summary: io.nicolaszurbuchen.pop_know.common.domain.AnswerStats,
    val perCategory: List<CategoryStats>,
    val perDifficulty: List<DifficultyStats>
)