package io.nicolaszurbuchen.pop_know.feature.stats.domain.model

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.AnswerStats

data class FullStats(
    val summary: AnswerStats,
    val perCategory: List<CategoryStats>,
    val perDifficulty: List<DifficultyStats>
)