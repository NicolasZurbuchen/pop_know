package io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface StatsDestination

@Serializable
data object StatsGraph : StatsDestination

@Serializable
internal data object StatsMainDestination : StatsDestination
