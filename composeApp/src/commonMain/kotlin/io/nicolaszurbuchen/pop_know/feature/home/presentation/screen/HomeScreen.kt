package io.nicolaszurbuchen.pop_know.feature.home.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.nicolaszurbuchen.pop_know.core.presentation.UiText
import io.nicolaszurbuchen.pop_know.core.domain.AnswerStats
import io.nicolaszurbuchen.pop_know.feature.home.presentation.model.HomeContent
import io.nicolaszurbuchen.pop_know.feature.home.presentation.model.HomeIntent
import io.nicolaszurbuchen.pop_know.feature.home.presentation.model.HomeState
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
            Spacer(Modifier.height(16.dp))
        }

        state.error?.let { error ->
            Text(
                text = when (error) {
                    is UiText.Dynamic -> error.value
                    is UiText.Resource -> "Error"
                }
            )
            Spacer(Modifier.height(16.dp))
        }

        if (state.content != null) {
            val stats = state.content.stats
            Text("${stats.totalAnswered} answered")
            Text("${stats.totalCorrect} correct")
            Text("${(stats.accuracy * 100).roundToInt()}% accuracy")
            Spacer(Modifier.height(24.dp))
        }

        Button(onClick = { onIntent(HomeIntent.NavigateToPlay) }) {
            Text("Play")
        }

        if (state.hasHistory) {
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = { onIntent(HomeIntent.NavigateToStats) }) {
                Text("View Stats")
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenEmptyPreview() {
    HomeScreen(
        state = HomeState(),
        onIntent = {},
    )
}

@Preview
@Composable
private fun HomeScreenWithStatsPreview() {
    HomeScreen(
        state = HomeState(
            content = HomeContent(
                stats = AnswerStats(
                    totalAnswered = 42,
                    totalCorrect = 31,
                    accuracy = 0.738f,
                )
            )
        ),
        onIntent = {},
    )
}
