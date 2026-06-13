package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote

import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion

interface QuizRemoteDataSource {
    suspend fun fetchCategories(): List<io.nicolaszurbuchen.pop_know.common.domain.Category>
    suspend fun fetchQuestions(
        categories: List<io.nicolaszurbuchen.pop_know.common.domain.Category>,
        amount: Int,
        categoryId: Int? = null,
        difficulty: String? = null,
    ): List<TriviaQuestion>
}
