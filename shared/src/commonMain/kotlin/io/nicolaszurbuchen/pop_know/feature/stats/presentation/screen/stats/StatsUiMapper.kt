package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.toUi
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.toUiModel
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.CategoryStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.DifficultyStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.FullStats

fun StatsState.toUiModel() = StatsUiModel(
    isLoading = isLoading,
    error = error,
    stats = stats?.toDataUiModel()
)

private fun FullStats.toDataUiModel() = StatsDataUiModel(
    summary = summary.toUiModel(),
    perCategory = perCategory.map { it.toUiModel() },
    perDifficulty = perDifficulty.map { it.toUiModel() }
)

private fun CategoryStats.toUiModel() = StatsCategoryUiModel(
    category = category.toUi(),
    answerStats = answerStats.toUiModel()
)

private fun DifficultyStats.toUiModel() = StatsDifficultyUiModel(
    difficulty = difficulty.toUi(),
    answerStats = answerStats.toUiModel()
)
