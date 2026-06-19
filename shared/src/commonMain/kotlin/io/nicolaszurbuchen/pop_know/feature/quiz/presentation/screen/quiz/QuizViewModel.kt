package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class QuizViewModel(
    factory: QuizStoreFactory,
) : ViewModel() {
    private val store = factory.create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<QuizUiModel> = store.stateFlow
        .map { it.toUiModel() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), QuizState().toUiModel())

    val labels: Flow<QuizLabel> = store.labels

    fun onIntent(intent: QuizIntent) {
        store.accept(intent)
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }
}
