package io.nicolaszurbuchen.pop_know.core.data.datasource.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.nicolaszurbuchen.pop_know.core.data.datasource.remote.model.CategoryResponseDto
import io.nicolaszurbuchen.pop_know.core.data.datasource.remote.model.TriviaResponseDto

class TriviaApiImpl(
    private val client: HttpClient,
) : TriviaApi {

    override suspend fun getQuestions(
        amount: Int,
        categoryId: Int?,
        difficulty: String?
    ): TriviaResponseDto = client.get("api.php") {
        parameter("amount", amount)
        categoryId?.let { parameter("category", it) }
        difficulty?.let { parameter("difficulty", it) }
    }.body()

    override suspend fun getCategories(): CategoryResponseDto =
        client.get("api_category.php").body()
}