package io.nicolaszurbuchen.pop_know.feature.quiz.di

import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local.QuizLocalDataSource
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local.QuizLocalDataSourceImpl
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.QuizRemoteDataSource
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.QuizRemoteDataSourceImpl
import io.nicolaszurbuchen.pop_know.feature.quiz.data.repository.QuizRepositoryImpl
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.repository.QuizRepository
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase.GetLastGameResultUseCase
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase.StartQuizUseCase
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase.SubmitAnswerUseCase
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.QuizStoreFactory
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.QuizViewModel
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result.ResultStoreFactory
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result.ResultViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val quizModule = module {
    single<QuizRemoteDataSource> { QuizRemoteDataSourceImpl(get()) }
    single<QuizLocalDataSource> { QuizLocalDataSourceImpl(get(), get()) }
    single<QuizRepository> { QuizRepositoryImpl(get(), get()) }
    factoryOf(::StartQuizUseCase)
    factoryOf(::SubmitAnswerUseCase)
    factoryOf(::GetLastGameResultUseCase)
    factoryOf(::QuizStoreFactory)
    factoryOf(::ResultStoreFactory)
    factory { params -> QuizViewModel(params.get(), get()) }
    factory { ResultViewModel(get()) }
}
