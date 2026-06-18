package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result

import io.nicolaszurbuchen.pop_know.common.error.AppErrorUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.AnswerStatsUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.DifficultyUiModel
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus

data class ResultUiModel(
    val isLoading: Boolean,
    val error: AppErrorUiModel?,
    val content: GameResultUiModel?,
)

data class GameResultUiModel(
    val questions: List<AnsweredQuestionResultUiModel>,
    val correctCount: Int,
    val incorrectCount: Int,
    val timeoutCount: Int,
    val score: AnswerStatsUiModel,
)

data class AnsweredQuestionResultUiModel(
    val question: String,
    val correctAnswer: String,
    val selectedAnswer: String?,
    val status: AnswerStatus,
    val categoryName: String,
    val difficulty: DifficultyUiModel,
)
