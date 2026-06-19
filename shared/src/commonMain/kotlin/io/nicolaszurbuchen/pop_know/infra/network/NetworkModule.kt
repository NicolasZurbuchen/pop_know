package io.nicolaszurbuchen.pop_know.infra.network

import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.api.QuizApi
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.api.QuizApiImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    single { createHttpClient() }

    singleOf(::QuizApiImpl) bind QuizApi::class
}
