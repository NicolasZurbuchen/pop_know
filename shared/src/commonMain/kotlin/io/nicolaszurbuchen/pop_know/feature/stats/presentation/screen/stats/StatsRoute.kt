package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StatsRoute(
    onNavigateBack: () -> Unit,
    viewModel: StatsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onNavigateBackUpdated by rememberUpdatedState(onNavigateBack)

    LaunchedEffect(Unit) {
        viewModel.labels.collect { label ->
            when (label) {
                StatsLabel.NavigateBack -> onNavigateBackUpdated()
            }
        }
    }

    StatsScreen(
        state = state,
        onBackClick = { viewModel.onIntent(StatsIntent.NavigateBack) },
    )
}
