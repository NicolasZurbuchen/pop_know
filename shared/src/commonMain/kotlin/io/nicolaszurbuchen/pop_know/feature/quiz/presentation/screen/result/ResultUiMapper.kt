package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result

import io.nicolaszurbuchen.pop_know.common.error.toUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.AnswerStatsUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.toUiModel
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnsweredQuestionResult
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.GameResult

fun ResultState.toUiModel() = ResultUiModel(
    isLoading = isLoading,
    error = error?.toUiModel(),
    content = content?.toUiModel()
)

fun GameResult.toUiModel() = GameResultUiModel(
    questions = questions.map { it.toUiModel() },
    correctCount = correctCount,
    incorrectCount = incorrectCount,
    timeoutCount = timeoutCount,
    score = AnswerStatsUiModel(
        totalAnswered = score.totalAnswered,
        totalCorrect = score.totalCorrect,
        accuracy = score.accuracy,
    )
)

fun AnsweredQuestionResult.toUiModel() = AnsweredQuestionResultUiModel(
    question = question,
    correctAnswer = correctAnswer,
    selectedAnswer = selectedAnswer,
    status = status,
    categoryName = categoryName,
    difficulty = difficulty.toUiModel(),
)
