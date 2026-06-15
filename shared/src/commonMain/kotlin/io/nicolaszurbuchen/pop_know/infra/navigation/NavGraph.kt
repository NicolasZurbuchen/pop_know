package io.nicolaszurbuchen.pop_know.infra.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.HomeGraph
import io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.home.HomeRoute
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.QuizMainDestination
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.ResultDestination
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.QuizRoute
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result.ResultRoute
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation.StatsGraph
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats.StatsRoute
import kotlin.time.Clock

@Composable
fun NavGraph() {
    val backStack = rememberNavBackStack(
        navConfig,
        HomeGraph
    )

    NavDisplay(
        backStack = backStack,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { key ->
            when (key) {
                HomeGraph -> {
                    NavEntry(key) {
                        HomeRoute(
                            onNavigateToPlay = {
                                val quizId = Clock.System.now().toEpochMilliseconds()
                                backStack.add(QuizMainDestination(gameId = quizId))
                            },
                            onNavigateToStats = { backStack.add(StatsGraph) },
                        )
                    }
                }

                is QuizMainDestination -> {
                    NavEntry(key) {
                        QuizRoute(
                            gameId = key.gameId,
                            onNavigateToResult = { backStack.add(ResultDestination) },
                            onNavigateBack = { backStack.removeLastOrNull() },
                        )
                    }
                }

                ResultDestination -> {
                    NavEntry(key) {
                        ResultRoute(
                            onNavigateHome = {
                                val index = backStack.indexOfFirst { it is HomeGraph }
                                if (index != -1) {
                                    while (backStack.size > index + 1) {
                                        backStack.removeAt(backStack.size - 1)
                                    }
                                }
                            },
                            onPlayAgain = {
                                backStack.removeLastOrNull()
                                val quizId = Clock.System.now().toEpochMilliseconds()
                                backStack.add(QuizMainDestination(gameId = quizId))
                            },
                            onNavigateToStats = { backStack.add(StatsGraph) },
                        )
                    }
                }

                StatsGraph -> {
                    NavEntry(key) {
                        StatsRoute(
                            onNavigateBack = { backStack.removeLastOrNull() },
                        )
                    }
                }

                else -> error("Unknown key: $key")
            }
        }
    )
}
