package io.nicolaszurbuchen.pop_know.feature.home.data.datasource.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOne
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.QuestionHistoryQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class HomeLocalDataSourceImpl(
    private val queries: QuestionHistoryQueries,
) : HomeLocalDataSource {
    override fun observeCountAll(): Flow<Long> = queries.countAllHistory().asFlow().mapToOne(Dispatchers.Default)

    override fun observeCountCorrect(): Flow<Long> = queries.countCorrectAnswers().asFlow().mapToOne(Dispatchers.Default)
}
