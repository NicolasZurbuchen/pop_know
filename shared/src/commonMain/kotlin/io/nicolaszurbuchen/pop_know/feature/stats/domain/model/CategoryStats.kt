package io.nicolaszurbuchen.pop_know.feature.stats.domain.model

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.AnswerStats
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category

data class CategoryStats(
    val category: Category,
    val answerStats: AnswerStats,
)
