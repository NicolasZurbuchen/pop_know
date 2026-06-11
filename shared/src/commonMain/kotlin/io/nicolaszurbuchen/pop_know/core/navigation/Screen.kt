package io.nicolaszurbuchen.pop_know.core.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable data object Home : Screen()
    @Serializable data object Play : Screen()
    @Serializable data object Result : Screen()
    @Serializable data object Stats : Screen()
}
