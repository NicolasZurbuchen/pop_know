package io.nicolaszurbuchen.pop_know.feature.stats.presentation.model

sealed class StatsIntent : io.nicolaszurbuchen.pop_know.common.presentation.mvi.UiIntent {
    data object NavigateBack : StatsIntent()
}
