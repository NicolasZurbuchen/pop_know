package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.CategoryEntity
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.GetLastGame

interface QuizLocalDataSource {
    fun saveAnswer(
        gameId: Long,
        type: String,
        difficulty: String,
        categoryId: Long,
        question: String,
        correctAnswer: String,
        incorrectAnswers: List<String>,
        selectedAnswer: String?,
        status: String,
    )
    fun getLastGame(): List<GetLastGame>
    fun getCategories(): List<CategoryEntity>
    fun saveCategories(categories: List<CategoryEntity>)
}
