package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.model

sealed class ResultIntent : io.nicolaszurbuchen.pop_know.common.presentation.mvi.UiIntent {
    data object NavigateHome : ResultIntent()
    data object PlayAgain : ResultIntent()
}
