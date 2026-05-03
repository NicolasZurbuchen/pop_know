package io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model

import io.nicolaszurbuchen.pop_know.core.presentation.mvi.UiEffect

sealed class QuizEffect : UiEffect {
    data object NavigateToResult : QuizEffect()
}
