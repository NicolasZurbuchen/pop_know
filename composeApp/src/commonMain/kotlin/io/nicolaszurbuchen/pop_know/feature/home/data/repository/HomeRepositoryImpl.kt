package io.nicolaszurbuchen.pop_know.feature.home.data.repository

import io.nicolaszurbuchen.pop_know.cache.QuestionHistoryQueries
import io.nicolaszurbuchen.pop_know.core.domain.AnswerStats
import io.nicolaszurbuchen.pop_know.feature.home.domain.repository.HomeRepository

class HomeRepositoryImpl(
    private val queries: QuestionHistoryQueries
) : HomeRepository {

    override suspend fun getAnswerStats(): AnswerStats? {
        val totalAnswered = queries.countAllHistory().executeAsOne()
        if (totalAnswered == 0L) return null

        val totalCorrect = queries.countCorrectAnswers().executeAsOne()
        return AnswerStats(
            totalAnswered = totalAnswered.toInt(),
            totalCorrect = totalCorrect.toInt(),
            accuracy = totalCorrect.toFloat() / totalAnswered.toFloat(),
        )
    }
}
