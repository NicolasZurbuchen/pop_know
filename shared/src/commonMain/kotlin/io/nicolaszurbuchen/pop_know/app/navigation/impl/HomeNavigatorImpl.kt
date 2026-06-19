package io.nicolaszurbuchen.pop_know.app.navigation.impl

import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.HomeNavigator
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.QuizMainDestination
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation.StatsMainDestination
import io.nicolaszurbuchen.pop_know.infra.navigation.AppNavigator
import kotlin.time.Clock

class HomeNavigatorImpl(
    private val appNavigator: AppNavigator,
) : HomeNavigator {
    override fun navigateToPlay() {
        appNavigator.navigateTo(QuizMainDestination(timestamp = Clock.System.now().toEpochMilliseconds()))
    }

    override fun navigateToStats() {
        appNavigator.navigateTo(StatsMainDestination)
    }
}
