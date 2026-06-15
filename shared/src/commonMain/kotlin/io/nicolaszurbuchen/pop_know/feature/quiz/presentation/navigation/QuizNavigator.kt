package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation

interface QuizNavigator {
    fun navigateToResult()
    fun navigateBack()
    fun onPlayAgain(gameId: Long)
    fun navigateToStats()
    fun navigateToHome()
}
