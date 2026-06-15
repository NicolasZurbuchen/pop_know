package io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeDestination : NavKey

@Serializable
data object HomeMainDestination : HomeDestination
