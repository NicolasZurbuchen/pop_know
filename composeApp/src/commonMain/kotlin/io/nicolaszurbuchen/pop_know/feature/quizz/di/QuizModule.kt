package io.nicolaszurbuchen.pop_know.feature.quizz.di

import io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.local.QuizLocalDataSource
import io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.local.QuizLocalDataSourceImpl
import io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.remote.QuizRemoteDataSource
import io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.remote.QuizRemoteDataSourceImpl
import io.nicolaszurbuchen.pop_know.feature.quizz.data.repository.QuizRepositoryImpl
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.repository.QuizRepository
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.usecase.GetLastGameResultUseCase
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.usecase.SaveAnswerUseCase
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.usecase.StartQuizUseCase
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.QuizViewModel
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.ResultViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val quizModule = module {
    single<QuizRemoteDataSource> { QuizRemoteDataSourceImpl(get()) }
    single<QuizLocalDataSource> { QuizLocalDataSourceImpl(get(), get()) }
    single<QuizRepository> { QuizRepositoryImpl(get(), get()) }
    factory { StartQuizUseCase(get()) }
    factory { SaveAnswerUseCase(get()) }
    factory { GetLastGameResultUseCase(get()) }
    viewModel { QuizViewModel(get(), get()) }
    viewModel { ResultViewModel(get()) }
}
