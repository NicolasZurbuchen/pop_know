package io.nicolaszurbuchen.pop_know.core.di

import io.nicolaszurbuchen.pop_know.cache.AppDatabase
import io.nicolaszurbuchen.pop_know.core.database.createDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { createDatabase(get()) }
    single { get<AppDatabase>().categoryQueries }
    single { get<AppDatabase>().questionHistoryQueries }
}