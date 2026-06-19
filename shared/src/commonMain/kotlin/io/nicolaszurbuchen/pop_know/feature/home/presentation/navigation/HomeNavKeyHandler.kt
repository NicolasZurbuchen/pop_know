package io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.home.HomeRoute
import io.nicolaszurbuchen.pop_know.infra.navigation.NavKeyHandler

class HomeNavKeyHandler(
    private val navigator: HomeNavigator,
) : NavKeyHandler {
    override fun EntryProviderScope<NavKey>.registerEntries() {
        entry<HomeMainDestination> {
            HomeRoute(
                onNavigateToPlay = { navigator.navigateToPlay() },
                onNavigateToStats = { navigator.navigateToStats() },
            )
        }
    }
}
