package io.nicolaszurbuchen.pop_know.infra.ui

import androidx.compose.runtime.Composable

@Composable
expect fun BackHandler(enabled: Boolean = true, onBack: () -> Unit)
