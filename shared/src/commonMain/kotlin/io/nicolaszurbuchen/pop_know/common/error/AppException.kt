package io.nicolaszurbuchen.pop_know.common.error

class AppException(
    val error: AppError,
) : Exception("App error: $error")
