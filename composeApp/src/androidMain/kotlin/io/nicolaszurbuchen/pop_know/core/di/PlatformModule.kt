package io.nicolaszurbuchen.pop_know.core.di

import io.nicolaszurbuchen.pop_know.core.database.DatabaseDriverFactory
import org.koin.dsl.module

val platformModule = module {
    single { DatabaseDriverFactory(get()) }
}