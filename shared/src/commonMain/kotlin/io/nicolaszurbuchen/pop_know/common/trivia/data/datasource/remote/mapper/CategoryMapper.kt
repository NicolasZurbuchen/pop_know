package io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.mapper

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.model.CategoryDto
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category

fun CategoryDto.toDomain(): Category {
    return Category(
        id = id,
        category = category,
    )
}