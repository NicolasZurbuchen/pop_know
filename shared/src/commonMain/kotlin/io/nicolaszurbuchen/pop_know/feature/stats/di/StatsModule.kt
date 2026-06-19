package io.nicolaszurbuchen.pop_know.feature.stats.di

import io.nicolaszurbuchen.pop_know.feature.stats.data.datasource.local.StatsLocalDataSource
import io.nicolaszurbuchen.pop_know.feature.stats.data.datasource.local.StatsLocalDataSourceImpl
import io.nicolaszurbuchen.pop_know.feature.stats.data.repository.StatsRepositoryImpl
import io.nicolaszurbuchen.pop_know.feature.stats.domain.repository.StatsRepository
import io.nicolaszurbuchen.pop_know.feature.stats.domain.usecase.ClearStatsUseCase
import io.nicolaszurbuchen.pop_know.feature.stats.domain.usecase.GetFullStatsUseCase
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats.StatsStoreFactory
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats.StatsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val statsModule = module {
    single<StatsLocalDataSource> { StatsLocalDataSourceImpl(get()) }
    single<StatsRepository> { StatsRepositoryImpl(get()) }
    factoryOf(::GetFullStatsUseCase)
    factoryOf(::ClearStatsUseCase)
    factoryOf(::StatsStoreFactory)
    viewModelOf(::StatsViewModel)
}
