package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.dto.CategoryDto
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.dto.TriviaQuestionDto
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category

interface QuizRemoteDataSource {
    suspend fun fetchCategories(): List<CategoryDto>
    suspend fun fetchQuestions(
        categories: List<Category>,
        amount: Int,
        categoryId: Int? = null,
        difficulty: String? = null,
    ): List<TriviaQuestionDto>
}
