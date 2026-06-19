package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local.mapper

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.CategoryEntity
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.GetLastGame
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnsweredQuestionResult

fun CategoryEntity.toDomain(): Category =
    Category(
        id = id.toInt(),
        category = name,
    )

fun GetLastGame.toDomain(): AnsweredQuestionResult =
    AnsweredQuestionResult(
        question = question,
        correctAnswer = correct_answer,
        selectedAnswer = selected_answer,
        status = status.toAnswerStatusEnum(),
        categoryName = category_name,
        difficulty = difficulty.toDifficultyEnum(),
    )

fun QuestionType.toValue(): String =
    when (this) {
        QuestionType.MULTIPLE -> "multiple"
        QuestionType.BOOLEAN -> "boolean"
    }

fun Difficulty.toValue(): String =
    when (this) {
        Difficulty.EASY -> "easy"
        Difficulty.MEDIUM -> "medium"
        Difficulty.HARD -> "hard"
    }

fun AnswerStatus.toValue(): String =
    when (this) {
        AnswerStatus.CORRECT -> "CORRECT"
        AnswerStatus.INCORRECT -> "INCORRECT"
        AnswerStatus.TIMEOUT -> "TIMEOUT"
    }

fun String.toAnswerStatusEnum(): AnswerStatus =
    when (this) {
        "CORRECT" -> AnswerStatus.CORRECT
        "INCORRECT" -> AnswerStatus.INCORRECT
        else -> AnswerStatus.TIMEOUT
    }

fun String.toDifficultyEnum(): Difficulty =
    when (this) {
        "easy" -> Difficulty.EASY
        "medium" -> Difficulty.MEDIUM
        else -> Difficulty.HARD
    }
