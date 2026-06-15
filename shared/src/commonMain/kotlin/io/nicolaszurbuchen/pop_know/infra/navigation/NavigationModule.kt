package io.nicolaszurbuchen.pop_know.infra.navigation

import org.koin.dsl.module

val infraNavigationModule = module {
    single { AppNavigator() }
}
