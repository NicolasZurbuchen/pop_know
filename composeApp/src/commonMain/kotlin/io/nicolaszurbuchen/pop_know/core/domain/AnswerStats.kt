package io.nicolaszurbuchen.pop_know.core.domain

data class AnswerStats(
    val totalAnswered: Int,
    val totalCorrect: Int,
    val accuracy: Float,
)
