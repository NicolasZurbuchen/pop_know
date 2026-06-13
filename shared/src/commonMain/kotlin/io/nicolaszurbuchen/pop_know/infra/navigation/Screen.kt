package io.nicolaszurbuchen.pop_know.infra.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable data object Play : Screen()
    @Serializable data object Result : Screen()
    @Serializable data object Stats : Screen()
}
