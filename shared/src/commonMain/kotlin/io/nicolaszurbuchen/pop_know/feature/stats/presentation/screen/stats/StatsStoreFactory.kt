package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.error.AppException
import io.nicolaszurbuchen.pop_know.feature.stats.domain.usecase.ClearStatsUseCase
import io.nicolaszurbuchen.pop_know.feature.stats.domain.usecase.GetFullStatsUseCase
import kotlinx.coroutines.launch

interface StatsStore : Store<StatsIntent, StatsState, StatsLabel>

class StatsStoreFactory(
    private val storeFactory: StoreFactory,
    private val getFullStats: GetFullStatsUseCase,
    private val clearStats: ClearStatsUseCase,
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
                StatsAction.ClearStats -> performClearStats()
            }
        }

        override fun executeIntent(intent: StatsIntent) {
            when (intent) {
                StatsIntent.NavigateBack -> publish(StatsLabel.NavigateBack)
                StatsIntent.Retry -> loadStats()
                is StatsIntent.ShowClearDialog -> dispatch(StatsMessage.ShowClearDialog(intent.isOpen))
                StatsIntent.ConfirmClearStats -> {
                    dispatch(StatsMessage.ShowClearDialog(false))
                    performClearStats()
                }
            }
        }

        private fun loadStats() {
            dispatch(StatsMessage.StatsLoading)
            scope.launch {
                try {
                    val stats = getFullStats()
                    dispatch(StatsMessage.StatsLoaded(stats))
                } catch (e: Exception) {
                    val error = (e as? AppException)?.error ?: AppError.Unexpected(e)
                    dispatch(StatsMessage.Error(error))
                }
            }
        }

        private fun performClearStats() {
            scope.launch {
                try {
                    clearStats()
                    publish(StatsLabel.NavigateToHome)
                } catch (e: Exception) {
                    val error = (e as? AppException)?.error ?: AppError.Unexpected(e)
                    dispatch(StatsMessage.Error(error))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<StatsState, StatsMessage> {
        override fun StatsState.reduce(msg: StatsMessage): StatsState =
            when (msg) {
                StatsMessage.StatsLoading -> copy(
                    isLoading = true,
                    error = null,
                )

                is StatsMessage.StatsLoaded -> copy(
                    isLoading = false,
                    stats = msg.stats,
                )

                is StatsMessage.Error -> copy(
                    isLoading = false,
                    error = msg.error,
                )

                is StatsMessage.ShowClearDialog -> copy(
                    isClearDialogOpen = msg.isOpen,
                )
            }
    }
}
