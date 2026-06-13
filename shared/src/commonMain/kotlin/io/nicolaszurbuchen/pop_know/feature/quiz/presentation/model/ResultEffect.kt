package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.model

sealed class ResultEffect : io.nicolaszurbuchen.pop_know.common.presentation.mvi.UiEffect {
    object NavigateHome : ResultEffect()
    object PlayAgain : ResultEffect()
    object NavigateToStats : ResultEffect()
}
