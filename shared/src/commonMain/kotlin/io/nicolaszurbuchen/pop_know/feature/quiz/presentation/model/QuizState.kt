package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.model

data class QuizState(
    val isLoading: Boolean = false,
    val error: io.nicolaszurbuchen.pop_know.common.presentation.UiText? = null,
    val content: QuizUiModel? = null,
) : io.nicolaszurbuchen.pop_know.common.presentation.mvi.UiState
