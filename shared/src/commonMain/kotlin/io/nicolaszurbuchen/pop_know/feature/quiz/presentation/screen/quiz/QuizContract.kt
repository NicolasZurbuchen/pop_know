package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.nicolaszurbuchen.pop_know.common.domain.Difficulty
import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.infra.design.theme.popKnowGameColors

sealed interface QuizIntent {
    data class SelectAnswer(val answer: String) : QuizIntent
    data object Next : QuizIntent
    data object SeeResult : QuizIntent
}

sealed interface QuizLabel {
    data object NavigateToResult : QuizLabel
}

sealed interface QuizAction {
    data object StartQuiz : QuizAction
}

sealed interface QuizMessage {
    data object QuizStarted : QuizMessage
    data class QuizDataLoaded(
        val content: QuizUiModel,
    ) : QuizMessage
    data class ErrorOccurred(val error: UiText) : QuizMessage
}

data class QuizState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val content: QuizUiModel? = null,
)

data class QuizUiModel(
    val questionText: String,
    val categoryText: String,
    val difficulty: Difficulty,
    val progressText: String,
    val scoreText: String,
    val choices: List<QuizChoiceUiModel>,
    val resultChoice: QuizChoiceUiModel?,
    val totalQuestions: Int,
    val currentIndex: Int,
    val isAnswered: Boolean,
    val isLastQuestion: Boolean,
    val timerSeconds: Int,
    val maxTimerSeconds: Int,
) {
    @Composable
    fun difficultyColor(): Color = when (difficulty) {
        Difficulty.EASY -> MaterialTheme.popKnowGameColors.difficultyEasy
        Difficulty.MEDIUM -> MaterialTheme.popKnowGameColors.difficultyMedium
        Difficulty.HARD -> MaterialTheme.popKnowGameColors.difficultyHard
    }

    fun difficultyName(): String = difficulty.name
}

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
