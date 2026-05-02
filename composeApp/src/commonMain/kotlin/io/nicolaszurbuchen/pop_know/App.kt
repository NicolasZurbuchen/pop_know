package io.nicolaszurbuchen.pop_know

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import io.nicolaszurbuchen.pop_know.core.navigation.NavGraph

@Composable
fun App() {
    MaterialTheme {
        NavGraph()
    }
}