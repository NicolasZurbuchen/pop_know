package io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.home

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.nicolaszurbuchen.pop_know.feature.home.domain.usecase.GetAnswerStatsUseCase
import kotlinx.coroutines.launch

interface HomeStore : Store<HomeIntent, HomeState, HomeLabel>

class HomeStoreFactory(
    private val storeFactory: StoreFactory,
    private val getAnswerStats: GetAnswerStatsUseCase,
) {
    fun create(): HomeStore =
        object :
            HomeStore,
            Store<HomeIntent, HomeState, HomeLabel> by storeFactory.create(
                name = "HomeStore",
                initialState = HomeState(isLoading = true),
                bootstrapper = BootstrapperImpl(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private class BootstrapperImpl : CoroutineBootstrapper<HomeAction>() {
        override fun invoke() {
            dispatch(HomeAction.LoadStats)
        }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<HomeIntent, HomeAction, HomeState, HomeMessage, HomeLabel>() {
        override fun executeAction(action: HomeAction) {
            when (action) {
                HomeAction.LoadStats -> loadStats()
            }
        }

        override fun executeIntent(intent: HomeIntent) {
            when (intent) {
                HomeIntent.NavigateToPlay -> publish(HomeLabel.NavigateToPlay)
                HomeIntent.NavigateToStats -> publish(HomeLabel.NavigateToStats)
            }
        }

        private fun loadStats() {
            scope.launch {
                val stats = getAnswerStats()
                dispatch(HomeMessage.StatsLoaded(stats))
            }
        }
    }

    private object ReducerImpl : Reducer<HomeState, HomeMessage> {
        override fun HomeState.reduce(msg: HomeMessage): HomeState =
            when (msg) {
                is HomeMessage.StatsLoaded -> copy(
                    isLoading = false,
                    stats = msg.stats,
                )
            }
    }
}
