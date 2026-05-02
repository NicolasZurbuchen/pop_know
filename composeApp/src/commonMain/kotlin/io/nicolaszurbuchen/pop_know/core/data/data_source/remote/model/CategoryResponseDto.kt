package io.nicolaszurbuchen.pop_know.core.data.data_source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseDto(
    @SerialName("trivia_categories")
    val triviaCategories: List<CategoryDto>
) {
}