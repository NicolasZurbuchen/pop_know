package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local.mapper

import io.nicolaszurbuchen.pop_know.cache.CategoryEntity
import io.nicolaszurbuchen.pop_know.cache.GetLastGame
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnsweredQuestionResult

class QuizLocalMapper {
    fun mapEntityToDomain(entity: CategoryEntity): Category {
        return Category(
            id = entity.id.toInt(),
            category = entity.name
        )
    }

    fun mapEntityToDomain(entity: GetLastGame): AnsweredQuestionResult {
        return AnsweredQuestionResult(
            question = entity.question,
            correctAnswer = entity.correct_answer,
            selectedAnswer = entity.selected_answer,
            status = mapToAnswerStatus(entity.status),
            categoryName = entity.category_name,
            difficulty = mapToDifficulty(entity.difficulty),
        )
    }

    fun mapToDbString(type: QuestionType): String = when (type) {
        QuestionType.MULTIPLE -> "multiple"
        QuestionType.BOOLEAN -> "boolean"
    }

    fun mapToDbString(difficulty: Difficulty): String = when (difficulty) {
        Difficulty.EASY -> "easy"
        Difficulty.MEDIUM -> "medium"
        Difficulty.HARD -> "hard"
    }

    fun mapToDbString(status: AnswerStatus): String = when (status) {
        AnswerStatus.CORRECT -> "CORRECT"
        AnswerStatus.INCORRECT -> "INCORRECT"
        AnswerStatus.TIMEOUT -> "TIMEOUT"
    }

    private fun mapToAnswerStatus(status: String): AnswerStatus = when (status) {
        "CORRECT" -> AnswerStatus.CORRECT
        "INCORRECT" -> AnswerStatus.INCORRECT
        else -> AnswerStatus.TIMEOUT
    }

    private fun mapToDifficulty(difficulty: String): Difficulty = when (difficulty) {
        "easy" -> Difficulty.EASY
        "medium" -> Difficulty.MEDIUM
        else -> Difficulty.HARD
    }
}
