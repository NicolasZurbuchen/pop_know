package io.nicolaszurbuchen.pop_know.core.di

import io.nicolaszurbuchen.pop_know.core.data.data_source.remote.createHttpClient
import org.koin.dsl.module

val networkModule = module {
    single { createHttpClient() }
}