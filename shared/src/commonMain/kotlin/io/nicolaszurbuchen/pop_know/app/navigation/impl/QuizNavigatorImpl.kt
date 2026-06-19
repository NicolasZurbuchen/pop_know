package io.nicolaszurbuchen.pop_know.app.navigation.impl

import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.HomeMainDestination
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.QuizMainDestination
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.QuizNavigator
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.ResultDestination
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation.StatsMainDestination
import io.nicolaszurbuchen.pop_know.infra.navigation.AppNavigator
import kotlin.time.Clock

class QuizNavigatorImpl(
    private val appNavigator: AppNavigator
) : QuizNavigator {
    override fun navigateToResult() {
        appNavigator.navigateTo(ResultDestination)
    }

    override fun navigateBack() {
        appNavigator.navigateBack()
    }

    override fun onPlayAgain() {
        appNavigator.popUpTo { it is HomeMainDestination }
        appNavigator.navigateTo(QuizMainDestination(timestamp = Clock.System.now().toEpochMilliseconds()))
    }

    override fun navigateToStats() {
        appNavigator.navigateTo(StatsMainDestination)
    }

    override fun navigateToHome() {
        appNavigator.popUpTo { it is HomeMainDestination }
    }
}
