package io.nicolaszurbuchen.pop_know.infra.database

import io.nicolaszurbuchen.pop_know.cache.AppDatabase
import org.koin.dsl.module

val databaseModule =
    module {
        single { createDatabase(get()) }
        single { get<AppDatabase>().categoryQueries }
        single { get<AppDatabase>().questionHistoryQueries }
    }
