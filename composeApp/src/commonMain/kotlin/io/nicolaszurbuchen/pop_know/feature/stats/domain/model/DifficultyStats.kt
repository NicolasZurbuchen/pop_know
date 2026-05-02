package io.nicolaszurbuchen.pop_know.feature.stats.domain.model

import io.nicolaszurbuchen.pop_know.core.domain.AnswerStats
import io.nicolaszurbuchen.pop_know.core.domain.Difficulty

data class DifficultyStats(
    val difficulty: Difficulty,
    val answerStats: AnswerStats,
)
