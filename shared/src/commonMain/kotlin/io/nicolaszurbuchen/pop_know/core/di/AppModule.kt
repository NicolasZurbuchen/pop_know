package io.nicolaszurbuchen.pop_know.core.di

import io.nicolaszurbuchen.pop_know.feature.home.di.homeModule
import io.nicolaszurbuchen.pop_know.feature.quizz.di.quizModule
import io.nicolaszurbuchen.pop_know.feature.stats.di.statsModule

val appModule = listOf(
    databaseModule,
    networkModule,
    homeModule,
    statsModule,
    quizModule,
)