package io.nicolaszurbuchen.pop_know.infra.network

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.api.TriviaApi
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.api.TriviaApiImpl
import org.koin.dsl.module

val networkModule = module {
    single { createHttpClient() }
    single<TriviaApi> {
        TriviaApiImpl(get())
    }
}
