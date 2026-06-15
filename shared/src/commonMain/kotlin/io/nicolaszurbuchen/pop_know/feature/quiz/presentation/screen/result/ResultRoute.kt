package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.nicolaszurbuchen.pop_know.infra.platform.BackHandler
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ResultRoute(
    onNavigateHome: () -> Unit,
    onPlayAgain: () -> Unit,
    onNavigateToStats: () -> Unit,
    viewModel: ResultViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onNavigateHomeUpdated by rememberUpdatedState(onNavigateHome)
    val onPlayAgainUpdated by rememberUpdatedState(onPlayAgain)
    val onNavigateToStatsUpdated by rememberUpdatedState(onNavigateToStats)

    BackHandler {
        viewModel.onIntent(ResultIntent.NavigateHome)
    }

    LaunchedEffect(Unit) {
        viewModel.labels.collect { label ->
            when (label) {
                ResultLabel.NavigateHome -> onNavigateHomeUpdated()
                ResultLabel.PlayAgain -> onPlayAgainUpdated()
                ResultLabel.NavigateToStats -> onNavigateToStatsUpdated()
            }
        }
    }

    ResultScreen(
        state = state,
        onNavigateHomeClick = { viewModel.onIntent(ResultIntent.NavigateHome) },
        onPlayAgainClick = { viewModel.onIntent(ResultIntent.PlayAgain) },
        onViewStatsClick = { viewModel.onIntent(ResultIntent.ViewStats) },
        onRetryClick = { viewModel.onIntent(ResultIntent.Retry) },
    )
}
