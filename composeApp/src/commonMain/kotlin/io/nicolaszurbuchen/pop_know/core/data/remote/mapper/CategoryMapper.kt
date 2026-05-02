package io.nicolaszurbuchen.pop_know.core.data.remote.mapper

import io.nicolaszurbuchen.pop_know.core.data.remote.model.CategoryDto
import io.nicolaszurbuchen.pop_know.core.domain.Category

fun CategoryDto.toDomain(): Category {
    return Category(
        id = id,
        category = category,
    )
}