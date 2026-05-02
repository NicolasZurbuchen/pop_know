package io.nicolaszurbuchen.pop_know.feature.stats.di

import io.nicolaszurbuchen.pop_know.feature.stats.data.repository.StatsRepositoryImpl
import io.nicolaszurbuchen.pop_know.feature.stats.domain.repository.StatsRepository
import io.nicolaszurbuchen.pop_know.feature.stats.domain.use_case.GetFullStatsUseCase
import org.koin.dsl.module

val statsModule = module {
    single<StatsRepository> { StatsRepositoryImpl(get()) }
    factory { GetFullStatsUseCase(get()) }
}
