package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.CategoryStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.DifficultyStats
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.model.StatsContent
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.model.StatsIntent
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.model.StatsState
import kotlin.math.roundToInt

@Composable
fun StatsScreen(
    state: StatsState,
    onIntent: (StatsIntent) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            state.error != null -> Text(
                text = when (val e = state.error) {
                    is io.nicolaszurbuchen.pop_know.common.presentation.UiText.Dynamic -> e.value
                    is io.nicolaszurbuchen.pop_know.common.presentation.UiText.Resource -> "Error"
                    else -> ""
                },
                modifier = Modifier.align(Alignment.Center),
            )

            else -> StatsContent(
                content = state.content,
                onIntent = onIntent,
            )
        }
    }
}

@Composable
private fun StatsContent(
    content: StatsContent?,
    onIntent: (StatsIntent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        if (content != null) {
            item { SummarySection(content.stats.summary) }
            item { Spacer(Modifier.height(16.dp)) }
            item { Text("By Difficulty") }
            item { Spacer(Modifier.height(8.dp)) }
            items(content.stats.perDifficulty) { DifficultyRow(it) }
            item { Spacer(Modifier.height(16.dp)) }
            item { Text("By Category") }
            item { Spacer(Modifier.height(8.dp)) }
            items(content.stats.perCategory) { CategoryRow(it) }
            item { Spacer(Modifier.height(24.dp)) }
        }

        item {
            Button(
                onClick = { onIntent(StatsIntent.NavigateBack) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Back")
            }
        }
    }
}

@Composable
private fun SummarySection(stats: io.nicolaszurbuchen.pop_know.common.domain.AnswerStats) {
    Column {
        Text("Total answered: ${stats.totalAnswered}")
        Text("Total correct: ${stats.totalCorrect}")
        Text("Accuracy: ${(stats.accuracy * 100).roundToInt()}%")
    }
}

@Composable
private fun DifficultyRow(stats: DifficultyStats) {
    StatsRow(
        label = stats.difficulty.displayName(),
        answerStats = stats.answerStats,
    )
}

@Composable
private fun CategoryRow(stats: CategoryStats) {
    StatsRow(
        label = stats.category.category,
        answerStats = stats.answerStats,
    )
}

@Composable
private fun StatsRow(
    label: String,
    answerStats: io.nicolaszurbuchen.pop_know.common.domain.AnswerStats,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(label)
        Text("${answerStats.totalCorrect}/${answerStats.totalAnswered} · ${(answerStats.accuracy * 100).roundToInt()}%")
    }
}

private fun io.nicolaszurbuchen.pop_know.common.domain.Difficulty.displayName(): String = name.lowercase().replaceFirstChar { it.uppercaseChar() }
