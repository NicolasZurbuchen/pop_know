package io.nicolaszurbuchen.pop_know.feature.home.domain.usecase

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.AnswerStats
import io.nicolaszurbuchen.pop_know.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetAnswerStatsUseCase(
    private val repository: HomeRepository,
) {
    operator fun invoke(): Flow<AnswerStats?> = repository.observeAnswerStats()
}
