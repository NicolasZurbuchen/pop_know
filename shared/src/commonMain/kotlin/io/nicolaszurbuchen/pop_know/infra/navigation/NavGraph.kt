package io.nicolaszurbuchen.pop_know.infra.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.HomeGraph
import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.homeGraph
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.QuizMainDestination
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.quizGraph
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation.StatsGraph
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation.statsGraph
import kotlin.time.Clock

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
            onNavigateToPlay = {
                val quizId = Clock.System.now().toEpochMilliseconds()
                navController.navigate(QuizMainDestination(gameId = quizId))
            },
            onNavigateToStats = { navController.navigate(StatsGraph) },
        )

        quizGraph(
            navController = navController,
            onNavigateHome = {
                navController.navigate(HomeGraph) {
                    popUpTo<HomeGraph> { inclusive = false }
                }
            },
            onNavigateToStats = {
                navController.navigate(StatsGraph)
            },
            onNavigateBack = {
                navController.popBackStack()
            }
        )

        statsGraph(
            onNavigateBack = { navController.popBackStack() },
        )
    }
}
