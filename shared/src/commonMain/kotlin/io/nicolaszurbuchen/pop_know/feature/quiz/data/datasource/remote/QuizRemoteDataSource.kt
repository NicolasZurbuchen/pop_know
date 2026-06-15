package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote

import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.CategoryDto
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.TriviaQuestionDto

interface QuizRemoteDataSource {
    suspend fun fetchCategories(): List<CategoryDto>
    suspend fun fetchQuestions(
        amount: Int,
        categoryId: Int? = null,
        difficulty: String? = null,
    ): List<TriviaQuestionDto>
}
