package io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeDestination

@Serializable
data object HomeGraph : HomeDestination

@Serializable
internal data object HomeMainDestination : HomeDestination
