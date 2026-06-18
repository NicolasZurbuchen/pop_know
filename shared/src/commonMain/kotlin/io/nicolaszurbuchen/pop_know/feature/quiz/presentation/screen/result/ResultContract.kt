package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result

import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.AnswerStatsUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.DifficultyUi
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.toUi
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnsweredQuestionResult
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.GameResult

sealed interface ResultIntent {
    data object NavigateHome : ResultIntent
    data object PlayAgain : ResultIntent
    data object ViewStats : ResultIntent
    data object Retry : ResultIntent
}

sealed interface ResultLabel {
    data object NavigateHome : ResultLabel
    data object PlayAgain : ResultLabel
    data object NavigateToStats : ResultLabel
}

sealed interface ResultAction {
    data object LoadResult : ResultAction
}

sealed interface ResultMessage {
    data object ResultLoading : ResultMessage
    data class ResultLoaded(val result: GameResultUi?) : ResultMessage
    data class Error(val error: AppError) : ResultMessage
}

data class ResultState(
    val isLoading: Boolean = false,
    val error: AppError? = null,
    val content: GameResultUi? = null,
)

data class GameResultUi(
    val questions: List<AnsweredQuestionResultUi>,
    val correctCount: Int,
    val incorrectCount: Int,
    val timeoutCount: Int,
    val score: AnswerStatsUiModel,
)

fun GameResult.toUi() = GameResultUi(
    questions = questions.map { it.toUi() },
    correctCount = correctCount,
    incorrectCount = incorrectCount,
    timeoutCount = timeoutCount,
    score = AnswerStatsUiModel(
        totalAnswered = score.totalAnswered,
        totalCorrect = score.totalCorrect,
        accuracy = score.accuracy,
    )
)

data class AnsweredQuestionResultUi(
    val question: String,
    val correctAnswer: String,
    val selectedAnswer: String?,
    val status: AnswerStatus,
    val categoryName: String,
    val difficulty: DifficultyUi,
)

fun AnsweredQuestionResult.toUi() = AnsweredQuestionResultUi(
    question = question,
    correctAnswer = correctAnswer,
    selectedAnswer = selectedAnswer,
    status = status,
    categoryName = categoryName,
    difficulty = difficulty.toUi(),
)
