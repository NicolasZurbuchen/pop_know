package io.nicolaszurbuchen.pop_know.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.HomeRoute
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.screen.QuizRoute
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.screen.ResultRoute
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.StatsRoute

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home,
    ) {
        composable<Screen.Home> {
            HomeRoute(
                onNavigateToPlay = { navController.navigate(Screen.Play) },
                onNavigateToStats = { navController.navigate(Screen.Stats) },
            )
        }
        composable<Screen.Play> {
            QuizRoute(
                onNavigateToResult = { navController.navigate(Screen.Result) },
            )
        }
        composable<Screen.Result> {
            ResultRoute(
                onNavigateHome = {
                    navController.navigate(Screen.Home) {
                        popUpTo<Screen.Home> { inclusive = false }
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
