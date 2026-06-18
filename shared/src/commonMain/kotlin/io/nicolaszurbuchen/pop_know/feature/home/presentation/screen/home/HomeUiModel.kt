package io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.home

import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.AnswerStatsUiModel

data class HomeUiModel(
    val isLoading: Boolean,
    val error: AppError?,
    val stats: AnswerStatsUiModel?,
    val hasHistory: Boolean,
)
