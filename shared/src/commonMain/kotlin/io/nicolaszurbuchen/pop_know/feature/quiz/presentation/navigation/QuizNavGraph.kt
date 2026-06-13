package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.QuizRoute
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result.ResultRoute

fun NavGraphBuilder.quizGraph(
    navController: NavController,
    onNavigateHome: () -> Unit,
    onNavigateToStats: () -> Unit,
) {
    navigation<QuizGraph>(startDestination = QuizMainDestination(gameId = 0L)) {
        composable<QuizMainDestination> {
            QuizRoute(
                gameId = it.toRoute<QuizMainDestination>().gameId,
                onNavigateToResult = { navController.navigate(ResultDestination) },
            )
        }

        composable<ResultDestination> {
            ResultRoute(
                onNavigateHome = onNavigateHome,
                onPlayAgain = {
                    navController.navigate(QuizGraph) {
                        popUpTo<ResultDestination> { inclusive = true }
                    }
                },
                onNavigateToStats = onNavigateToStats,
            )
        }
    }
}
