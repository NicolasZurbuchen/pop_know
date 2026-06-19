package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuizSession

sealed interface QuizIntent {
    data class SelectAnswer(
        val answer: String,
    ) : QuizIntent

    data object Next : QuizIntent

    data object SeeResult : QuizIntent

    data object Retry : QuizIntent

    data object DismissInsertionError : QuizIntent

    data class ShowQuitDialog(
        val show: Boolean,
    ) : QuizIntent

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
    data object QuizLoading : QuizMessage

    data class QuizLoaded(
        val session: QuizSession,
        val shuffledAnswers: List<List<String>>,
    ) : QuizMessage

    data class SessionUpdated(
        val session: QuizSession,
    ) : QuizMessage

    data class TimerTick(
        val secondsRemaining: Int,
    ) : QuizMessage

    data class ErrorOccurred(
        val error: AppError,
    ) : QuizMessage

    data class InsertionError(
        val error: AppError?,
    ) : QuizMessage

    data class ToggleQuitDialog(
        val show: Boolean,
    ) : QuizMessage
}

data class QuizState(
    val isLoading: Boolean = false,
    val initialError: AppError? = null,
    val insertionError: AppError? = null,
    val session: QuizSession? = null,
    val timerSeconds: Int = 15,
    val shuffledAnswers: List<List<String>> = emptyList(),
    val isQuitDialogOpen: Boolean = false,
)
