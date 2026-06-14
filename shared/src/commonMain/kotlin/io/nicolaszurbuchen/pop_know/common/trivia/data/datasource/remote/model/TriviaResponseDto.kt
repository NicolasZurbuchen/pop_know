package io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TriviaResponseDto(
    @SerialName("response_code")
    val responseCode: Int,
    @SerialName("results")
    val results: List<TriviaQuestionDto>,
)