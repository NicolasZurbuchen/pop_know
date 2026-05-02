package io.nicolaszurbuchen.pop_know.feature.home.domain.repository

import io.nicolaszurbuchen.pop_know.core.domain.AnswerStats

interface HomeRepository {
    suspend fun getAnswerStats(): AnswerStats?
}
