package io.nicolaszurbuchen.pop_know.feature.stats.domain.model

data class CategoryStats(
    val category: io.nicolaszurbuchen.pop_know.common.domain.Category,
    val answerStats: io.nicolaszurbuchen.pop_know.common.domain.AnswerStats,
)
