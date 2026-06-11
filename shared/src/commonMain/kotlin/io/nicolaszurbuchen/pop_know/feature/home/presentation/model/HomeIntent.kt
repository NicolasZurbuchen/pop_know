package io.nicolaszurbuchen.pop_know.feature.home.presentation.model

import io.nicolaszurbuchen.pop_know.core.presentation.mvi.UiIntent

sealed class HomeIntent : UiIntent {
    object NavigateToPlay : HomeIntent()
    object NavigateToStats : HomeIntent()
}