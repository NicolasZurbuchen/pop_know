package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.DifficultyUiModel

data class QuizUiModel(
    val isLoading: Boolean,
    val initialError: AppError?,
    val insertionError: AppError?,
    val quizData: QuizDataUiModel?,
    val isQuitDialogOpen: Boolean,
)

data class QuizDataUiModel(
    val questionText: String,
    val categoryText: String,
    val difficulty: DifficultyUiModel,
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
)

data class QuizChoiceUiModel(
    val letter: String,
    val text: String,
    val status: QuizAnswerStatusUi?,
)

enum class QuizAnswerStatusUi {
    CORRECT,
    INCORRECT,
    TIMEOUT,
}
