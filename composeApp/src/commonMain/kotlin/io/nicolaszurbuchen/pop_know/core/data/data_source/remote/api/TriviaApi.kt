package io.nicolaszurbuchen.pop_know.core.data.data_source.remote.api

import io.nicolaszurbuchen.pop_know.core.data.data_source.remote.model.CategoryResponseDto
import io.nicolaszurbuchen.pop_know.core.data.data_source.remote.model.TriviaResponseDto

interface TriviaApi {
    suspend fun getQuestions(
        amount: Int,
        categoryId: Int?,
        difficulty: String?,
    ): TriviaResponseDto

    suspend fun getCategories(): CategoryResponseDto
}