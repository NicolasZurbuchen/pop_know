package io.nicolaszurbuchen.pop_know.feature.stats.domain.model

data class DifficultyStats(
    val difficulty: io.nicolaszurbuchen.pop_know.common.domain.Difficulty,
    val answerStats: io.nicolaszurbuchen.pop_know.common.domain.AnswerStats,
)
