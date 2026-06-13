package io.nicolaszurbuchen.pop_know.infra.di

import io.nicolaszurbuchen.pop_know.feature.home.di.homeModule
import io.nicolaszurbuchen.pop_know.feature.quiz.di.quizModule
import io.nicolaszurbuchen.pop_know.feature.stats.di.statsModule
import io.nicolaszurbuchen.pop_know.infra.database.databaseModule
import io.nicolaszurbuchen.pop_know.infra.mvi.storeModule
import io.nicolaszurbuchen.pop_know.infra.network.networkModule

val appModule = listOf(
    databaseModule,
    networkModule,
    homeModule,
    statsModule,
    storeModule,
    quizModule,
)