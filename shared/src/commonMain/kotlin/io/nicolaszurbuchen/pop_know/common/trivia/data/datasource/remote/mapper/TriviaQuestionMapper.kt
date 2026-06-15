package io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.mapper

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.dto.TriviaQuestionDto
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion
import io.nicolaszurbuchen.pop_know.util.decodeHtml

class TriviaQuestionMapper {

    fun toDomain(dto: TriviaQuestionDto, categories: List<Category>): TriviaQuestion {
        return TriviaQuestion(
            questionType = when (dto.type) {
                "multiple" -> QuestionType.MULTIPLE
                "boolean" -> QuestionType.BOOLEAN
                else -> throw IllegalArgumentException("Unknown question type: ${dto.type}")
            },
            difficulty = when (dto.difficulty) {
                "easy" -> Difficulty.EASY
                "medium" -> Difficulty.MEDIUM
                "hard" -> Difficulty.HARD
                else -> throw IllegalArgumentException("Unknown difficulty: ${dto.difficulty}")
            },
            category = categories.find { it.category == dto.category }
                ?: Category(
                    id = -1,
                    category = dto.category
                ),
            question = dto.question.decodeHtml(),
            correctAnswer = dto.correctAnswer.decodeHtml(),
            incorrectAnswers = dto.incorrectAnswers.map { it.decodeHtml() }
        )
    }
}
