package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.component.QuizAnswers
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.component.QuizResultBar
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.component.QuizStoryBar
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.component.QuizTimer
import io.nicolaszurbuchen.pop_know.infra.design.theme.spacing
import io.nicolaszurbuchen.pop_know.infra.ui.UiText

@Composable
fun QuizScreen(
    state: QuizState,
    onSelectAnswer: (String) -> Unit,
    onNextClick: () -> Unit,
    onSeeResultClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            state.error != null -> Text(
                text = when (val e = state.error) {
                    is UiText.Raw -> e.value
                    is UiText.Resource -> "Error"
                    else -> "Error"
                },
                modifier = Modifier.align(Alignment.Center),
            )

            state.content != null -> QuizSessionContent(
                uiModel = state.content,
                onSelectAnswer = onSelectAnswer,
                onNextClick = onNextClick,
                onSeeResultClick = onSeeResultClick,
            )
        }
    }
}

@Composable
private fun QuizSessionContent(
    uiModel: QuizUiModel,
    onSelectAnswer: (String) -> Unit,
    onNextClick: () -> Unit,
    onSeeResultClick: () -> Unit,
) {
    val dotColor = uiModel.difficultyColor()

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = MaterialTheme.spacing.md,
                    vertical = MaterialTheme.spacing.md,
                ),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md),
        ) {
            QuizStoryBar(
                totalQuestions = uiModel.totalQuestions,
                currentIndex = uiModel.currentIndex,
                timerSeconds = uiModel.timerSeconds,
                maxTimerSeconds = uiModel.maxTimerSeconds,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = "QUESTION",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Text(
                        text = uiModel.progressText,
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
                QuizTimer(
                    seconds = uiModel.timerSeconds,
                    maxSeconds = uiModel.maxTimerSeconds,
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(dotColor, CircleShape),
                    )
                    Spacer(Modifier.width(MaterialTheme.spacing.xs))
                }
                Spacer(Modifier.width(MaterialTheme.spacing.xs))
                Text(
                    text = "${uiModel.difficultyName()} · ${uiModel.categoryText}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }

            Text(
                text = uiModel.questionText,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(Modifier.height(MaterialTheme.spacing.xs))

            uiModel.choices.forEach { choice ->
                QuizAnswers(
                    choice = choice,
                    isAnswered = uiModel.isAnswered,
                    onSelect = { onSelectAnswer(choice.text) },
                )
            }
        }

        if (uiModel.isAnswered && uiModel.resultChoice != null) {
            QuizResultBar(
                resultChoice = uiModel.resultChoice,
                isLastQuestion = uiModel.isLastQuestion,
                onNextClick = onNextClick,
                onSeeResultClick = onSeeResultClick,
            )
        }
    }
}
