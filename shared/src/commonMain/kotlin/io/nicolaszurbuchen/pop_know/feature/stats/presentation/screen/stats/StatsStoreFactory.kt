package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.nicolaszurbuchen.pop_know.feature.stats.domain.usecase.GetFullStatsUseCase
import kotlinx.coroutines.launch

interface StatsStore : Store<StatsIntent, StatsState, StatsLabel>

class StatsStoreFactory(
    private val storeFactory: StoreFactory,
    private val getFullStats: GetFullStatsUseCase,
) {
    fun create(): StatsStore =
        object :
            StatsStore,
            Store<StatsIntent, StatsState, StatsLabel> by storeFactory.create(
                name = "StatsStore",
                initialState = StatsState(isLoading = true),
                bootstrapper = BootstrapperImpl(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private class BootstrapperImpl : CoroutineBootstrapper<StatsAction>() {
        override fun invoke() {
            dispatch(StatsAction.LoadStats)
        }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<StatsIntent, StatsAction, StatsState, StatsMessage, StatsLabel>() {
        override fun executeAction(action: StatsAction) {
            when (action) {
                StatsAction.LoadStats -> loadStats()
            }
        }

        override fun executeIntent(intent: StatsIntent) {
            when (intent) {
                StatsIntent.NavigateBack -> publish(StatsLabel.NavigateBack)
            }
        }

        private fun loadStats() {
            scope.launch {
                val stats = getFullStats()
                dispatch(StatsMessage.StatsLoaded(stats))
            }
        }
    }

    private object ReducerImpl : Reducer<StatsState, StatsMessage> {
        override fun StatsState.reduce(msg: StatsMessage): StatsState =
            when (msg) {
                is StatsMessage.StatsLoaded -> copy(
                    isLoading = false,
                    stats = msg.stats,
                )
            }
    }
}
