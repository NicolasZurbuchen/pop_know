package io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.remote

import io.nicolaszurbuchen.pop_know.cache.CategoryQueries
import io.nicolaszurbuchen.pop_know.core.data.remote.api.TriviaApi
import io.nicolaszurbuchen.pop_know.core.data.remote.mapper.toDomain
import io.nicolaszurbuchen.pop_know.core.domain.Category
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.TriviaQuestion

class QuizRemoteDataSourceImpl(
    private val api: TriviaApi,
    private val categoryQueries: CategoryQueries,
) : QuizRemoteDataSource {

    override suspend fun fetchQuestions(amount: Int): List<TriviaQuestion> {
        val categories = categoryQueries.getAllCategories().executeAsList()
            .map { Category(id = it.id.toInt(), category = it.name) }
        return api.getQuestions(amount, categoryId = null, difficulty = null)
            .results
            .map { it.toDomain(categories) }
    }
}
