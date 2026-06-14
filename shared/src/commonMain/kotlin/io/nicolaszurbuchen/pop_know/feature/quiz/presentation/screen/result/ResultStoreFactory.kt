package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase.GetLastGameResultUseCase
import kotlinx.coroutines.launch

interface ResultStore : Store<ResultIntent, ResultState, ResultLabel>

class ResultStoreFactory(
    private val storeFactory: StoreFactory,
    private val getLastGameResult: GetLastGameResultUseCase,
) {
    fun create(): ResultStore =
        object :
            ResultStore,
            Store<ResultIntent, ResultState, ResultLabel> by storeFactory.create(
                name = "ResultStore",
                initialState = ResultState(isLoading = true),
                bootstrapper = BootstrapperImpl(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private class BootstrapperImpl : CoroutineBootstrapper<ResultAction>() {
        override fun invoke() {
            dispatch(ResultAction.LoadResult)
        }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<ResultIntent, ResultAction, ResultState, ResultMessage, ResultLabel>() {
        override fun executeAction(action: ResultAction) {
            when (action) {
                ResultAction.LoadResult -> loadResult()
            }
        }

        override fun executeIntent(intent: ResultIntent) {
            when (intent) {
                ResultIntent.NavigateHome -> publish(ResultLabel.NavigateHome)
                ResultIntent.PlayAgain -> publish(ResultLabel.PlayAgain)
                ResultIntent.ViewStats -> publish(ResultLabel.NavigateToStats)
                ResultIntent.Retry -> loadResult()
            }
        }

        private fun loadResult() {
            dispatch(ResultMessage.ResultLoading)
            scope.launch {
                try {
                    val result = getLastGameResult()
                    dispatch(ResultMessage.ResultLoaded(result))
                } catch (e: Exception) {
                    dispatch(ResultMessage.Error(AppError.Database.QueryFailed(e)))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<ResultState, ResultMessage> {
        override fun ResultState.reduce(msg: ResultMessage): ResultState =
            when (msg) {
                ResultMessage.ResultLoading -> copy(
                    isLoading = true,
                    error = null,
                )

                is ResultMessage.ResultLoaded -> copy(
                    isLoading = false,
                    content = msg.result,
                )

                is ResultMessage.Error -> copy(
                    isLoading = false,
                    error = msg.error,
                )
            }
    }
}
