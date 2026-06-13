package io.nicolaszurbuchen.pop_know.common.data.datasource.remote.api

import io.nicolaszurbuchen.pop_know.common.data.datasource.remote.model.CategoryResponseDto
import io.nicolaszurbuchen.pop_know.common.data.datasource.remote.model.TriviaResponseDto

interface TriviaApi {
    suspend fun getQuestions(
        amount: Int,
        categoryId: Int?,
        difficulty: String?,
    ): TriviaResponseDto

    suspend fun getCategories(): CategoryResponseDto
}