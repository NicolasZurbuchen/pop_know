package io.nicolaszurbuchen.pop_know.feature.quizz.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.QuizViewModel
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.QuizEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun QuizRoute(
    onNavigateToResult: () -> Unit,
    viewModel: QuizViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                QuizEffect.NavigateToResult -> onNavigateToResult()
            }
        }
    }

    QuizScreen(
        state = state,
        onIntent = viewModel::onIntent,
    )
}
