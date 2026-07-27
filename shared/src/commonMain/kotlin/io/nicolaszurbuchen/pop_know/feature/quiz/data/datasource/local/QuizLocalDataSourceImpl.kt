package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.CategoryEntity
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.CategoryQueries
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.GetLastGame
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.QuestionHistoryQueries
import kotlin.time.Clock

class QuizLocalDataSourceImpl(
    private val questionHistoryQueries: QuestionHistoryQueries,
    private val categoryQueries: CategoryQueries,
    private val nowMillis: () -> Long = { Clock.System.now().toEpochMilliseconds() },
) : QuizLocalDataSource {
    override fun saveAnswer(
        gameId: Long,
        type: String,
        difficulty: String,
        categoryId: Long,
        question: String,
        correctAnswer: String,
        incorrectAnswers: List<String>,
        selectedAnswer: String?,
        status: String,
    ) {
        questionHistoryQueries.insertHistory(
            game_id = gameId,
            type = type,
            difficulty = difficulty,
            category_id = categoryId,
            question = question,
            correct_answer = correctAnswer,
            incorrect_answers = incorrectAnswers,
            selected_answer = selectedAnswer,
            status = status,
            answered_at = nowMillis(),
        )
    }

    override fun getLastGame(): List<GetLastGame> = questionHistoryQueries.getLastGame().executeAsList()

    override fun getCategories(): List<CategoryEntity> = categoryQueries.getAllCategories().executeAsList()

    override fun saveCategories(categories: List<CategoryEntity>) {
        categories.forEach { category ->
            categoryQueries.insertCategory(id = category.id, name = category.name)
        }
    }
}
