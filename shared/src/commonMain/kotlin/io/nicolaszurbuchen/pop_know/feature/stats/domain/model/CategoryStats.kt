package io.nicolaszurbuchen.pop_know.feature.stats.domain.model

import io.nicolaszurbuchen.pop_know.core.domain.AnswerStats
import io.nicolaszurbuchen.pop_know.core.domain.Category

data class CategoryStats(
    val category: Category,
    val answerStats: AnswerStats,
)
