package io.nicolaszurbuchen.pop_know.feature.home.di

import io.nicolaszurbuchen.pop_know.feature.home.data.datasource.local.HomeLocalDataSource
import io.nicolaszurbuchen.pop_know.feature.home.data.datasource.local.HomeLocalDataSourceImpl
import io.nicolaszurbuchen.pop_know.feature.home.data.repository.HomeRepositoryImpl
import io.nicolaszurbuchen.pop_know.feature.home.domain.repository.HomeRepository
import io.nicolaszurbuchen.pop_know.feature.home.domain.usecase.GetAnswerStatsUseCase
import io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.home.HomeStoreFactory
import io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.home.HomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    singleOf(::HomeLocalDataSourceImpl) bind HomeLocalDataSource::class

    singleOf(::HomeRepositoryImpl) bind HomeRepository::class

    factoryOf(::GetAnswerStatsUseCase)

    factoryOf(::HomeStoreFactory)

    viewModelOf(::HomeViewModel)
}
