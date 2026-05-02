package io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.local

import io.nicolaszurbuchen.pop_know.cache.CategoryQueries
import io.nicolaszurbuchen.pop_know.cache.QuestionHistoryQueries
import io.nicolaszurbuchen.pop_know.core.domain.Category
import io.nicolaszurbuchen.pop_know.core.domain.Difficulty
import io.nicolaszurbuchen.pop_know.core.domain.QuestionType
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.TriviaQuestion

class QuizLocalDataSourceImpl(
    private val questionHistoryQueries: QuestionHistoryQueries,
    private val categoryQueries: CategoryQueries,
) : QuizLocalDataSource {

    override fun saveQuestion(question: TriviaQuestion, selectedAnswer: String, answeredAt: Long) {
        questionHistoryQueries.insertHistory(
            type = question.questionType.toDbString(),
            difficulty = question.difficulty.toDbString(),
            category_id = question.category.id.toLong(),
            question = question.question,
            correct_answer = question.correctAnswer,
            incorrect_answers = question.incorrectAnswers,
            selected_answer = selectedAnswer,
            answered_at = answeredAt,
        )
    }

    override fun getCategories(): List<Category> {
        return categoryQueries.getAllCategories { id, name ->
            Category(id = id.toInt(), category = name)
        }.executeAsList()
    }

    override fun saveCategories(categories: List<Category>) {
        categories.forEach { category ->
            categoryQueries.insertCategory(id = category.id.toLong(), name = category.category)
        }
    }

    private fun QuestionType.toDbString(): String = when (this) {
        QuestionType.MULTIPLE -> "multiple"
        QuestionType.BOOLEAN -> "boolean"
    }

    private fun Difficulty.toDbString(): String = when (this) {
        Difficulty.EASY -> "easy"
        Difficulty.MEDIUM -> "medium"
        Difficulty.HARD -> "hard"
    }
}
