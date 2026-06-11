package io.nicolaszurbuchen.pop_know.core.domain

sealed class TriviaError {
    object NoResults : TriviaError()
    object InvalidParameter : TriviaError()
    object RateLimit : TriviaError()
    object NetworkError : TriviaError()
    data class Unknown(val message: String?) : TriviaError()
}

class TriviaException(val error: TriviaError) : Exception()

fun Int.toTriviaError(): TriviaError = when (this) {
    1 -> TriviaError.NoResults
    2 -> TriviaError.InvalidParameter
    5 -> TriviaError.RateLimit
    else -> TriviaError.Unknown("Response code: $this")
}