package io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.nicolaszurbuchen.pop_know.core.ui.theme.appColors
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.AnswerStatus

data class QuizChoiceUiModel(
    val letter: String,
    val text: String,
    val answerStatus: AnswerStatus?,
) {
    val showCheckmark: Boolean get() = answerStatus == AnswerStatus.CORRECT
    val showCloseIcon: Boolean get() = answerStatus == AnswerStatus.INCORRECT

    @Composable
    fun color(): Color = when (answerStatus) {
        AnswerStatus.CORRECT -> MaterialTheme.appColors.correct
        AnswerStatus.INCORRECT -> MaterialTheme.appColors.incorrect
        AnswerStatus.TIMEOUT -> MaterialTheme.appColors.timeout
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
