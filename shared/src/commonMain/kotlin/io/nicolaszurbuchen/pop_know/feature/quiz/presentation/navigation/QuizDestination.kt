package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface QuizDestination

@Serializable
data object QuizGraph : QuizDestination

@Serializable
internal data class QuizMainDestination(
    val gameId: Long,
) : QuizDestination

@Serializable
internal data object ResultDestination : QuizDestination
