package io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model

import io.nicolaszurbuchen.pop_know.core.presentation.mvi.UiState
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.GameResult

data class ResultState(
    val isLoading: Boolean = false,
    val content: GameResult? = null,
) : UiState
