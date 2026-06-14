package io.nicolaszurbuchen.pop_know.feature.stats.presentation.model

import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.AnswerStatsUi
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.CategoryUi
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.toUi
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.CategoryStats

data class CategoryStatsUi(
    val category: CategoryUi,
    val answerStats: AnswerStatsUi,
)

fun CategoryStats.toUi() = CategoryStatsUi(
    category = category.toUi(),
    answerStats = answerStats.toUi(),
)
