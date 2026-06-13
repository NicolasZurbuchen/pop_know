package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

import androidx.lifecycle.ViewModel
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class StatsViewModel(
    factory: StatsStoreFactory,
) : ViewModel() {
    private val store = factory.create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<StatsState> = store.stateFlow

    val labels: Flow<StatsLabel> = store.labels

    fun onIntent(intent: StatsIntent) {
        store.accept(intent)
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }
}
