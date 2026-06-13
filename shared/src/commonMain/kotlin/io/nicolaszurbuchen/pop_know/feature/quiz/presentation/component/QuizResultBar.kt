package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.model.QuizChoiceUiModel
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.model.QuizIntent
import io.nicolaszurbuchen.pop_know.infra.ui.theme.Black
import io.nicolaszurbuchen.pop_know.infra.ui.theme.White
import io.nicolaszurbuchen.pop_know.infra.ui.theme.spacing

@Composable
fun QuizResultBar(
    resultChoice: QuizChoiceUiModel,
    isLastQuestion: Boolean,
    onIntent: (QuizIntent) -> Unit,
) {
    val barColor = resultChoice.color()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(barColor)
            .padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.lg),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = resultChoice.label(),
                style = MaterialTheme.typography.displaySmall,
                color = Black,
            )
            Text(
                text = resultChoice.headline(),
                style = MaterialTheme.typography.headlineLarge,
                color = Black,
            )
        }
        Button(
            onClick = {
                if (isLastQuestion) onIntent(QuizIntent.SeeResult) else onIntent(QuizIntent.Next)
            },
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = Black,
                contentColor = White,
            ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs),
            ) {
                Text(
                    text = "NEXT",
                    style = MaterialTheme.typography.titleLarge,
                    color = White,
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = White,
                )
            }
        }
    }
}
