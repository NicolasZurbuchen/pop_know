package io.nicolaszurbuchen.pop_know.app.design.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class PopKnowGameColors(
    // --- Difficulty ---
    val difficultyEasy: Color,
    val difficultyMedium: Color,
    val difficultyHard: Color,
    // --- Answer status ---
    val correct: Color,
    val onCorrect: Color,
    val correctSubtle: Color,
    val onCorrectSubtle: Color,
    val wrong: Color,
    val onWrong: Color,
    val wrongSubtle: Color,
    val onWrongSubtle: Color,
    val timeout: Color,
    val onTimeout: Color,
    val timeoutSubtle: Color,
    val onTimeoutSubtle: Color,
)

val DarkPopKnowGameColors =
    PopKnowGameColors(
        // Difficulty
        difficultyEasy = AcidPalette.acid200,
        difficultyMedium = AcidPalette.acid400,
        difficultyHard = AcidPalette.acid600,
        // Answer status
        correct = LimePalette.lime400,
        onCorrect = LimePalette.lime950,
        correctSubtle = LimePalette.lime900,
        onCorrectSubtle = LimePalette.lime300,
        wrong = CrimsonPalette.crimson400,
        onWrong = CrimsonPalette.crimson50,
        wrongSubtle = CrimsonPalette.crimson900,
        onWrongSubtle = CrimsonPalette.crimson300,
        timeout = InkPalette.ink400,
        onTimeout = InkPalette.ink950,
        timeoutSubtle = InkPalette.ink800,
        onTimeoutSubtle = InkPalette.ink200,
    )

val LightPopKnowGameColors =
    PopKnowGameColors(
        // Difficulty
        difficultyEasy = VioletPalette.violet300,
        difficultyMedium = VioletPalette.violet500,
        difficultyHard = VioletPalette.violet700,
        // Answer status
        correct = LimePalette.lime500,
        onCorrect = LimePalette.lime50,
        correctSubtle = LimePalette.lime100,
        onCorrectSubtle = LimePalette.lime800,
        wrong = CrimsonPalette.crimson500,
        onWrong = CrimsonPalette.crimson50,
        wrongSubtle = CrimsonPalette.crimson100,
        onWrongSubtle = CrimsonPalette.crimson800,
        timeout = InkPalette.ink400,
        onTimeout = InkPalette.ink50,
        timeoutSubtle = InkPalette.ink100,
        onTimeoutSubtle = InkPalette.ink700,
    )

internal val LocalPopKnowGameColors = staticCompositionLocalOf { DarkPopKnowGameColors }

val MaterialTheme.popKnowGameColors: PopKnowGameColors
    @Composable
    @ReadOnlyComposable
    get() = LocalPopKnowGameColors.current
