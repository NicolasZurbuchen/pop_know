package io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model

import io.nicolaszurbuchen.pop_know.core.presentation.mvi.UiIntent

sealed class ResultIntent : UiIntent {
    data object NavigateHome : ResultIntent()
    data object PlayAgain : ResultIntent()
}
