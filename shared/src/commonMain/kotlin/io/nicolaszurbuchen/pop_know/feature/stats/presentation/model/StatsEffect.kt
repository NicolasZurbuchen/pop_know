package io.nicolaszurbuchen.pop_know.feature.stats.presentation.model

sealed class StatsEffect : io.nicolaszurbuchen.pop_know.common.presentation.mvi.UiEffect {
    data object NavigateBack : StatsEffect()
}
