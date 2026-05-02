package io.nicolaszurbuchen.pop_know.feature.home.data.data_source.local

import io.nicolaszurbuchen.pop_know.cache.QuestionHistoryQueries

class HomeLocalDataSourceImpl(
    private val queries: QuestionHistoryQueries
) : HomeLocalDataSource {

    override fun countAll(): Long = queries.countAllHistory().executeAsOne()

    override fun countCorrect(): Long = queries.countCorrectAnswers().executeAsOne()
}
