package io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats.StatsRoute

fun NavGraphBuilder.statsGraph(
    onNavigateBack: () -> Unit,
) {
    navigation<StatsGraph>(startDestination = StatsMainDestination) {
        composable<StatsMainDestination> {
            StatsRoute(
                onNavigateBack = onNavigateBack,
            )
        }
    }
}
