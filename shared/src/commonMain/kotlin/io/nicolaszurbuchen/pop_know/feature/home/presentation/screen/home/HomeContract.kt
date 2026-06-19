package io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.home

import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.AnswerStats

sealed interface HomeIntent {
    data object NavigateToPlay : HomeIntent

    data object NavigateToStats : HomeIntent

    data object Retry : HomeIntent

    data object DismissError : HomeIntent
}

sealed interface HomeLabel {
    data object NavigateToPlay : HomeLabel

    data object NavigateToStats : HomeLabel
}

sealed interface HomeAction {
    data object LoadStats : HomeAction
}

sealed interface HomeMessage {
    data class StatsLoaded(
        val stats: AnswerStats?,
    ) : HomeMessage

    data class Error(
        val error: AppError?,
    ) : HomeMessage
}

data class HomeState(
    val isLoading: Boolean = false,
    val error: AppError? = null,
    val stats: AnswerStats? = null,
)
