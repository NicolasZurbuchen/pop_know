package io.nicolaszurbuchen.pop_know.feature.home.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.nicolaszurbuchen.pop_know.feature.home.presentation.HomeViewModel
import io.nicolaszurbuchen.pop_know.feature.home.presentation.model.HomeEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoute(
    onNavigateToPlay: () -> Unit,
    onNavigateToStats: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                HomeEffect.NavigateToPlay -> onNavigateToPlay()
                HomeEffect.NavigateToStats -> onNavigateToStats()
            }
        }
    }

    HomeScreen(
        state = state,
        onIntent = viewModel::onIntent,
    )
}
