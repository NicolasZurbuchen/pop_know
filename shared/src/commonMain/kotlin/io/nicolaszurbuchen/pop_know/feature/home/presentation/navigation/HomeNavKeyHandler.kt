package io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.home.HomeRoute
import io.nicolaszurbuchen.pop_know.infra.navigation.NavKeyHandler
import kotlin.time.Clock

class HomeNavKeyHandler(
    private val navigator: HomeNavigator
) : NavKeyHandler {
    override fun EntryProviderScope<NavKey>.registerEntries() {
        entry<HomeMainDestination> {
            HomeRoute(
                onNavigateToPlay = {
                    val quizId = Clock.System.now().toEpochMilliseconds()
                    navigator.navigateToPlay(quizId)
                },
                onNavigateToStats = { navigator.navigateToStats() },
            )
        }
    }
}
