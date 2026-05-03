package io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model

import io.nicolaszurbuchen.pop_know.core.presentation.mvi.UiIntent

sealed class QuizIntent : UiIntent {
    data class SelectAnswer(val answer: String) : QuizIntent()
    data object Next : QuizIntent()
    data object SeeResult : QuizIntent()
}
