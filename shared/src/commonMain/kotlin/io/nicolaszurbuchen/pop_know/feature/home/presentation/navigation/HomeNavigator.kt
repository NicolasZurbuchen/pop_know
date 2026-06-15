package io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation

interface HomeNavigator {
    fun navigateToPlay(gameId: Long)
    fun navigateToStats()
}
