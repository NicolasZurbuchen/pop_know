package io.nicolaszurbuchen.pop_know.feature.stats.presentation.model

import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.AnswerStatsUi
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.toUi
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.FullStats

data class FullStatsUi(
    val summary: AnswerStatsUi,
    val perCategory: List<CategoryStatsUi>,
    val perDifficulty: List<DifficultyStatsUi>
)

fun FullStats.toUi() = FullStatsUi(
    summary = summary.toUi(),
    perCategory = perCategory.map { it.toUi() },
    perDifficulty = perDifficulty.map { it.toUi() },
)
