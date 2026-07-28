package io.nicolaszurbuchen.pop_know.app.navigation.impl

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.HomeMainDestination
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.QuizMainDestination
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.ResultDestination
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation.StatsMainDestination
import io.nicolaszurbuchen.pop_know.infra.navigation.AppNavigator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class QuizNavigatorImplTest {

    private fun navigatorWithBackStack(vararg initial: NavKey): Pair<AppNavigator, NavBackStack<NavKey>> {
        val backStack = NavBackStack<NavKey>(*initial) // constructor signature unverified — see note
        val navigator = AppNavigator()
        navigator.attach(backStack)
        return navigator to backStack
    }

    @Test
    fun navigateToResult_pushesResultDestination() {
        val (navigator, backStack) = navigatorWithBackStack(QuizMainDestination(timestamp = 0L))
        val quizNavigator = QuizNavigatorImpl(navigator)

        quizNavigator.navigateToResult()

        assertEquals(ResultDestination, backStack.last())
    }

    @Test
    fun navigateBack_removesLastEntry() {
        val (navigator, backStack) = navigatorWithBackStack(
            HomeMainDestination, QuizMainDestination(timestamp = 0L),
        )
        val quizNavigator = QuizNavigatorImpl(navigator)

        quizNavigator.navigateBack()

        assertEquals(listOf(HomeMainDestination), backStack.toList())
    }

    @Test
    fun navigateToStats_pushesStatsMainDestination() {
        val (navigator, backStack) = navigatorWithBackStack(HomeMainDestination)
        val quizNavigator = QuizNavigatorImpl(navigator)

        quizNavigator.navigateToStats()

        assertEquals(StatsMainDestination, backStack.last())
    }

    @Test
    fun navigateToHome_popsBackToHomeDestination() {
        val (navigator, backStack) = navigatorWithBackStack(
            HomeMainDestination,
            QuizMainDestination(timestamp = 0L),
            ResultDestination,
        )
        val quizNavigator = QuizNavigatorImpl(navigator)

        quizNavigator.navigateToHome()

        assertEquals(listOf(HomeMainDestination), backStack.toList())
    }

    @Test
    fun onPlayAgain_popsToHomeThenPushesFreshQuizDestination() {
        val (navigator, backStack) = navigatorWithBackStack(
            HomeMainDestination,
            QuizMainDestination(timestamp = 0L),
            ResultDestination,
        )
        val quizNavigator = QuizNavigatorImpl(navigator)

        quizNavigator.onPlayAgain()

        assertEquals(2, backStack.size)
        assertEquals(HomeMainDestination, backStack.first())
        assertIs<QuizMainDestination>(backStack.last())
    }

    @Test
    fun popUpTo_noMatchingEntry_leavesStackUnchanged() {
        val (navigator, backStack) = navigatorWithBackStack(
            QuizMainDestination(timestamp = 0L), ResultDestination,
        )
        val quizNavigator = QuizNavigatorImpl(navigator)

        quizNavigator.navigateToHome() // predicate looks for HomeMainDestination, absent here

        assertEquals(2, backStack.size) // unchanged, per the `index != -1` guard
    }
}