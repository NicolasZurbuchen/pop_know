package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

import io.nicolaszurbuchen.pop_know.common.error.toUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.toUiModel
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.CategoryStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.DifficultyStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.FullStats

fun StatsState.toUiModel() = StatsUiModel(
    isLoading = isLoading,
    error = error?.toUiModel(),
    stats = stats?.toUiModel(),
    isClearDialogOpen = isClearDialogOpen,
)

private fun FullStats.toUiModel() = StatsDataUiModel(
    summary = summary.toUiModel(),
    perCategory = perCategory.map { it.toUiModel() },
    perDifficulty = perDifficulty.map { it.toUiModel() }
)

private fun CategoryStats.toUiModel() = StatsCategoryUiModel(
    category = category.toUiModel(),
    answerStats = answerStats.toUiModel()
)

private fun DifficultyStats.toUiModel() = StatsDifficultyUiModel(
    difficulty = difficulty.toUiModel(),
    answerStats = answerStats.toUiModel()
)
