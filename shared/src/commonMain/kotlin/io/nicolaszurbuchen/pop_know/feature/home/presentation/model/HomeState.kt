package io.nicolaszurbuchen.pop_know.feature.home.presentation.model

import io.nicolaszurbuchen.pop_know.common.presentation.UiText
import io.nicolaszurbuchen.pop_know.common.presentation.mvi.UiState

data class HomeState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val content: HomeContent? = null,
) : UiState {
    val hasHistory: Boolean
        get() = content != null && content.stats.totalAnswered > 0
}