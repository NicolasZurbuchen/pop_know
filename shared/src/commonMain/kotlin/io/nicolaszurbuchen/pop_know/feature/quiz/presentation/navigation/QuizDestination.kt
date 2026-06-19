package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface QuizDestination : NavKey

@Serializable
internal data object QuizMainDestination : QuizDestination

@Serializable
internal data object ResultDestination : QuizDestination
