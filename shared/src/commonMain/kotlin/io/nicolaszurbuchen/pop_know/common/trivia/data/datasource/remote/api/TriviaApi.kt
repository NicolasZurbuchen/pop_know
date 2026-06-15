package io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.api

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.dto.CategoryResponseDto
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.dto.TriviaResponseDto

interface TriviaApi {
    suspend fun getQuestions(
        amount: Int,
        categoryId: Int?,
        difficulty: String?,
    ): TriviaResponseDto

    suspend fun getCategories(): CategoryResponseDto
}