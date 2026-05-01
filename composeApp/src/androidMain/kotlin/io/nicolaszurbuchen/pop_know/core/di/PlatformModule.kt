package io.nicolaszurbuchen.pop_know.core.di

import io.nicolaszurbuchen.pop_know.core.data.data_source.cache.DatabaseDriverFactory
import org.koin.dsl.module

val platformModule = module {
    single { DatabaseDriverFactory(get()) }
}