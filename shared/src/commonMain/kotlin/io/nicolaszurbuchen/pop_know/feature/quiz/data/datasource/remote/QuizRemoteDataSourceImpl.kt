package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote

import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.error.AppException
import io.nicolaszurbuchen.pop_know.common.error.toAppError
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.api.QuizApi
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.CategoryDto
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.TriviaQuestionDto
import kotlin.coroutines.cancellation.CancellationException

class QuizRemoteDataSourceImpl(
    private val api: QuizApi,
) : QuizRemoteDataSource {
    override suspend fun fetchCategories(): List<CategoryDto> =
        try {
            api.getCategories().triviaCategories
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            throw AppException(AppError.Network.Unavailable)
        }

    override suspend fun fetchQuestions(
        amount: Int,
        categoryId: Int?,
        difficulty: String?,
    ): List<TriviaQuestionDto> =
        try {
            val response =
                api.getQuestions(
                    amount = amount,
                    categoryId = categoryId,
                    difficulty = difficulty,
                )
            if (response.responseCode != 0) {
                throw AppException(
                    response.responseCode.toAppError(),
                )
            }
            response.results
        } catch (e: AppException) {
            throw e
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            throw AppException(AppError.Network.Unavailable)
        }
}
