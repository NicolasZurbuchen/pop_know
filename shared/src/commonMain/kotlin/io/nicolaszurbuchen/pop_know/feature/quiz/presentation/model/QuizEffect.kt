package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.model

sealed class QuizEffect : io.nicolaszurbuchen.pop_know.common.presentation.mvi.UiEffect {
    data object NavigateToResult : QuizEffect()
}
