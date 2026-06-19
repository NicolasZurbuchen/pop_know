package io.nicolaszurbuchen.pop_know.app.navigation.impl

import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.HomeMainDestination
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation.StatsNavigator
import io.nicolaszurbuchen.pop_know.infra.navigation.AppNavigator

class StatsNavigatorImpl(
    private val appNavigator: AppNavigator,
) : StatsNavigator {
    override fun navigateBack() {
        appNavigator.navigateBack()
    }

    override fun navigateToHome() {
        appNavigator.popUpTo { it is HomeMainDestination }
    }
}
