package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun QuizRoute(
    gameId: Long,
    onNavigateToResult: () -> Unit,
    viewModel: QuizViewModel = koinViewModel { parametersOf(gameId) },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onNavigateToResultUpdated by rememberUpdatedState(onNavigateToResult)

    LaunchedEffect(Unit) {
        viewModel.labels.collect { label ->
            when (label) {
                QuizLabel.NavigateToResult -> onNavigateToResultUpdated()
            }
        }
    }

    QuizScreen(
        state = state,
        onSelectAnswer = { viewModel.onIntent(QuizIntent.SelectAnswer(it)) },
        onNextClick = { viewModel.onIntent(QuizIntent.Next) },
        onSeeResultClick = { viewModel.onIntent(QuizIntent.SeeResult) },
    )
}
