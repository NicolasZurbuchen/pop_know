package io.nicolaszurbuchen.pop_know.infra.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightColorScheme = lightColorScheme(
    primary = Black,
    onPrimary = White,
    primaryContainer = DeepPurpleA200,
    onPrimaryContainer = White,
    secondary = Gray,
    onSecondary = White,
    secondaryContainer = LightGray,
    onSecondaryContainer = Black,
    background = Cream,
    onBackground = Black,
    surface = Cream,
    onSurface = Black,
    surfaceVariant = LightGray,
    onSurfaceVariant = Gray,
    error = RedA400,
    onError = White,
    outline = LightGray,
    outlineVariant = LightGray
)

private val DarkColorScheme = darkColorScheme(
    primary = White,
    onPrimary = Black,
    primaryContainer = LimeA200,
    onPrimaryContainer = Black,
    secondary = LightGray,
    onSecondary = Black,
    secondaryContainer = Gray,
    onSecondaryContainer = White,
    background = Black,
    onBackground = White,
    surface = Black,
    onSurface = White,
    surfaceVariant = Gray,
    onSurfaceVariant = LightGray,
    error = RedA400,
    onError = Black,
    outline = Gray,
    outlineVariant = Gray
)

@Composable
fun PopKnowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val appColors  = if (darkTheme) DarkAppColors else LightAppColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = PopKnowTypography,
        shapes = PopKnowShapes,
        content = {
            CompositionLocalProvider(
                LocalSpacing provides Spacing(),
                LocalAppColors provides appColors,
            ) {
                content()
            }
        }
    )
}