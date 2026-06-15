package io.nicolaszurbuchen.pop_know.app.navigation.impl

import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.HomeNavigator
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.QuizMainDestination
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation.StatsMainDestination
import io.nicolaszurbuchen.pop_know.infra.navigation.AppNavigator

class HomeNavigatorImpl(
    private val appNavigator: AppNavigator
) : HomeNavigator {
    override fun navigateToPlay(gameId: Long) {
        appNavigator.navigateTo(QuizMainDestination(gameId = gameId))
    }

    override fun navigateToStats() {
        appNavigator.navigateTo(StatsMainDestination)
    }
}
