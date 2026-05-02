package io.nicolaszurbuchen.pop_know.feature.home.di

import io.nicolaszurbuchen.pop_know.feature.home.data.datasource.local.HomeLocalDataSource
import io.nicolaszurbuchen.pop_know.feature.home.data.datasource.local.HomeLocalDataSourceImpl
import io.nicolaszurbuchen.pop_know.feature.home.data.repository.HomeRepositoryImpl
import io.nicolaszurbuchen.pop_know.feature.home.domain.repository.HomeRepository
import io.nicolaszurbuchen.pop_know.feature.home.domain.usecase.GetAnswerStatsUseCase
import org.koin.dsl.module

val homeModule = module {
    single<HomeLocalDataSource> { HomeLocalDataSourceImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    factory { GetAnswerStatsUseCase(get()) }
}
