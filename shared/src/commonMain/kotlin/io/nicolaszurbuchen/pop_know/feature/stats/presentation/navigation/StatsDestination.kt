package io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface StatsDestination : NavKey

@Serializable
data object StatsGraph : StatsDestination

@Serializable
internal data object StatsMainDestination : StatsDestination
