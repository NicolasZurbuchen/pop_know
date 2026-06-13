package io.nicolaszurbuchen.pop_know.infra.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.infra.ui.theme.White
import io.nicolaszurbuchen.pop_know.infra.ui.theme.appColors

@Composable
fun PopKnowQuestionResultDot(
    status: AnswerStatus,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (status) {
        AnswerStatus.CORRECT -> MaterialTheme.appColors.correct
        AnswerStatus.INCORRECT -> MaterialTheme.appColors.incorrect
        AnswerStatus.TIMEOUT -> MaterialTheme.appColors.timeout
    }

    Box(
        modifier = modifier
            .size(24.dp)
            .background(
                color = backgroundColor,
                shape = MaterialTheme.shapes.extraSmall
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = when (status) {
                AnswerStatus.CORRECT -> Icons.Default.Check
                AnswerStatus.INCORRECT, AnswerStatus.TIMEOUT -> Icons.Default.Close
            },
            contentDescription = null,
            tint = White,
            modifier = Modifier.size(14.dp)
        )
    }
}