package io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model

import io.nicolaszurbuchen.pop_know.core.presentation.mvi.UiEffect
import io.nicolaszurbuchen.pop_know.feature.home.presentation.model.HomeEffect

sealed class ResultEffect : UiEffect {
    object NavigateHome : ResultEffect()
    object PlayAgain : ResultEffect()
    object NavigateToStats : ResultEffect()
}
