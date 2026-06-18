package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.AnswerStatsUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.CategoryUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.DifficultyUiModel

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
    val category: CategoryUiModel,
    val answerStats: AnswerStatsUiModel
)

data class StatsDifficultyUiModel(
    val difficulty: DifficultyUiModel,
    val answerStats: AnswerStatsUiModel
)
