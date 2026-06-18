package io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.pop_know.app.design.component.PopKnowErrorBanner
import io.nicolaszurbuchen.pop_know.app.design.component.PopKnowPrimaryButton
import io.nicolaszurbuchen.pop_know.app.design.component.PopKnowSecondaryButton
import io.nicolaszurbuchen.pop_know.app.design.component.PopKnowSectionLabel
import io.nicolaszurbuchen.pop_know.app.design.component.PopKnowTopBar
import io.nicolaszurbuchen.pop_know.app.design.theme.JetBrainsMonoFontFamily
import io.nicolaszurbuchen.pop_know.app.design.theme.SpaceGroteskFontFamily
import io.nicolaszurbuchen.pop_know.app.design.theme.popKnowColors
import io.nicolaszurbuchen.pop_know.app.design.theme.spacing
import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import io.nicolaszurbuchen.pop_know.infra.ui.asString
import org.jetbrains.compose.resources.stringResource
import popknow.shared.generated.resources.Res
import popknow.shared.generated.resources.home_appbar_center
import popknow.shared.generated.resources.home_appbar_left
import popknow.shared.generated.resources.home_appbar_right
import popknow.shared.generated.resources.home_section_title
import popknow.shared.generated.resources.home_start_round
import popknow.shared.generated.resources.home_stats_correct
import popknow.shared.generated.resources.home_stats_played
import popknow.shared.generated.resources.home_stats_ratio
import popknow.shared.generated.resources.home_stats_ratio_value
import popknow.shared.generated.resources.home_title_lower
import popknow.shared.generated.resources.home_title_subtitle
import popknow.shared.generated.resources.home_title_upper
import popknow.shared.generated.resources.home_view_stats
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    state: HomeUiModel,
    onStartRoundClick: () -> Unit,
    onViewStatsClick: () -> Unit,
    onRetryClick: () -> Unit,
    onDismissErrorClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        PopKnowTopBar(
            left = UiText.Resource(Res.string.home_appbar_left),
            center = UiText.Resource(Res.string.home_appbar_center),
            right = UiText.Resource(Res.string.home_appbar_right),
        )

        state.error?.let { error ->
            val errorMessage = when (error) {
                is AppError.Database.QueryFailed -> "Failed to load statistics from database."
                else -> "An unexpected error occurred."
            }
            PopKnowErrorBanner(
                text = errorMessage,
                onRetry = onRetryClick,
                onDismiss = onDismissErrorClick
            )
        }

        NeonBar()

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = MaterialTheme.spacing.md)
        ) {
            PopKnowSectionLabel(
                text = UiText.Resource(Res.string.home_section_title),
                showSlashes = true,
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.xxl)
            )

            Title()
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.spacing.md,
                    vertical = MaterialTheme.spacing.lg,
                ),
        ) {
            if (state.isLoading) {
                Skeleton()
            } else {
                state.stats?.let { stats ->
                    StatsSummary(
                        played = UiText.Raw(stats.totalAnswered.toString()),
                        correct = UiText.Raw(stats.totalCorrect.toString()),
                        ratio = UiText.Resource(Res.string.home_stats_ratio_value, listOf((stats.accuracy * 100).roundToInt()))
                    )
                }
            }

            PopKnowPrimaryButton(
                text = UiText.Resource(Res.string.home_start_round),
                onClick = onStartRoundClick,
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.sm),
            )

            if (state.hasHistory) {
                PopKnowSecondaryButton(
                    text = UiText.Resource(Res.string.home_view_stats),
                    onClick = onViewStatsClick,
                )
            }
        }
    }
}

@Composable
private fun NeonBar(
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.popKnowColors.accent),
    ) {
        HorizontalDivider(
            modifier = Modifier
                .height(1.dp)
                .background(color = MaterialTheme.colorScheme.onBackground),
        )
        Text(
            text = "????  ???????????  ???????????????  ???",
            style = TextStyle(
                color = MaterialTheme.colorScheme.background,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = SpaceGroteskFontFamily,
                letterSpacing = 2.sp,
                lineHeight = 40.sp,
            ),
            modifier = Modifier
                .padding(vertical = MaterialTheme.spacing.sm)
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    velocity = 30.dp,
                ),
        )
        HorizontalDivider(
            modifier = Modifier
                .height(1.dp)
                .background(MaterialTheme.colorScheme.onBackground),
        )
    }
}

@Composable
private fun Title(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = UiText.Resource(Res.string.home_title_upper).asString().uppercase(),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 92.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = SpaceGroteskFontFamily,
                letterSpacing = (-3).sp,
                lineHeight = 80.sp,
            ),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height - 40.dp.roundToPx()) {
                        placeable.placeRelative(0, -40.dp.roundToPx())
                    }
                }
        ) {
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.popKnowColors.accent),
            ) {
                Text(
                    text = UiText.Resource(Res.string.home_title_lower).asString().uppercase(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 92.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = SpaceGroteskFontFamily,
                        letterSpacing = (-3).sp,
                        lineHeight = 80.sp,
                    ),
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.spacing.sm),
                )
            }
            Text(
                text = ".",
                style = TextStyle(
                    color = MaterialTheme.popKnowColors.accent,
                    fontSize = 92.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = SpaceGroteskFontFamily,
                    letterSpacing = (-3).sp,
                    lineHeight = 80.sp,
                ),
            )
        }
        Text(
            text = UiText.Resource(Res.string.home_title_subtitle).asString(),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = SpaceGroteskFontFamily,
                letterSpacing = 0.sp,
                lineHeight = 23.sp,
            ),
            modifier = Modifier
                .padding(top = MaterialTheme.spacing.sm)
                .width(280.dp),
        )
    }
}

@Composable
private fun StatsSummary(
    played: UiText,
    correct: UiText,
    ratio: UiText,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.small,
            )
            .padding(
                horizontal = MaterialTheme.spacing.lg,
                vertical = MaterialTheme.spacing.md,
            ),
    ) {
        StatsItem(
            label = stringResource(Res.string.home_stats_played),
            value = played.asString(),
            alignment = Alignment.Start,
            modifier = Modifier
                .weight(1f),
        )
        VerticalDivider(
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .height(48.dp),
        )
        StatsItem(
            label = stringResource(Res.string.home_stats_correct),
            value = correct.asString(),
            alignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f),
        )
        VerticalDivider(
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .height(48.dp),
        )
        StatsItem(
            label = stringResource(Res.string.home_stats_ratio),
            value = ratio.asString(),
            alignment = Alignment.End,
            modifier = Modifier
                .weight(1f),
        )
    }
}

@Composable
private fun StatsItem(
    label: String,
    value: String,
    alignment: Alignment.Horizontal,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = alignment,
        modifier = modifier,
    ) {
        Text(
            text = label.uppercase(),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = JetBrainsMonoFontFamily,
                letterSpacing = 1.5.sp,
                lineHeight = 15.sp,
            ),
        )
        Text(
            text = value,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = SpaceGroteskFontFamily,
                letterSpacing = 0.sp,
                lineHeight = 42.sp,
            ),
        )
    }
}

@Composable
private fun Skeleton() {
    val shimmerAlpha by rememberInfiniteTransition(label = "shimmer")
        .animateFloat(
            initialValue = 0.35f,
            targetValue = 0.85f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(
                        durationMillis = 900,
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "shimmer-alpha",
        )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.popKnowColors.surface.copy(alpha = shimmerAlpha)),
    )
}
