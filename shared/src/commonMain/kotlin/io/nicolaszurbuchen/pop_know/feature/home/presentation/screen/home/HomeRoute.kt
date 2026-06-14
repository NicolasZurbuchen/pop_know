package io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoute(
    onNavigateToPlay: () -> Unit,
    onNavigateToStats: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onNavigateToPlayUpdated by rememberUpdatedState(onNavigateToPlay)
    val onNavigateToStatsUpdated by rememberUpdatedState(onNavigateToStats)

    LaunchedEffect(Unit) {
        viewModel.labels.collect { label ->
            when (label) {
                HomeLabel.NavigateToPlay -> onNavigateToPlayUpdated()
                HomeLabel.NavigateToStats -> onNavigateToStatsUpdated()
            }
        }
    }

    HomeScreen(
        state = state,
        onStartRoundClick = { viewModel.onIntent(HomeIntent.NavigateToPlay) },
        onViewStatsClick = { viewModel.onIntent(HomeIntent.NavigateToStats) },
        onRetryClick = { viewModel.onIntent(HomeIntent.Retry) },
        onDismissErrorClick = { viewModel.onIntent(HomeIntent.DismissError) },
    )
}
