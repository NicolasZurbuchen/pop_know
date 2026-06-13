package io.nicolaszurbuchen.pop_know.infra.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import popknow.shared.generated.resources.Res
import popknow.shared.generated.resources.jetbrains_mono_bold
import popknow.shared.generated.resources.jetbrains_mono_regular
import popknow.shared.generated.resources.jetbrains_mono_semibold
import popknow.shared.generated.resources.space_grotesk_bold
import popknow.shared.generated.resources.space_grotesk_light
import popknow.shared.generated.resources.space_grotesk_medium
import popknow.shared.generated.resources.space_grotesk_regular

val SpaceGroteskFontFamily
    @Composable get() = FontFamily(
        Font(Res.font.space_grotesk_light, FontWeight.Light),
        Font(Res.font.space_grotesk_regular, FontWeight.Normal),
        Font(Res.font.space_grotesk_medium, FontWeight.Medium),
        Font(Res.font.space_grotesk_bold, FontWeight.Bold),
    )

val JetBrainsMonoFontFamily
    @Composable get() = FontFamily(
        Font(Res.font.jetbrains_mono_regular, FontWeight.Normal),
        Font(Res.font.jetbrains_mono_semibold, FontWeight.SemiBold),
        Font(Res.font.jetbrains_mono_bold, FontWeight.Bold),
    )

val PopKnowTypography
    @Composable get() = Typography(
        // Large score display — 40%, 30%
        displayLarge = TextStyle(
            fontFamily = JetBrainsMonoFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 72.sp,
            lineHeight = 80.sp,
            letterSpacing = (-2).sp
        ),
        // Question number — 02/10
        displayMedium = TextStyle(
            fontFamily = JetBrainsMonoFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            lineHeight = 44.sp,
            letterSpacing = (-1).sp
        ),
        // Section labels — ROUND COMPLETE, BY QUESTION
        displaySmall = TextStyle(
            fontFamily = SpaceGroteskFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 2.sp
        ),
        // Screen titles
        headlineLarge = TextStyle(
            fontFamily = SpaceGroteskFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = (-0.5).sp
        ),
        // Section headers — ACCURACY BY DIFFICULTY
        headlineMedium = TextStyle(
            fontFamily = SpaceGroteskFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            lineHeight = 20.sp,
            letterSpacing = 1.5.sp
        ),
        // Question text
        headlineSmall = TextStyle(
            fontFamily = SpaceGroteskFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 30.sp,
            letterSpacing = (-0.3).sp
        ),
        // Button labels — PLAY ANOTHER ROUND
        titleLarge = TextStyle(
            fontFamily = SpaceGroteskFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 1.sp
        ),
        // Answer options text
        titleMedium = TextStyle(
            fontFamily = SpaceGroteskFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp
        ),
        // Stats labels — EASY, MEDIUM, HARD
        titleSmall = TextStyle(
            fontFamily = SpaceGroteskFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            lineHeight = 20.sp,
            letterSpacing = 1.sp
        ),
        // Body text — 4 of 10 correct
        bodyLarge = TextStyle(
            fontFamily = SpaceGroteskFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.sp
        ),
        // Supporting text
        bodyMedium = TextStyle(
            fontFamily = SpaceGroteskFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.sp
        ),
        // Captions, metadata
        bodySmall = TextStyle(
            fontFamily = SpaceGroteskFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.3.sp
        ),
        // Answer option letter — A, B, C, D
        labelLarge = TextStyle(
            fontFamily = JetBrainsMonoFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.sp
        ),
        // Timer number
        labelMedium = TextStyle(
            fontFamily = JetBrainsMonoFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.sp
        ),
        // Tags, chips
        labelSmall = TextStyle(
            fontFamily = SpaceGroteskFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
            lineHeight = 14.sp,
            letterSpacing = 1.sp
        )
    )