package io.nicolaszurbuchen.pop_know.infra.network

import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.api.QuizApi
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.api.QuizApiImpl
import org.koin.dsl.module

val networkModule = module {
    single { createHttpClient() }
    single<QuizApi> {
        QuizApiImpl(get())
    }
}
