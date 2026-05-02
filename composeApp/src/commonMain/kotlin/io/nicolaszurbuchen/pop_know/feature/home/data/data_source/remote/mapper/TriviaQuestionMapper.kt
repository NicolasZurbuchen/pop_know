package io.nicolaszurbuchen.pop_know.feature.home.data.data_source.remote.mapper

import io.nicolaszurbuchen.pop_know.feature.home.data.data_source.remote.model.TriviaQuestionDto
import io.nicolaszurbuchen.pop_know.core.domain.Category
import io.nicolaszurbuchen.pop_know.core.domain.Difficulty
import io.nicolaszurbuchen.pop_know.core.domain.QuestionType
import io.nicolaszurbuchen.pop_know.core.domain.TriviaQuestion
import io.nicolaszurbuchen.pop_know.util.decodeHtml

fun TriviaQuestionDto.toDomain(categories: List<Category>): TriviaQuestion {
    return TriviaQuestion(
        questionType = when (type) {
            "multiple" -> QuestionType.MULTIPLE
            "boolean" -> QuestionType.BOOLEAN
            else -> throw IllegalArgumentException("Unknown question type: $type")
        },
        difficulty = when (difficulty) {
            "easy" -> Difficulty.EASY
            "medium" -> Difficulty.MEDIUM
            "hard" -> Difficulty.HARD
            else -> throw IllegalArgumentException("Unknown difficulty: $difficulty")
        },
        category = categories.find { it.category == category }
            ?: Category(id = -1, category = category),
        question = question.decodeHtml(),
        correctAnswer = correctAnswer.decodeHtml(),
        incorrectAnswers = incorrectAnswers.map { it.decodeHtml() }
    )
}