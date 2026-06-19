package io.nicolaszurbuchen.pop_know.app.navigation

import androidx.navigation3.runtime.NavKey
import io.nicolaszurbuchen.pop_know.app.navigation.impl.HomeNavigatorImpl
import io.nicolaszurbuchen.pop_know.app.navigation.impl.QuizNavigatorImpl
import io.nicolaszurbuchen.pop_know.app.navigation.impl.StatsNavigatorImpl
import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.HomeMainDestination
import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.HomeNavKeyHandler
import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.HomeNavigator
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.QuizNavKeyHandler
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.QuizNavigator
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation.StatsNavKeyHandler
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation.StatsNavigator
import io.nicolaszurbuchen.pop_know.infra.navigation.NavKeyHandler
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val appNavigationModule = module {
    single<NavKey>(named("initialRoute")) { HomeMainDestination }

    singleOf(::HomeNavigatorImpl) bind HomeNavigator::class
    singleOf(::QuizNavigatorImpl) bind QuizNavigator::class
    singleOf(::StatsNavigatorImpl) bind StatsNavigator::class

    singleOf(::HomeNavKeyHandler) { named("home") } bind NavKeyHandler::class
    singleOf(::QuizNavKeyHandler) { named("quiz") } bind NavKeyHandler::class
    singleOf(::StatsNavKeyHandler) { named("stats") } bind NavKeyHandler::class
}
