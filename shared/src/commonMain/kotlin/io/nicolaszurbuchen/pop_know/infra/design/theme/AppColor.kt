package io.nicolaszurbuchen.pop_know.infra.design.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class PopKnowColors(
    // --- Surfaces ---
    val background: Color,
    val surface: Color,
    val surfaceRaised: Color,
    // --- Borders ---
    val borderSubtle: Color,
    val borderDefault: Color,
    // --- Text ---
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textDisabled: Color,
    val textInverse: Color,
    // --- Accent (violet on light / acid on dark) ---
    val accent: Color,
    val onAccent: Color,
    val accentSubtle: Color,
    val onAccentSubtle: Color,
    // --- Correct (lime) ---
    val correct: Color,
    val onCorrect: Color,
    val correctSubtle: Color,
    val onCorrectSubtle: Color,
    // --- Wrong (crimson) ---
    val wrong: Color,
    val onWrong: Color,
    val wrongSubtle: Color,
    val onWrongSubtle: Color,
    // --- Mode flag ---
    val isDark: Boolean,
)

val DarkPopKnowColors = PopKnowColors(
    isDark = true,
    // Surfaces
    background = InkPalette.ink950,
    surface = InkPalette.ink800,
    surfaceRaised = InkPalette.ink700,
    // Borders
    borderSubtle = InkPalette.ink700,
    borderDefault = InkPalette.ink600,
    // Text
    textPrimary = InkPalette.ink50,
    textSecondary = InkPalette.ink200,
    textTertiary = InkPalette.ink300,
    textDisabled = InkPalette.ink500,
    textInverse = InkPalette.ink900,
    // Accent — acid yellow in dark mode
    accent = AcidPalette.acid300,
    onAccent = InkPalette.ink950,
    accentSubtle = AcidPalette.acid900,
    onAccentSubtle = AcidPalette.acid200,
    // Correct
    correct = LimePalette.lime400,
    onCorrect = LimePalette.lime950,
    correctSubtle = LimePalette.lime900,
    onCorrectSubtle = LimePalette.lime300,
    // Wrong
    wrong = CrimsonPalette.crimson400,
    onWrong = CrimsonPalette.crimson50,
    wrongSubtle = CrimsonPalette.crimson900,
    onWrongSubtle = CrimsonPalette.crimson300,
)

val LightPopKnowColors = PopKnowColors(
    isDark = false,
    // Surfaces
    background = InkPalette.ink50,       // your Cream
    surface = Color(0xFFFFFFFF),
    surfaceRaised = Color(0xFFE8E4DC),
    // Borders
    borderSubtle = InkPalette.ink100,
    borderDefault = InkPalette.ink200,
    // Text
    textPrimary = InkPalette.ink900,
    textSecondary = InkPalette.ink500,
    textTertiary = InkPalette.ink400,
    textDisabled = InkPalette.ink300,
    textInverse = InkPalette.ink50,
    // Accent — violet in light mode
    accent = VioletPalette.violet600,
    onAccent = Color(0xFFFFFFFF),
    accentSubtle = VioletPalette.violet100,
    onAccentSubtle = VioletPalette.violet800,
    // Correct — step up to 500 for contrast on light bg
    correct = LimePalette.lime500,
    onCorrect = LimePalette.lime50,
    correctSubtle = LimePalette.lime100,
    onCorrectSubtle = LimePalette.lime800,
    // Wrong
    wrong = CrimsonPalette.crimson500,
    onWrong = CrimsonPalette.crimson50,
    wrongSubtle = CrimsonPalette.crimson100,
    onWrongSubtle = CrimsonPalette.crimson800,
)

internal val LocalPopKnowColors = staticCompositionLocalOf { DarkPopKnowColors }

val MaterialTheme.popKnowColors: PopKnowColors
    @Composable
    @ReadOnlyComposable
    get() = LocalPopKnowColors.current


val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)