package io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.local

import io.nicolaszurbuchen.pop_know.core.domain.Category
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.TriviaQuestion

interface QuizLocalDataSource {
    fun saveQuestion(question: TriviaQuestion, selectedAnswer: String, answeredAt: Long)
    fun getCategories(): List<Category>
    fun saveCategories(categories: List<Category>)
}
