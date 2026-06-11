package io.nicolaszurbuchen.pop_know.feature.quizz.presentation.screen

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
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.AnsweredQuestionResult
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.GameResult
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.ResultIntent
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.ResultState
import kotlin.math.roundToInt

@Composable
fun ResultScreen(
    state: ResultState,
    onIntent: (ResultIntent) -> Unit,
) {
    when {
        state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        state.content == null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No results available.")
        }
        else -> ResultContentUi(content = state.content, onIntent = onIntent)
    }
}

@Composable
private fun ResultContentUi(
    content: GameResult,
    onIntent: (ResultIntent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            Text("${content.score.totalCorrect} / ${content.score.totalAnswered} correct")
            Text("${(content.score.accuracy * 100).roundToInt()}% accuracy")
            Spacer(Modifier.height(4.dp))
            Text("Correct: ${content.correctCount}")
            Text("Incorrect: ${content.incorrectCount}")
            Text("Timeout: ${content.timeoutCount}")
        }

        item { Spacer(Modifier.height(8.dp)) }

        items(content.questions) { result ->
            QuestionResultRow(result)
        }

        item { Spacer(Modifier.height(8.dp)) }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Button(
                    onClick = { onIntent(ResultIntent.NavigateHome) },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Home")
                }
                Button(
                    onClick = { onIntent(ResultIntent.PlayAgain) },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Play Again")
                }
            }
        }
    }
}

@Composable
private fun QuestionResultRow(result: AnsweredQuestionResult) {
    val statusLabel = when (result.status) {
        AnswerStatus.CORRECT -> "✓"
        AnswerStatus.INCORRECT -> "✗"
        AnswerStatus.TIMEOUT -> "⏱"
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(statusLabel)
            Text(result.question, modifier = Modifier.weight(1f))
        }
        Text("Correct: ${result.correctAnswer}")
        if (result.status == AnswerStatus.INCORRECT && result.selectedAnswer != null) {
            Text("Your answer: ${result.selectedAnswer}")
        }
    }
}
