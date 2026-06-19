package io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats.StatsRoute
import io.nicolaszurbuchen.pop_know.infra.navigation.NavKeyHandler

class StatsNavKeyHandler(
    private val navigator: StatsNavigator,
) : NavKeyHandler {
    override fun EntryProviderScope<NavKey>.registerEntries() {
        entry<StatsMainDestination> {
            StatsRoute(
                onNavigateBack = { navigator.navigateBack() },
                onNavigateToHome = { navigator.navigateToHome() },
            )
        }
    }
}
