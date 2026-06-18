package io.nicolaszurbuchen.pop_know.feature.stats.presentation.model

import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.AnswerStatsUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.DifficultyUi
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.toUi
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.toUiModel
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.DifficultyStats

data class DifficultyStatsUi(
    val difficulty: DifficultyUi,
    val answerStats: AnswerStatsUiModel,
)

fun DifficultyStats.toUi() = DifficultyStatsUi(
    difficulty = difficulty.toUi(),
    answerStats = answerStats.toUiModel(),
)
