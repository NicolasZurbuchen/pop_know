package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.nicolaszurbuchen.pop_know.infra.design.theme.popKnowColors
import io.nicolaszurbuchen.pop_know.infra.design.theme.spacing

@Composable
fun QuizSkeleton(modifier: Modifier = Modifier) {
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
        modifier =
            modifier
                .fillMaxSize()
                .padding(
                    horizontal = spacing.md,
                    vertical = spacing.md,
                ),
        verticalArrangement = Arrangement.spacedBy(spacing.md),
    ) {
        // Story Bar Placeholder
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.xs),
        ) {
            repeat(10) {
                Box(
                    modifier =
                        Modifier
                            .weight(1f)
                            .height(3.dp)
                            .clip(CircleShape)
                            .background(popKnowColors.surface.copy(alpha = shimmerAlpha)),
                )
            }
        }

        // Question Header Placeholder
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
                Box(
                    modifier =
                        Modifier
                            .width(80.dp)
                            .height(20.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(popKnowColors.surface.copy(alpha = shimmerAlpha)),
                )
                Box(
                    modifier =
                        Modifier
                            .width(120.dp)
                            .height(32.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(popKnowColors.surface.copy(alpha = shimmerAlpha)),
                )
            }
            // Timer Placeholder
            Box(
                modifier =
                    Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(popKnowColors.surface.copy(alpha = shimmerAlpha)),
            )
        }

        // Difficulty/Category Placeholder
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.xs),
        ) {
            repeat(3) {
                Box(
                    modifier =
                        Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(popKnowColors.surface.copy(alpha = shimmerAlpha)),
                )
            }
            Spacer(Modifier.width(spacing.xs))
            Box(
                modifier =
                    Modifier
                        .width(150.dp)
                        .height(20.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(popKnowColors.surface.copy(alpha = shimmerAlpha)),
            )
        }

        // Question Text Placeholder
        Column(verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(0.9f)
                        .height(24.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(popKnowColors.surface.copy(alpha = shimmerAlpha)),
            )
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(0.7f)
                        .height(24.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(popKnowColors.surface.copy(alpha = shimmerAlpha)),
            )
        }

        Spacer(Modifier.height(spacing.xs))

        // Answer Choices Placeholder
        repeat(4) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(popKnowColors.surface.copy(alpha = shimmerAlpha)),
            )
        }
    }
}
