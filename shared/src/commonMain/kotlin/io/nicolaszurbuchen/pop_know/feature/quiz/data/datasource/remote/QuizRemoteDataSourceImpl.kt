package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote

import io.nicolaszurbuchen.pop_know.common.data.datasource.remote.api.TriviaApi
import io.nicolaszurbuchen.pop_know.common.data.datasource.remote.mapper.toDomain
import io.nicolaszurbuchen.pop_know.common.domain.Category
import io.nicolaszurbuchen.pop_know.common.domain.TriviaError
import io.nicolaszurbuchen.pop_know.common.domain.TriviaException
import io.nicolaszurbuchen.pop_know.common.domain.toTriviaError
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion

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
                throw TriviaException(
                    response.responseCode.toTriviaError()
                )
            }
            response.results.map { it.toDomain(categories) }
        } catch (e: TriviaException) {
            throw e
        } catch (_: Exception) {
            throw TriviaException(TriviaError.NetworkError)
        }
    }
}
