package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.QuizViewModel
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.model.QuizEffect
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.time.Clock

@Composable
fun QuizRoute(
    onNavigateToResult: () -> Unit,
) {
    val quizId = remember { Clock.System.now().toEpochMilliseconds() }
    val viewModel: QuizViewModel = koinViewModel { parametersOf(quizId) }
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
