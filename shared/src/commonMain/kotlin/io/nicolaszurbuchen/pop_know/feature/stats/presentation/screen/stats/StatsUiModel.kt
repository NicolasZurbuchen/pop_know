package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

import io.nicolaszurbuchen.pop_know.common.error.AppErrorUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.AnswerStatsUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.CategoryUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.DifficultyUiModel

data class StatsUiModel(
    val isLoading: Boolean,
    val error: AppErrorUiModel?,
    val stats: StatsDataUiModel?,
    val isClearDialogOpen: Boolean,
)

data class StatsDataUiModel(
    val summary: AnswerStatsUiModel,
    val perCategory: List<StatsCategoryUiModel>,
    val perDifficulty: List<StatsDifficultyUiModel>,
)

data class StatsCategoryUiModel(
    val category: CategoryUiModel,
    val answerStats: AnswerStatsUiModel,
)

data class StatsDifficultyUiModel(
    val difficulty: DifficultyUiModel,
    val answerStats: AnswerStatsUiModel,
)
