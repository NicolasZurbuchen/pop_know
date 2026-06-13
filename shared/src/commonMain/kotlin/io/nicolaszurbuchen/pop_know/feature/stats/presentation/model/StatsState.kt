package io.nicolaszurbuchen.pop_know.feature.stats.presentation.model

data class StatsState(
    val isLoading: Boolean = false,
    val error: io.nicolaszurbuchen.pop_know.common.presentation.UiText? = null,
    val content: StatsContent? = null,
) : io.nicolaszurbuchen.pop_know.common.presentation.mvi.UiState
