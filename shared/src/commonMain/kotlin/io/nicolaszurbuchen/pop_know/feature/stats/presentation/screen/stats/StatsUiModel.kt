package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.AnswerStatsUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.CategoryUi
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.DifficultyUi

data class StatsUiModel(
    val isLoading: Boolean,
    val error: AppError?,
    val stats: StatsDataUiModel?,
)

data class StatsDataUiModel(
    val summary: AnswerStatsUiModel,
    val perCategory: List<StatsCategoryUiModel>,
    val perDifficulty: List<StatsDifficultyUiModel>
)

data class StatsCategoryUiModel(
    val category: CategoryUi,
    val answerStats: AnswerStatsUiModel
)

data class StatsDifficultyUiModel(
    val difficulty: DifficultyUi,
    val answerStats: AnswerStatsUiModel
)
