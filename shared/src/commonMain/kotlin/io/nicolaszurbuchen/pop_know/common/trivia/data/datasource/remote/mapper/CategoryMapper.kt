package io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.mapper

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.dto.CategoryDto
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category

class CategoryMapper {

    fun toDomain(dto: CategoryDto): Category {
        return Category(
            id = dto.id,
            category = dto.category,
        )
    }
}
