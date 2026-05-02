package io.nicolaszurbuchen.pop_know.feature.home.presentation.model

import io.nicolaszurbuchen.pop_know.core.presentation.mvi.UiEffect

sealed class HomeEffect : UiEffect {
    object NavigateToPlay : HomeEffect()
    object NavigateToStats : HomeEffect()
}