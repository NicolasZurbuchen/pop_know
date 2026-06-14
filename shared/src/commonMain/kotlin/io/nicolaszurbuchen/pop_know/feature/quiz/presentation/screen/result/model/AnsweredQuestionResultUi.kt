package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result.model

import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.DifficultyUi
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.toUi
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnsweredQuestionResult

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
