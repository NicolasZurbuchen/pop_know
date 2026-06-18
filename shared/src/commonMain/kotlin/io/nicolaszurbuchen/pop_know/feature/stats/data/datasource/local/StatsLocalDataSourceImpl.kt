package io.nicolaszurbuchen.pop_know.feature.stats.data.datasource.local

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.QuestionHistoryQueries
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.AnswerStats
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.CategoryStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.DifficultyStats

class StatsLocalDataSourceImpl(
    private val queries: QuestionHistoryQueries
) : StatsLocalDataSource {

    override fun countAll(): Long = queries.countAllHistory().executeAsOne()

    override fun countCorrect(): Long = queries.countCorrectAnswers().executeAsOne()

    override fun statsByDifficulty(): List<DifficultyStats> =
        queries.statsByDifficulty().executeAsList().map { row ->
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

    override fun statsByCategory(): List<CategoryStats> =
        queries.statsByCategory().executeAsList().map { row ->
            CategoryStats(
                category = Category(
                    id = row.id.toInt(),
                    category = row.name
                ),
                answerStats = AnswerStats(
                    totalAnswered = row.total.toInt(),
                    totalCorrect = (row.correct ?: 0L).toInt(),
                    accuracy = (row.correct ?: 0L).toFloat() / row.total.toFloat(),
                )
            )
        }
}
