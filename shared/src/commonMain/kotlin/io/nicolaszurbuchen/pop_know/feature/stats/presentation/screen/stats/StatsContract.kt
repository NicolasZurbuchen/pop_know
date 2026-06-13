package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.FullStats
import io.nicolaszurbuchen.pop_know.infra.ui.UiText

sealed interface StatsIntent {
    data object NavigateBack : StatsIntent
}

sealed interface StatsLabel {
    data object NavigateBack : StatsLabel
}

sealed interface StatsAction {
    data object LoadStats : StatsAction
}

sealed interface StatsMessage {
    data class StatsLoaded(val stats: FullStats?) : StatsMessage
}

data class StatsState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val stats: FullStats? = null,
)
