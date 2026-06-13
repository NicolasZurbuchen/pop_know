package io.nicolaszurbuchen.pop_know.feature.quiz.presentation

import io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase.GetLastGameResultUseCase
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.model.ResultEffect
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.model.ResultIntent
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.model.ResultState

class ResultViewModel(
    private val getLastGameResultUseCase: GetLastGameResultUseCase,
) : io.nicolaszurbuchen.pop_know.common.presentation.mvi.MviViewModel<ResultState, ResultIntent, ResultEffect>(
    initialState = ResultState(isLoading = true),
) {
    init {
        launch {
            val result = getLastGameResultUseCase()
            updateState { copy(isLoading = false, content = result) }
        }
    }

    override fun onIntent(intent: ResultIntent) {
        when (intent) {
            ResultIntent.NavigateHome -> emitEffect(ResultEffect.NavigateHome)
            ResultIntent.PlayAgain -> emitEffect(ResultEffect.PlayAgain)
        }
    }
}
