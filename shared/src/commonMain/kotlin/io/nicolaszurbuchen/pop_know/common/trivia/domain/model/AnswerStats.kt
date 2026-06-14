package io.nicolaszurbuchen.pop_know.common.trivia.domain.model

data class AnswerStats(
    val totalAnswered: Int,
    val totalCorrect: Int,
    val accuracy: Float,
)
