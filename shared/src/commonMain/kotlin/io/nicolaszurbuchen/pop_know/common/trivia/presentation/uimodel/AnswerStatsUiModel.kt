package io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.AnswerStats

data class AnswerStatsUiModel(
    val totalAnswered: Int,
    val totalCorrect: Int,
    val accuracy: Float,
)

fun AnswerStats.toUiModel() =
    AnswerStatsUiModel(
        totalAnswered = totalAnswered,
        totalCorrect = totalCorrect,
        accuracy = accuracy,
    )
