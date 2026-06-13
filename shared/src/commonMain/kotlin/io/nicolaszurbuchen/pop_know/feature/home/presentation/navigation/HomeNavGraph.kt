package io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.home.HomeRoute

fun NavGraphBuilder.homeGraph(
    onNavigateToPlay: () -> Unit,
    onNavigateToStats: () -> Unit,
) {
    navigation<HomeGraph>(startDestination = HomeMainDestination) {
        composable<HomeMainDestination> {
            HomeRoute(
                onNavigateToPlay = onNavigateToPlay,
                onNavigateToStats = onNavigateToStats,
            )
        }
    }
}
