package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val category: String,
)
