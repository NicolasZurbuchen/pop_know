package io.nicolaszurbuchen.pop_know.feature.quizz.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.ResultViewModel
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.ResultEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ResultRoute(
    onNavigateHome: () -> Unit,
    onPlayAgain: () -> Unit,
    onNavigateToStats: () -> Unit,
    viewModel: ResultViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                ResultEffect.NavigateHome -> onNavigateHome()
                ResultEffect.PlayAgain -> onPlayAgain()
                ResultEffect.NavigateToStats -> onNavigateToStats()
            }
        }
    }

    ResultScreen(
        state = state,
        onIntent = viewModel::onIntent,
    )
}
