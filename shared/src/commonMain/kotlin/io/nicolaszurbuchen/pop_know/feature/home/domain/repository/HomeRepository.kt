package io.nicolaszurbuchen.pop_know.feature.home.domain.repository

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.AnswerStats

interface HomeRepository {
    suspend fun getAnswerStats(): AnswerStats?
}
