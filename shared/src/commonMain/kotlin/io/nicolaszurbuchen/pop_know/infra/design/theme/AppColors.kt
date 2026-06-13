package io.nicolaszurbuchen.pop_know.infra.design.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val correct: Color,
    val incorrect: Color,
    val timeout: Color,
    val neonAccent: Color,
    val easy: Color,
    val medium: Color,
    val hard: Color,
)

val LightAppColors = AppColors(
    correct = GreenA400,
    incorrect = RedA400,
    timeout = DeepOrangeA400,
    neonAccent = DeepPurpleA200,
    easy = DeepPurple300,
    medium = DeepPurple500,
    hard = DeepPurple700,
)

val DarkAppColors = AppColors(
    correct = GreenA400,
    incorrect = RedA400,
    timeout = DeepOrangeA400,
    neonAccent = LimeA200,
    easy = Lime300,
    medium = Lime500,
    hard = Lime700,
)

val LocalAppColors = staticCompositionLocalOf { LightAppColors }

val MaterialTheme.appColors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColors.current