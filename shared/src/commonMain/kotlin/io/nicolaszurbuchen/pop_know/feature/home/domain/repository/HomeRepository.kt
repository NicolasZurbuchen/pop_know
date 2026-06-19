package io.nicolaszurbuchen.pop_know.feature.home.domain.repository

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.AnswerStats
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun observeAnswerStats(): Flow<AnswerStats?>
}
