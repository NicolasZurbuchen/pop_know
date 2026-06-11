package io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.remote

import io.nicolaszurbuchen.pop_know.core.data.datasource.remote.api.TriviaApi
import io.nicolaszurbuchen.pop_know.core.data.datasource.remote.mapper.toDomain
import io.nicolaszurbuchen.pop_know.core.domain.Category
import io.nicolaszurbuchen.pop_know.core.domain.TriviaError
import io.nicolaszurbuchen.pop_know.core.domain.TriviaException
import io.nicolaszurbuchen.pop_know.core.domain.toTriviaError
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.TriviaQuestion

class QuizRemoteDataSourceImpl(
    private val api: TriviaApi,
) : QuizRemoteDataSource {

    override suspend fun fetchCategories(): List<Category> {
        return try {
            api.getCategories().triviaCategories.map { it.toDomain() }
        } catch (_: Exception) {
            throw TriviaException(TriviaError.NetworkError)
        }
    }

    override suspend fun fetchQuestions(
        categories: List<Category>,
        amount: Int,
        categoryId: Int?,
        difficulty: String?
    ): List<TriviaQuestion> {
        return try {
            val response = api.getQuestions(
                amount = amount,
                categoryId = categoryId,
                difficulty = difficulty,
            )
            if (response.responseCode != 0) {
                throw TriviaException(response.responseCode.toTriviaError())
            }
            response.results.map { it.toDomain(categories) }
        } catch (e: TriviaException) {
            throw e
        } catch (_: Exception) {
            throw TriviaException(TriviaError.NetworkError)
        }
    }
}
