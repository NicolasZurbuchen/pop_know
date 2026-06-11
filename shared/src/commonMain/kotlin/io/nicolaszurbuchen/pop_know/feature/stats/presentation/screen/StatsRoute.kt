package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.StatsViewModel
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.model.StatsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StatsRoute(
    onNavigateBack: () -> Unit,
    viewModel: StatsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                StatsEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    StatsScreen(
        state = state,
        onIntent = viewModel::onIntent,
    )
}
