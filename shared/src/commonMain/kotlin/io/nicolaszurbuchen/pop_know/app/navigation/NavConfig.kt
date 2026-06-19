package io.nicolaszurbuchen.pop_know.app.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import io.nicolaszurbuchen.pop_know.feature.home.presentation.navigation.HomeMainDestination
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.QuizMainDestination
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.navigation.ResultDestination
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.navigation.StatsMainDestination
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

val navConfig =
    SavedStateConfiguration {
        serializersModule =
            SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(HomeMainDestination::class)
                    subclass(QuizMainDestination::class)
                    subclass(ResultDestination::class)
                    subclass(StatsMainDestination::class)
                }
            }
    }
