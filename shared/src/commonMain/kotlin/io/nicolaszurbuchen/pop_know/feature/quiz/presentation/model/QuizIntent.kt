package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.model

sealed class QuizIntent : io.nicolaszurbuchen.pop_know.common.presentation.mvi.UiIntent {
    data class SelectAnswer(val answer: String) : QuizIntent()
    data object Next : QuizIntent()
    data object SeeResult : QuizIntent()
}
