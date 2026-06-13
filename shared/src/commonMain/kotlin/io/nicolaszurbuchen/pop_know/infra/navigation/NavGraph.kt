package io.nicolaszurbuchen.pop_know.infra.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.HomeGraph
import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.homeGraph
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.QuizRoute
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.ResultRoute
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.StatsRoute

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = HomeGraph,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
    ) {
        homeGraph(
            onNavigateToPlay = { navController.navigate(Screen.Play) },
            onNavigateToStats = { navController.navigate(Screen.Stats) },
        )

        composable<Screen.Play> {
            QuizRoute(
                onNavigateToResult = { navController.navigate(Screen.Result) },
            )
        }
        composable<Screen.Result> {
            ResultRoute(
                onNavigateHome = {
                    navController.navigate(HomeGraph) {
                        popUpTo<HomeGraph> { inclusive = false }
                    }
                },
                onPlayAgain = {
                    navController.navigate(Screen.Play) {
                        popUpTo<Screen.Result> { inclusive = true }
                    }
                },
                onNavigateToStats = {
                    navController.navigate(Screen.Stats) {
                        popUpTo<Screen.Result> { inclusive = true }
                    }
                },
            )
        }
        composable<Screen.Stats> {
            StatsRoute(
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}
