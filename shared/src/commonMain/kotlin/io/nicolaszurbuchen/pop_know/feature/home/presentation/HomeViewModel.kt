package io.nicolaszurbuchen.pop_know.feature.home.presentation

import io.nicolaszurbuchen.pop_know.core.presentation.mvi.MviViewModel
import io.nicolaszurbuchen.pop_know.feature.home.domain.usecase.GetAnswerStatsUseCase
import io.nicolaszurbuchen.pop_know.feature.home.presentation.model.HomeContent
import io.nicolaszurbuchen.pop_know.feature.home.presentation.model.HomeEffect
import io.nicolaszurbuchen.pop_know.feature.home.presentation.model.HomeIntent
import io.nicolaszurbuchen.pop_know.feature.home.presentation.model.HomeState

class HomeViewModel(
    private val getAnswerStatsUseCase: GetAnswerStatsUseCase,
) : MviViewModel<HomeState, HomeIntent, HomeEffect>(
    initialState = HomeState(isLoading = true),
) {
    init {
        loadStats()
    }

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.NavigateToPlay -> emitEffect(HomeEffect.NavigateToPlay)
            HomeIntent.NavigateToStats -> emitEffect(HomeEffect.NavigateToStats)
        }
    }

    private fun loadStats() {
        launch {
            val stats = getAnswerStatsUseCase()
            updateState { copy(isLoading = false, content = stats?.let { HomeContent(stats = it) }) }
        }
    }
}