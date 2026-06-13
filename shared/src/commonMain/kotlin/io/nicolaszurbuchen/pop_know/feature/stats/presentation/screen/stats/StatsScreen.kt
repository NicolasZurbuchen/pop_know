package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

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
import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.CategoryStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.DifficultyStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.FullStats
import kotlin.math.roundToInt

@Composable
fun StatsScreen(
    state: StatsState,
    onBackClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            state.error != null -> Text(
                text = when (val e = state.error) {
                    is UiText.Raw -> e.value
                    is UiText.Resource -> "Error"
                    else -> ""
                },
                modifier = Modifier.align(Alignment.Center),
            )

            else -> StatsContent(
                stats = state.stats,
                onBackClick = onBackClick,
            )
        }
    }
}

@Composable
private fun StatsContent(
    stats: FullStats?,
    onBackClick: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        if (stats != null) {
            item { SummarySection(stats.summary) }
            item { Spacer(Modifier.height(16.dp)) }
            item { Text("By Difficulty") }
            item { Spacer(Modifier.height(8.dp)) }
            items(stats.perDifficulty) { DifficultyRow(it) }
            item { Spacer(Modifier.height(16.dp)) }
            item { Text("By Category") }
            item { Spacer(Modifier.height(8.dp)) }
            items(stats.perCategory) { CategoryRow(it) }
            item { Spacer(Modifier.height(24.dp)) }
        }

        item {
            Button(
                onClick = onBackClick,
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
