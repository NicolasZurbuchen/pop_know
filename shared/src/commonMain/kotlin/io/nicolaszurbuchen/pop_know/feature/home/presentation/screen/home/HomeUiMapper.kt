package io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.home

import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.toUiModel

fun HomeState.toUiModel() = HomeUiModel(
    isLoading = isLoading,
    error = error,
    stats = stats?.toUiModel(),
    hasHistory = stats != null && stats.totalAnswered > 0,
)