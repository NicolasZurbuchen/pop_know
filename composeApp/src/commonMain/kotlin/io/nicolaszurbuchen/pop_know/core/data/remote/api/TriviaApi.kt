package io.nicolaszurbuchen.pop_know.core.data.remote.api

import io.nicolaszurbuchen.pop_know.core.data.remote.model.CategoryResponseDto
import io.nicolaszurbuchen.pop_know.core.data.remote.model.TriviaResponseDto

interface TriviaApi {
    suspend fun getQuestions(
        amount: Int,
        categoryId: Int?,
        difficulty: String?,
    ): TriviaResponseDto

    suspend fun getCategories(): CategoryResponseDto
}