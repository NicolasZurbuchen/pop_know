package io.nicolaszurbuchen.pop_know.app.di

import io.nicolaszurbuchen.pop_know.app.navigation.HomeNavigatorImpl
import io.nicolaszurbuchen.pop_know.app.navigation.QuizNavigatorImpl
import io.nicolaszurbuchen.pop_know.app.navigation.StatsNavigatorImpl
import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.HomeMainDestination
import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.HomeNavKeyHandler
import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.HomeNavigator
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.QuizNavKeyHandler
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.QuizNavigator
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation.StatsNavKeyHandler
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation.StatsNavigator
import io.nicolaszurbuchen.pop_know.infra.navigation.NavKeyHandler
import androidx.navigation3.runtime.NavKey
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val appNavigationModule = module {
    single<NavKey>(named("initialRoute")) { HomeMainDestination }

    single { HomeNavigatorImpl(get()) } bind HomeNavigator::class
    single { QuizNavigatorImpl(get()) } bind QuizNavigator::class
    single { StatsNavigatorImpl(get()) } bind StatsNavigator::class

    single(named("home")) { HomeNavKeyHandler(get()) } bind NavKeyHandler::class
    single(named("quiz")) { QuizNavKeyHandler(get()) } bind NavKeyHandler::class
    single(named("stats")) { StatsNavKeyHandler(get()) } bind NavKeyHandler::class
}
