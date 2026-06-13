package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.model

import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.GameResult

data class ResultState(
    val isLoading: Boolean = false,
    val content: GameResult? = null,
) : io.nicolaszurbuchen.pop_know.common.presentation.mvi.UiState
