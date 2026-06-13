package io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.home

import io.nicolaszurbuchen.pop_know.common.domain.AnswerStats
import io.nicolaszurbuchen.pop_know.infra.ui.UiText

sealed interface HomeIntent {
    data object NavigateToPlay : HomeIntent
    data object NavigateToStats : HomeIntent
}

sealed interface HomeLabel {
    data object NavigateToPlay : HomeLabel
    data object NavigateToStats : HomeLabel
}

sealed interface HomeAction {
    data object LoadStats : HomeAction
}

sealed interface HomeMessage {
    data class StatsLoaded(val stats: AnswerStats?) : HomeMessage
}

data class HomeState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val stats: AnswerStats? = null,
) {
    val hasHistory: Boolean
        get() = stats != null && stats.totalAnswered > 0
}
