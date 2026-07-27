package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.mapper

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.CategoryEntity
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.CategoryDto
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.TriviaQuestionDto
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion
import io.nicolaszurbuchen.pop_know.infra.platform.decodeHtml

fun CategoryDto.toEntity(decodeHtml: (String) -> String = String::decodeHtml): CategoryEntity =
    CategoryEntity(
        id = id.toLong(),
        name = decodeHtml(category),
    )

fun TriviaQuestionDto.toDomain(
    categories: List<Category>,
    decodeHtml: (String) -> String = String::decodeHtml,
): TriviaQuestion =
    TriviaQuestion(
        questionType =
            when (type) {
                "multiple" -> QuestionType.MULTIPLE
                "boolean" -> QuestionType.BOOLEAN
                else -> throw IllegalArgumentException("Unknown question type: $type")
            },
        difficulty =
            when (difficulty) {
                "easy" -> Difficulty.EASY
                "medium" -> Difficulty.MEDIUM
                "hard" -> Difficulty.HARD
                else -> throw IllegalArgumentException("Unknown difficulty: $difficulty")
            },
        category =
            categories.find { it.category == category }
                ?: Category(
                    id = -1,
                    category = category,
                ),
        question = decodeHtml(question),
        correctAnswer = decodeHtml(correctAnswer),
        incorrectAnswers = incorrectAnswers.map { decodeHtml(it) },
    )
