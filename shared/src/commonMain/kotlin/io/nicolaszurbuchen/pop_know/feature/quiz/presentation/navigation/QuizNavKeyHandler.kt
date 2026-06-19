package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.QuizRoute
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result.ResultRoute
import io.nicolaszurbuchen.pop_know.infra.navigation.NavKeyHandler

class QuizNavKeyHandler(
    private val navigator: QuizNavigator
) : NavKeyHandler {
    override fun EntryProviderScope<NavKey>.registerEntries() {
        entry<QuizMainDestination> {
            QuizRoute(
                onNavigateToResult = { navigator.navigateToResult() },
                onNavigateBack = { navigator.navigateBack() },
            )
        }

        entry<ResultDestination> {
            ResultRoute(
                onNavigateHome = { navigator.navigateToHome() },
                onPlayAgain = { navigator.onPlayAgain() },
                onNavigateToStats = { navigator.navigateToStats() },
            )
        }
    }
}
