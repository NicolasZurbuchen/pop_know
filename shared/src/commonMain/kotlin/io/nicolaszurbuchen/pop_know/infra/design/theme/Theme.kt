package io.nicolaszurbuchen.pop_know.infra.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightMaterialColorScheme =
    lightColorScheme(
        primary = VioletPalette.violet600,
        onPrimary = InkPalette.ink50,
        primaryContainer = VioletPalette.violet100,
        onPrimaryContainer = VioletPalette.violet900,
        background = InkPalette.ink50,
        onBackground = InkPalette.ink900,
        surface = InkPalette.ink50,
        onSurface = InkPalette.ink900,
        surfaceVariant = InkPalette.ink100,
        onSurfaceVariant = InkPalette.ink500,
        outline = InkPalette.ink200,
        outlineVariant = InkPalette.ink100,
        error = CrimsonPalette.crimson500,
        onError = CrimsonPalette.crimson50,
        errorContainer = CrimsonPalette.crimson100,
        onErrorContainer = CrimsonPalette.crimson800,
    )

private val DarkMaterialColorScheme =
    darkColorScheme(
        primary = AcidPalette.acid300,
        onPrimary = InkPalette.ink950,
        primaryContainer = AcidPalette.acid900,
        onPrimaryContainer = AcidPalette.acid200,
        background = InkPalette.ink950,
        onBackground = InkPalette.ink50,
        surface = InkPalette.ink800,
        onSurface = InkPalette.ink50,
        surfaceVariant = InkPalette.ink700,
        onSurfaceVariant = InkPalette.ink300,
        outline = InkPalette.ink600,
        outlineVariant = InkPalette.ink700,
        error = CrimsonPalette.crimson400,
        onError = CrimsonPalette.crimson50,
        errorContainer = CrimsonPalette.crimson900,
        onErrorContainer = CrimsonPalette.crimson200,
    )

@Composable
fun PopKnowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val popKnowColors = if (darkTheme) DarkPopKnowColors else LightPopKnowColors
    val popKnowGameColors = if (darkTheme) DarkPopKnowGameColors else LightPopKnowGameColors
    val materialColors = if (darkTheme) DarkMaterialColorScheme else LightMaterialColorScheme

    MaterialTheme(
        colorScheme = materialColors,
        typography = PopKnowTypography,
        shapes = PopKnowShapes,
        content = {
            CompositionLocalProvider(
                LocalSpacing provides Spacing(),
                LocalPopKnowColors provides popKnowColors,
                LocalPopKnowGameColors provides popKnowGameColors,
            ) {
                content()
            }
        },
    )
}
