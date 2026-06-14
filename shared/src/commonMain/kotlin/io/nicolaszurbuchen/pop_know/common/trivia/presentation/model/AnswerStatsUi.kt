package io.nicolaszurbuchen.pop_know.common.trivia.presentation.model

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.AnswerStats

data class AnswerStatsUi(
    val totalAnswered: Int,
    val totalCorrect: Int,
    val accuracy: Float,
)

fun AnswerStats.toUi() = AnswerStatsUi(
    totalAnswered = totalAnswered,
    totalCorrect = totalCorrect,
    accuracy = accuracy,
)
