package io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.mapper

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.dto.TriviaQuestionDto
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion
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
                ?: Category(
                    id = -1,
                    category = category
                ),
            question = question.decodeHtml(),
            correctAnswer = correctAnswer.decodeHtml(),
            incorrectAnswers = incorrectAnswers.map { it.decodeHtml() }
        )
    }
