package io.nicolaszurbuchen.pop_know.feature.stats.data.repository

import io.nicolaszurbuchen.pop_know.common.domain.AnswerStats
import io.nicolaszurbuchen.pop_know.feature.stats.data.datasource.local.StatsLocalDataSource
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.FullStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.repository.StatsRepository

class StatsRepositoryImpl(
    private val localDataSource: StatsLocalDataSource,
) : StatsRepository {

    override suspend fun getFullStats(): FullStats? {
        val totalAnswered = localDataSource.countAll()
        if (totalAnswered == 0L) return null

        val totalCorrect = localDataSource.countCorrect()
        return FullStats(
            summary = AnswerStats(
                totalAnswered = totalAnswered.toInt(),
                totalCorrect = totalCorrect.toInt(),
                accuracy = totalCorrect.toFloat() / totalAnswered.toFloat(),
            ),
            perDifficulty = localDataSource.statsByDifficulty(),
            perCategory = localDataSource.statsByCategory(),
        )
    }
}
