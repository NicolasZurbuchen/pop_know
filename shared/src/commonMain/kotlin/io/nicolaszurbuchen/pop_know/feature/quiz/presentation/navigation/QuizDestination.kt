package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface QuizDestination : NavKey

@Serializable
data object QuizGraph : QuizDestination

@Serializable
internal data class QuizMainDestination(
    val gameId: Long,
) : QuizDestination

@Serializable
internal data object ResultDestination : QuizDestination
