package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import androidx.lifecycle.ViewModel
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class QuizViewModel(
    gameId: Long,
    factory: QuizStoreFactory,
) : ViewModel() {
    private val store = factory.create(gameId)

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<QuizState> = store.stateFlow

    val labels: Flow<QuizLabel> = store.labels

    fun onIntent(intent: QuizIntent) {
        store.accept(intent)
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }
}
