package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.pop_know.common.domain.Difficulty
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.CategoryStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.DifficultyStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.FullStats
import io.nicolaszurbuchen.pop_know.infra.design.component.PopKnowErrorView
import io.nicolaszurbuchen.pop_know.infra.design.component.PopKnowSectionLabel
import io.nicolaszurbuchen.pop_know.infra.design.component.PopKnowTopBar
import io.nicolaszurbuchen.pop_know.infra.design.theme.JetBrainsMonoFontFamily
import io.nicolaszurbuchen.pop_know.infra.design.theme.SpaceGroteskFontFamily
import io.nicolaszurbuchen.pop_know.infra.design.theme.popKnowColors
import io.nicolaszurbuchen.pop_know.infra.design.theme.popKnowGameColors
import io.nicolaszurbuchen.pop_know.infra.design.theme.spacing
import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import io.nicolaszurbuchen.pop_know.infra.ui.asString
import popknow.shared.generated.resources.Res
import popknow.shared.generated.resources.stats_appbar_center
import popknow.shared.generated.resources.stats_appbar_right
import popknow.shared.generated.resources.stats_back
import popknow.shared.generated.resources.stats_overall
import popknow.shared.generated.resources.stats_section_category
import popknow.shared.generated.resources.stats_summary_correct
import kotlin.math.roundToInt

@Composable
fun StatsScreen(
    state: StatsState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        PopKnowTopBar(
            left = UiText.Resource(Res.string.stats_back),
            center = UiText.Resource(Res.string.stats_appbar_center),
            right = UiText.Resource(Res.string.stats_appbar_right),
            onBack = onBackClick,
        )

        HorizontalDivider(color = MaterialTheme.colorScheme.outline)

        Box(modifier = Modifier.weight(1f)) {
            when {
                state.isLoading -> Skeleton()

                state.error != null -> PopKnowErrorView(
                    onRetry = onRetryClick,
                )

                state.stats != null -> Content(
                    stats = state.stats,
                )
            }
        }
    }
}

@Composable
private fun Content(
    stats: FullStats,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.spacing.md),
    ) {
        item {
            OverallSummary(
                summary = stats.summary,
                perDifficulty = stats.perDifficulty
            )
        }

        item {
            PopKnowSectionLabel(
                text = UiText.Resource(Res.string.stats_section_category),
                showSlashes = false,
                modifier = Modifier.padding(top = MaterialTheme.spacing.xl)
            )
            HorizontalDivider(
                modifier = Modifier.padding(top = MaterialTheme.spacing.sm),
                color = MaterialTheme.colorScheme.outline
            )
        }

        itemsIndexed(stats.perCategory) { index, categoryStats ->
            CategoryRow(
                index = index + 1,
                stats = categoryStats
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        }
        
        item { Spacer(Modifier.height(MaterialTheme.spacing.xl)) }
    }
}

@Composable
private fun OverallSummary(
    summary: io.nicolaszurbuchen.pop_know.common.domain.AnswerStats,
    perDifficulty: List<DifficultyStats>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.spacing.xl),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(180.dp),
            contentAlignment = Alignment.Center
        ) {
            DifficultyRings(perDifficulty)
        }

        Column(
            modifier = Modifier
                .padding(start = MaterialTheme.spacing.lg)
                .weight(1f)
        ) {
            PopKnowSectionLabel(
                text = UiText.Resource(Res.string.stats_overall),
                showSlashes = false
            )

            Text(
                text = "${(summary.accuracy * 100).roundToInt()}%",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = SpaceGroteskFontFamily,
                )
            )

            Text(
                text = UiText.Resource(
                    Res.string.stats_summary_correct,
                    listOf(summary.totalCorrect, summary.totalAnswered)
                ).asString().uppercase(),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                    fontFamily = JetBrainsMonoFontFamily,
                    letterSpacing = 1.sp
                )
            )

            Spacer(Modifier.height(MaterialTheme.spacing.md))

            perDifficulty.forEach { diffStats ->
                DifficultySummaryRow(diffStats)
            }
        }
    }
}

@Composable
private fun DifficultyRings(perDifficulty: List<DifficultyStats>) {
    val gameColors = MaterialTheme.popKnowGameColors
    val inactiveColor = MaterialTheme.colorScheme.outlineVariant

    Canvas(modifier = Modifier.fillMaxSize()) {
        val strokeWidth = 10.dp.toPx()
        val spacing = 8.dp.toPx()
        
        perDifficulty.asReversed().forEachIndexed { index, diffStats ->
            val radius = size.minDimension / 2 - (index * (strokeWidth + spacing)) - strokeWidth / 2
            val color = when (diffStats.difficulty) {
                Difficulty.EASY -> gameColors.difficultyEasy
                Difficulty.MEDIUM -> gameColors.difficultyMedium
                Difficulty.HARD -> gameColors.difficultyHard
            }
            
            // Inactive ring
            drawCircle(
                color = inactiveColor,
                radius = radius,
                style = Stroke(width = strokeWidth)
            )
            
            // Active arc
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = diffStats.answerStats.accuracy * 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
                topLeft = androidx.compose.ui.geometry.Offset(center.x - radius, center.y - radius)
            )
        }
    }
}

@Composable
private fun DifficultySummaryRow(stats: DifficultyStats) {
    val color = when (stats.difficulty) {
        Difficulty.EASY -> MaterialTheme.popKnowGameColors.difficultyEasy
        Difficulty.MEDIUM -> MaterialTheme.popKnowGameColors.difficultyMedium
        Difficulty.HARD -> MaterialTheme.popKnowGameColors.difficultyHard
    }
    val accuracy = (stats.answerStats.accuracy * 100).roundToInt()
    val accuracyColor = when {
        accuracy >= 80 -> MaterialTheme.popKnowGameColors.correct
        accuracy >= 50 -> MaterialTheme.popKnowGameColors.difficultyMedium
        else -> MaterialTheme.popKnowGameColors.wrong
    }

    Row(
        modifier = Modifier.padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )
        Spacer(Modifier.width(MaterialTheme.spacing.sm))
        Text(
            text = stats.difficulty.name,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp,
                fontFamily = JetBrainsMonoFontFamily,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${stats.answerStats.totalCorrect}/${stats.answerStats.totalAnswered}",
            style = TextStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                fontFamily = JetBrainsMonoFontFamily,
            )
        )
        Spacer(Modifier.width(MaterialTheme.spacing.md))
        Text(
            text = "$accuracy%",
            style = TextStyle(
                color = accuracyColor,
                fontSize = 12.sp,
                fontFamily = JetBrainsMonoFontFamily,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier.width(36.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )
    }
}

@Composable
private fun CategoryRow(
    index: Int,
    stats: CategoryStats
) {
    val accuracy = (stats.answerStats.accuracy * 100).roundToInt()
    val accuracyColor = when {
        accuracy >= 80 -> MaterialTheme.popKnowGameColors.correct
        accuracy >= 50 -> MaterialTheme.popKnowGameColors.difficultyMedium
        else -> MaterialTheme.popKnowGameColors.wrong
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = index.toString().padStart(2, '0'),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                fontFamily = JetBrainsMonoFontFamily,
            )
        )
        Spacer(Modifier.width(MaterialTheme.spacing.md))
        Text(
            text = stats.category.category.uppercase(),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                fontFamily = JetBrainsMonoFontFamily,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${stats.answerStats.totalCorrect}/${stats.answerStats.totalAnswered}",
            style = TextStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                fontFamily = JetBrainsMonoFontFamily,
            )
        )
        Spacer(Modifier.width(MaterialTheme.spacing.md))
        Text(
            text = "$accuracy%",
            style = TextStyle(
                color = accuracyColor,
                fontSize = 14.sp,
                fontFamily = JetBrainsMonoFontFamily,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier.width(48.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )
    }
}

@Composable
private fun Skeleton() {
    val popKnowColors = MaterialTheme.popKnowColors
    val spacing = MaterialTheme.spacing

    val shimmerAlpha by rememberInfiniteTransition(label = "shimmer")
        .animateFloat(
            initialValue = 0.35f,
            targetValue = 0.85f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 900, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "shimmer-alpha",
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = spacing.md),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacing.xl),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .background(popKnowColors.surface.copy(alpha = shimmerAlpha))
            )

            Column(
                modifier = Modifier
                    .padding(start = spacing.lg)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(spacing.sm)
            ) {
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(20.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(popKnowColors.surface.copy(alpha = shimmerAlpha))
                )
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(72.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(popKnowColors.surface.copy(alpha = shimmerAlpha))
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(popKnowColors.surface.copy(alpha = shimmerAlpha))
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(top = spacing.xl)
                .width(150.dp)
                .height(20.dp)
                .clip(MaterialTheme.shapes.small)
                .background(popKnowColors.surface.copy(alpha = shimmerAlpha))
        )

        Spacer(Modifier.height(spacing.sm))

        repeat(5) {
            Box(
                modifier = Modifier
                    .padding(vertical = spacing.xs)
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(popKnowColors.surface.copy(alpha = shimmerAlpha))
            )
        }
    }
}
