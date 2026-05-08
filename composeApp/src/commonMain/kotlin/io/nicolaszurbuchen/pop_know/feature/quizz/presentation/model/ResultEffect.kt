package io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model

import io.nicolaszurbuchen.pop_know.core.presentation.mvi.UiEffect

sealed class ResultEffect : UiEffect {
    object NavigateHome : ResultEffect()
    object PlayAgain : ResultEffect()
    object NavigateToStats : ResultEffect()
}
