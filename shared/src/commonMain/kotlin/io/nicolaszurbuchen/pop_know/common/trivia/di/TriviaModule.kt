package io.nicolaszurbuchen.pop_know.common.trivia.di

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.mapper.CategoryMapper
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.mapper.TriviaQuestionMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val triviaModule = module {
    factoryOf(::CategoryMapper)
    factoryOf(::TriviaQuestionMapper)
}