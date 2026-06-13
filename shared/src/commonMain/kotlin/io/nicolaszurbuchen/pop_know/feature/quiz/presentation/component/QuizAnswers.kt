package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.QuizChoiceUiModel
import io.nicolaszurbuchen.pop_know.infra.design.theme.popKnowGameColors
import io.nicolaszurbuchen.pop_know.infra.design.theme.spacing

@Composable
fun QuizAnswers(
    choice: QuizChoiceUiModel,
    isAnswered: Boolean,
    onSelect: () -> Unit,
) {
    val isHighlighted = choice.answerStatus != null
    val backgroundColor = choice.color()
    val showBorder = !isHighlighted
    val contentColor = when (choice.answerStatus) {
        AnswerStatus.CORRECT -> MaterialTheme.popKnowGameColors.onCorrect
        AnswerStatus.INCORRECT -> MaterialTheme.popKnowGameColors.onWrong
        AnswerStatus.TIMEOUT -> MaterialTheme.popKnowGameColors.onTimeout
        null -> MaterialTheme.colorScheme.onBackground
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (showBorder) {
                    Modifier.border(1.5.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium)
                } else {
                    Modifier
                }
            )
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor)
            .clickable(enabled = !isAnswered) { onSelect() }
            .padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md),
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .border(1.5.dp, contentColor, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = choice.letter,
                    style = MaterialTheme.typography.labelLarge,
                    color = contentColor,
                )
            }
            Text(
                text = choice.text,
                style = MaterialTheme.typography.titleMedium,
                color = contentColor,
            )
        }
        if (choice.showCheckmark) {
            Icon(Icons.Default.Check, contentDescription = null, tint = contentColor)
        } else if (choice.showCloseIcon) {
            Icon(Icons.Default.Close, contentDescription = null, tint = contentColor)
        }
    }
}
