package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.api

import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.CategoryResponseDto
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.TriviaResponseDto

interface QuizApi {
    suspend fun getQuestions(
        amount: Int,
        categoryId: Int?,
        difficulty: String?,
    ): TriviaResponseDto

    suspend fun getCategories(): CategoryResponseDto
}
