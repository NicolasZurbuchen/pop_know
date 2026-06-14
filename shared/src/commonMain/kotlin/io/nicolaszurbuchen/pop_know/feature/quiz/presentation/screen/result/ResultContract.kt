package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result

import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.GameResult
import io.nicolaszurbuchen.pop_know.infra.ui.UiText

sealed interface ResultIntent {
    data object NavigateHome : ResultIntent
    data object PlayAgain : ResultIntent
    data object ViewStats : ResultIntent
}

sealed interface ResultLabel {
    data object NavigateHome : ResultLabel
    data object PlayAgain : ResultLabel
    data object NavigateToStats : ResultLabel
}

sealed interface ResultAction {
    data object LoadResult : ResultAction
}

sealed interface ResultMessage {
    data class ResultLoaded(val result: GameResult?) : ResultMessage
}

data class ResultState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val content: GameResult? = null,
)
