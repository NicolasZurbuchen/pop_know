package io.nicolaszurbuchen.pop_know.common.data.datasource.remote.mapper

import io.nicolaszurbuchen.pop_know.common.data.datasource.remote.model.CategoryDto
import io.nicolaszurbuchen.pop_know.common.domain.Category

fun CategoryDto.toDomain(): Category {
    return Category(
        id = id,
        category = category,
    )
}