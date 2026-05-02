package io.nicolaszurbuchen.pop_know.feature.stats.presentation.model

import io.nicolaszurbuchen.pop_know.core.presentation.mvi.UiIntent

sealed class StatsIntent : UiIntent {
    data object NavigateBack : StatsIntent()
}
