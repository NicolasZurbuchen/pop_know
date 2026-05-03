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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.nicolaszurbuchen.pop_know.core.presentation.UiText
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.QuestionProgress
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.QuizContent
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.QuizIntent
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.QuizState

@Composable
fun QuizScreen(
    state: QuizState,
    onIntent: (QuizIntent) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            state.error != null -> Text(
                text = when (val e = state.error) {
                    is UiText.Dynamic -> e.value
                    is UiText.Resource -> "Error"
                    else -> "Error"
                },
                modifier = Modifier.align(Alignment.Center),
            )

            state.content != null -> QuizContent(
                content = state.content,
                isAnswered = state.isAnswered,
                isLastQuestion = state.isLastQuestion,
                onIntent = onIntent,
            )
        }
    }
}

@Composable
private fun QuizContent(
    content: QuizContent,
    isAnswered: Boolean,
    isLastQuestion: Boolean,
    onIntent: (QuizIntent) -> Unit,
) {
    val session = content.session
    val progress = session.currentQuestion
    val question = when (progress) {
        is QuestionProgress.Unanswered -> progress.question
        is QuestionProgress.Answered -> progress.question
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Question ${session.currentIndex + 1} / ${session.questionStates.size}")
            Text("${content.timerSeconds}s")
        }

        Text(question.difficulty.name.lowercase().replaceFirstChar { it.uppercaseChar() })
        Text(question.category.category)

        Spacer(Modifier.height(8.dp))

        Text(question.question)

        Spacer(Modifier.height(8.dp))

        content.shuffledAnswers.forEach { answer ->
            Button(
                onClick = { onIntent(QuizIntent.SelectAnswer(answer)) },
                enabled = !isAnswered,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(answer)
            }
        }

        if (isAnswered) {
            Spacer(Modifier.height(8.dp))
            if (isLastQuestion) {
                Button(
                    onClick = { onIntent(QuizIntent.SeeResult) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("See Result")
                }
            } else {
                Button(
                    onClick = { onIntent(QuizIntent.Next) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Next")
                }
            }
        }
    }
}
