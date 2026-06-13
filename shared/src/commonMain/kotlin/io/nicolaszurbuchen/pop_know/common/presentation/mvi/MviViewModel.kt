package io.nicolaszurbuchen.pop_know.common.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.nicolaszurbuchen.pop_know.common.domain.TriviaError
import io.nicolaszurbuchen.pop_know.common.domain.TriviaException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class MviViewModel<S : UiState, I : UiIntent, E : UiEffect>(
    initialState: S
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _effects = Channel<E>(Channel.BUFFERED)
    val effects: Flow<E> = _effects.receiveAsFlow()

    protected fun updateState(block: S.() -> S) {
        _state.update { it.block() }
    }

    protected fun emitEffect(effect: E) {
        viewModelScope.launch {
            _effects.send(effect)
        }
    }

    protected fun launch(
        onError: (e: TriviaError) -> Unit,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: TriviaException) {
                onError(e.error)
            } catch (e: Exception) {
                onError(TriviaError.Unknown(e.message))
            }
        }
    }

    protected fun launch(
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            block()
        }
    }

    abstract fun onIntent(intent: I)
}