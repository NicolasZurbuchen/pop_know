package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.FullStats

sealed interface StatsIntent {
    data object NavigateBack : StatsIntent
    data object Retry : StatsIntent
    data class ShowClearDialog(val isOpen: Boolean) : StatsIntent
    data object ConfirmClearStats : StatsIntent
}

sealed interface StatsLabel {
    data object NavigateBack : StatsLabel
    data object NavigateToHome : StatsLabel
}

sealed interface StatsAction {
    data object LoadStats : StatsAction
    data object ClearStats : StatsAction
}

sealed interface StatsMessage {
    data object StatsLoading : StatsMessage
    data class StatsLoaded(val stats: FullStats?) : StatsMessage
    data class Error(val error: AppError) : StatsMessage
    data class ShowClearDialog(val isOpen: Boolean) : StatsMessage
}

data class StatsState(
    val isLoading: Boolean = false,
    val error: AppError? = null,
    val stats: FullStats? = null,
    val isClearDialogOpen: Boolean = false,
)
