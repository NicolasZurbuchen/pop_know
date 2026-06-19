package io.nicolaszurbuchen.pop_know.app

import androidx.compose.runtime.Composable
import io.nicolaszurbuchen.pop_know.app.design.theme.PopKnowTheme
import io.nicolaszurbuchen.pop_know.infra.navigation.NavGraph

@Composable
fun App() {
    PopKnowTheme {
        NavGraph()
    }
}
