package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion

interface QuizRemoteDataSource {
    suspend fun fetchCategories(): List<Category>
    suspend fun fetchQuestions(
        categories: List<Category>,
        amount: Int,
        categoryId: Int? = null,
        difficulty: String? = null,
    ): List<TriviaQuestion>
}
