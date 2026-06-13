package io.nicolaszurbuchen.pop_know.common.domain

data class AnswerStats(
    val totalAnswered: Int,
    val totalCorrect: Int,
    val accuracy: Float,
)
