package io.nicolaszurbuchen.pop_know.infra.ui

import androidx.compose.runtime.Composable

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    // No-op for iOS as it doesn't have a system back button
}
