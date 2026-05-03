package io.nicolaszurbuchen.pop_know.feature.quizz.presentation

import io.nicolaszurbuchen.pop_know.core.presentation.mvi.MviViewModel
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.usecase.GetLastGameResultUseCase
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.ResultEffect
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.ResultIntent
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.ResultState

class ResultViewModel(
    private val getLastGameResultUseCase: GetLastGameResultUseCase,
) : MviViewModel<ResultState, ResultIntent, ResultEffect>(
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
