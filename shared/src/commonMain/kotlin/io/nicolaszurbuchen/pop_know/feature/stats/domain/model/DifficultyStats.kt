package io.nicolaszurbuchen.pop_know.feature.stats.domain.model

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.AnswerStats
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty

data class DifficultyStats(
    val difficulty: Difficulty,
    val answerStats: AnswerStats,
)
