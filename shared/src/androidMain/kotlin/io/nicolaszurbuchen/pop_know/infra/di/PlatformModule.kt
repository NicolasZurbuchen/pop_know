package io.nicolaszurbuchen.pop_know.infra.di

import io.nicolaszurbuchen.pop_know.infra.database.DatabaseDriverFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val platformModule = module {
    singleOf(::DatabaseDriverFactory)
}
