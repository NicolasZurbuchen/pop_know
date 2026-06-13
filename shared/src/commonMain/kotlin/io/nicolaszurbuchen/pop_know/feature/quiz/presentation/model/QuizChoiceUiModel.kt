package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.infra.design.theme.popKnowGameColors

data class QuizChoiceUiModel(
    val letter: String,
    val text: String,
    val answerStatus: AnswerStatus?,
) {
    val showCheckmark: Boolean get() = answerStatus == AnswerStatus.CORRECT
    val showCloseIcon: Boolean get() = answerStatus == AnswerStatus.INCORRECT

    @Composable
    fun color(): Color = when (answerStatus) {
        AnswerStatus.CORRECT -> MaterialTheme.popKnowGameColors.correct
        AnswerStatus.INCORRECT -> MaterialTheme.popKnowGameColors.wrong
        AnswerStatus.TIMEOUT -> MaterialTheme.popKnowGameColors.timeout
        null -> MaterialTheme.colorScheme.background
    }

    fun label(): String = when (answerStatus) {
        AnswerStatus.CORRECT -> "· POINTS WON"
        AnswerStatus.INCORRECT -> "· POINTS LOST"
        AnswerStatus.TIMEOUT -> "· TIMED OUT"
        null -> ""
    }

    fun headline(): String = when (answerStatus) {
        AnswerStatus.CORRECT -> "NICE."
        AnswerStatus.INCORRECT -> "NOPE."
        AnswerStatus.TIMEOUT -> "SLOW."
        null -> ""
    }
}
