package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.api.TriviaApi
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.mapper.toDomain
import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.error.AppException
import io.nicolaszurbuchen.pop_know.common.error.toAppError
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion

class QuizRemoteDataSourceImpl(
    private val api: TriviaApi,
) : QuizRemoteDataSource {

    override suspend fun fetchCategories(): List<Category> {
        return try {
            api.getCategories().triviaCategories.map { it.toDomain() }
        } catch (_: Exception) {
            throw AppException(AppError.Network.Unavailable)
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
                throw AppException(
                    response.responseCode.toAppError()
                )
            }
            response.results.map { it.toDomain(categories) }
        } catch (e: AppException) {
            throw e
        } catch (_: Exception) {
            throw AppException(AppError.Network.Unavailable)
        }
    }
}
