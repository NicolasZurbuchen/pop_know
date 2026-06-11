package io.nicolaszurbuchen.pop_know.feature.stats.presentation.model

import io.nicolaszurbuchen.pop_know.core.presentation.UiText
import io.nicolaszurbuchen.pop_know.core.presentation.mvi.UiState

data class StatsState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val content: StatsContent? = null,
) : UiState
