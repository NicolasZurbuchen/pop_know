package io.nicolaszurbuchen.pop_know.feature.home.data.repository

import io.nicolaszurbuchen.pop_know.core.domain.AnswerStats
import io.nicolaszurbuchen.pop_know.feature.home.data.data_source.local.HomeLocalDataSource
import io.nicolaszurbuchen.pop_know.feature.home.domain.repository.HomeRepository

class HomeRepositoryImpl(
    private val localDataSource: HomeLocalDataSource
) : HomeRepository {

    override suspend fun getAnswerStats(): AnswerStats? {
        val totalAnswered = localDataSource.countAll()
        if (totalAnswered == 0L) return null

        val totalCorrect = localDataSource.countCorrect()
        return AnswerStats(
            totalAnswered = totalAnswered.toInt(),
            totalCorrect = totalCorrect.toInt(),
            accuracy = totalCorrect.toFloat() / totalAnswered.toFloat(),
        )
    }
}
