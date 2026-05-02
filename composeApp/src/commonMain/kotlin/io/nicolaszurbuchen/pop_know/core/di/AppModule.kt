package io.nicolaszurbuchen.pop_know.core.di

import io.nicolaszurbuchen.pop_know.feature.home.di.homeModule

val appModule = listOf(
    databaseModule,
    networkModule,
    homeModule,
)