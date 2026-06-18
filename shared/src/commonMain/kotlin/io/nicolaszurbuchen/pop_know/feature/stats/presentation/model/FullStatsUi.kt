package io.nicolaszurbuchen.pop_know.feature.stats.presentation.model

import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.AnswerStatsUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.toUiModel
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.FullStats

data class FullStatsUi(
    val summary: AnswerStatsUiModel,
    val perCategory: List<CategoryStatsUi>,
    val perDifficulty: List<DifficultyStatsUi>
)

fun FullStats.toUi() = FullStatsUi(
    summary = summary.toUiModel(),
    perCategory = perCategory.map { it.toUi() },
    perDifficulty = perDifficulty.map { it.toUi() },
)
