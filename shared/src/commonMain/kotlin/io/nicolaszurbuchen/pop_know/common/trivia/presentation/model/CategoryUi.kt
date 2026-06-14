package io.nicolaszurbuchen.pop_know.common.trivia.presentation.model

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category

data class CategoryUi(
    val id: Int,
    val category: String,
)

fun Category.toUi() = CategoryUi(
    id = id,
    category = category,
)
