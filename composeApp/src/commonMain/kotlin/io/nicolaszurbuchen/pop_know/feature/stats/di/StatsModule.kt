package io.nicolaszurbuchen.pop_know.feature.stats.di

import io.nicolaszurbuchen.pop_know.feature.stats.data.datasource.local.StatsLocalDataSource
import io.nicolaszurbuchen.pop_know.feature.stats.data.datasource.local.StatsLocalDataSourceImpl
import io.nicolaszurbuchen.pop_know.feature.stats.data.repository.StatsRepositoryImpl
import io.nicolaszurbuchen.pop_know.feature.stats.domain.repository.StatsRepository
import io.nicolaszurbuchen.pop_know.feature.stats.domain.usecase.GetFullStatsUseCase
import org.koin.dsl.module

val statsModule = module {
    single<StatsLocalDataSource> { StatsLocalDataSourceImpl(get()) }
    single<StatsRepository> { StatsRepositoryImpl(get()) }
    factory { GetFullStatsUseCase(get()) }
}
