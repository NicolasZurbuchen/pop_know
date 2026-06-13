package io.nicolaszurbuchen.pop_know.infra

import androidx.compose.runtime.Composable
import io.nicolaszurbuchen.pop_know.infra.navigation.NavGraph
import io.nicolaszurbuchen.pop_know.infra.design.theme.PopKnowTheme

@Composable
fun App() {
    PopKnowTheme {
        NavGraph()
    }
}