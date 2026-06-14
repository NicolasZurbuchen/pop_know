package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.DifficultyUi
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.infra.design.theme.popKnowGameColors

sealed interface QuizIntent {
    data class SelectAnswer(val answer: String) : QuizIntent
    data object Next : QuizIntent
    data object SeeResult : QuizIntent
    data object Retry : QuizIntent
    data object DismissInsertionError : QuizIntent
    data class ShowQuitDialog(val show: Boolean) : QuizIntent
    data object ConfirmQuit : QuizIntent
}

sealed interface QuizLabel {
    data object NavigateToResult : QuizLabel
    data object NavigateBack : QuizLabel
}

sealed interface QuizAction {
    data object StartQuiz : QuizAction
}

sealed interface QuizMessage {
    data object QuizStarted : QuizMessage
    data class QuizDataLoaded(
        val content: QuizUi,
    ) : QuizMessage
    data class ErrorOccurred(val error: AppError) : QuizMessage
    data class InsertionError(val error: AppError?) : QuizMessage
    data class ToggleQuitDialog(val show: Boolean) : QuizMessage
}

data class QuizState(
    val isLoading: Boolean = false,
    val initialError: AppError? = null,
    val insertionError: AppError? = null,
    val content: QuizUi? = null,
    val isQuitDialogOpen: Boolean = false,
)

data class QuizUi(
    val questionText: String,
    val categoryText: String,
    val difficulty: DifficultyUi,
    val progressText: String,
    val scoreText: String,
    val choices: List<QuizChoiceUi>,
    val resultChoice: QuizChoiceUi?,
    val totalQuestions: Int,
    val currentIndex: Int,
    val isAnswered: Boolean,
    val isLastQuestion: Boolean,
    val timerSeconds: Int,
    val maxTimerSeconds: Int,
) {
    @Composable
    fun difficultyColor(): Color = when (difficulty) {
        DifficultyUi.EASY -> MaterialTheme.popKnowGameColors.difficultyEasy
        DifficultyUi.MEDIUM -> MaterialTheme.popKnowGameColors.difficultyMedium
        DifficultyUi.HARD -> MaterialTheme.popKnowGameColors.difficultyHard
    }

    fun difficultyName(): String = difficulty.name
}

data class QuizChoiceUi(
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
