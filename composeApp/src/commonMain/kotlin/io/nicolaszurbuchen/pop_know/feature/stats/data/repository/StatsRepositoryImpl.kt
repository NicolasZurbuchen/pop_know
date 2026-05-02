package io.nicolaszurbuchen.pop_know.feature.stats.data.repository

import io.nicolaszurbuchen.pop_know.cache.QuestionHistoryQueries
import io.nicolaszurbuchen.pop_know.core.domain.AnswerStats
import io.nicolaszurbuchen.pop_know.core.domain.Category
import io.nicolaszurbuchen.pop_know.core.domain.Difficulty
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.CategoryStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.DifficultyStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.FullStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.repository.StatsRepository

class StatsRepositoryImpl(
    private val queries: QuestionHistoryQueries
) : StatsRepository {

    override suspend fun getFullStats(): FullStats? {
        val totalAnswered = queries.countAllHistory().executeAsOne()
        if (totalAnswered == 0L) return null

        val totalCorrect = queries.countCorrectAnswers().executeAsOne()

        val summary = AnswerStats(
            totalAnswered = totalAnswered.toInt(),
            totalCorrect = totalCorrect.toInt(),
            accuracy = totalCorrect.toFloat() / totalAnswered.toFloat(),
        )

        val perDifficulty = queries.statsByDifficulty().executeAsList().map { row ->
            DifficultyStats(
                difficulty = when (row.difficulty) {
                    "easy" -> Difficulty.EASY
                    "medium" -> Difficulty.MEDIUM
                    "hard" -> Difficulty.HARD
                    else -> throw IllegalStateException("Unknown difficulty in DB: ${row.difficulty}")
                },
                answerStats = AnswerStats(
                    totalAnswered = row.total.toInt(),
                    totalCorrect = (row.correct ?: 0L).toInt(),
                    accuracy = (row.correct ?: 0L).toFloat() / row.total.toFloat(),
                )
            )
        }

        val perCategory = queries.statsByCategory().executeAsList().map { row ->
            CategoryStats(
                category = Category(id = row.id.toInt(), category = row.name),
                answerStats = AnswerStats(
                    totalAnswered = row.total.toInt(),
                    totalCorrect = (row.correct ?: 0L).toInt(),
                    accuracy = (row.correct ?: 0L).toFloat() / row.total.toFloat(),
                )
            )
        }

        return FullStats(
            summary = summary,
            perDifficulty = perDifficulty,
            perCategory = perCategory,
        )
    }
}
