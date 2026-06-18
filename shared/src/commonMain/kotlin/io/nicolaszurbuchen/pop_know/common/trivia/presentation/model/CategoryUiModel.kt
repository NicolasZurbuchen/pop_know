package io.nicolaszurbuchen.pop_know.common.trivia.presentation.model

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category

data class CategoryUiModel(
    val id: Int,
    val category: String,
)

fun Category.toUiModel() = CategoryUiModel(
    id = id,
    category = category,
)
