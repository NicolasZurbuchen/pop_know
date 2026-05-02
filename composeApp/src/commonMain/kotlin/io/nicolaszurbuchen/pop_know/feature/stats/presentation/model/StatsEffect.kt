package io.nicolaszurbuchen.pop_know.feature.stats.presentation.model

import io.nicolaszurbuchen.pop_know.core.presentation.mvi.UiEffect

sealed class StatsEffect : UiEffect {
    data object NavigateBack : StatsEffect()
}
