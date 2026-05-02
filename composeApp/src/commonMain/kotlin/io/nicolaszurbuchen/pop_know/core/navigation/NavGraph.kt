package io.nicolaszurbuchen.pop_know.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.nicolaszurbuchen.pop_know.feature.home.presentation.ui.HomeRoute

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
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Play — coming soon")
            }
        }
        composable<Screen.Result> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Result — coming soon")
            }
        }
        composable<Screen.Stats> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Stats — coming soon")
            }
        }
    }
}
