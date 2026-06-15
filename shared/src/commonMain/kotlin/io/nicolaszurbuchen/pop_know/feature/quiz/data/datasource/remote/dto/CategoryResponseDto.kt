package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseDto(
    @SerialName("trivia_categories")
    val triviaCategories: List<CategoryDto>
)
