package io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model

import io.nicolaszurbuchen.pop_know.core.presentation.UiText
import io.nicolaszurbuchen.pop_know.core.presentation.mvi.UiState

data class QuizState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val content: QuizUiModel? = null,
) : UiState
