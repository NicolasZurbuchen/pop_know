package io.nicolaszurbuchen.pop_know

import androidx.compose.runtime.Composable
import io.nicolaszurbuchen.pop_know.core.navigation.NavGraph
import io.nicolaszurbuchen.pop_know.core.ui.theme.PopKnowTheme

@Composable
fun App() {
    PopKnowTheme {
        NavGraph()
    }
}