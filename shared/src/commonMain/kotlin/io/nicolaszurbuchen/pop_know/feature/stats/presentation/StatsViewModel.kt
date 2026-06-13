package io.nicolaszurbuchen.pop_know.feature.stats.presentation

import io.nicolaszurbuchen.pop_know.feature.stats.domain.usecase.GetFullStatsUseCase
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.model.StatsContent
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.model.StatsEffect
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.model.StatsIntent
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.model.StatsState

class StatsViewModel(
    private val getFullStatsUseCase: GetFullStatsUseCase,
) : io.nicolaszurbuchen.pop_know.common.presentation.mvi.MviViewModel<StatsState, StatsIntent, StatsEffect>(
    initialState = StatsState(isLoading = true),
) {
    init {
        loadStats()
    }

    override fun onIntent(intent: StatsIntent) {
        when (intent) {
            StatsIntent.NavigateBack -> emitEffect(StatsEffect.NavigateBack)
        }
    }

    private fun loadStats() {
        launch {
            val stats = getFullStatsUseCase()
            updateState { copy(isLoading = false, content = stats?.let { StatsContent(it) }) }
        }
    }
}
