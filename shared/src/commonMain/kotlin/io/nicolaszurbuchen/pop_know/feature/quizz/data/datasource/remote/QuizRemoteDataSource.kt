package io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.remote

import io.nicolaszurbuchen.pop_know.core.domain.Category
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.TriviaQuestion

interface QuizRemoteDataSource {
    suspend fun fetchCategories(): List<Category>
    suspend fun fetchQuestions(
        categories: List<Category>,
        amount: Int,
        categoryId: Int? = null,
        difficulty: String? = null,
    ): List<TriviaQuestion>
}
