package io.nicolaszurbuchen.pop_know.feature.home.data.repository

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.AnswerStats
import io.nicolaszurbuchen.pop_know.feature.home.data.datasource.local.HomeLocalDataSource
import io.nicolaszurbuchen.pop_know.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class HomeRepositoryImpl(
    private val localDataSource: HomeLocalDataSource,
) : HomeRepository {
    override fun observeAnswerStats(): Flow<AnswerStats?> =
        combine(
            localDataSource.observeCountAll(),
            localDataSource.observeCountCorrect(),
        ) { totalAnswered, totalCorrect ->
            if (totalAnswered == 0L) return@combine null

            AnswerStats(
                totalAnswered = totalAnswered.toInt(),
                totalCorrect = totalCorrect.toInt(),
                accuracy = totalCorrect.toFloat() / totalAnswered.toFloat(),
            )
        }
}
