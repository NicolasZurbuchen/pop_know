package io.nicolaszurbuchen.pop_know

import androidx.compose.runtime.Composable
import io.nicolaszurbuchen.pop_know.infra.navigation.NavGraph
import io.nicolaszurbuchen.pop_know.infra.ui.theme.PopKnowTheme

@Composable
fun App() {
    PopKnowTheme {
        NavGraph()
    }
}