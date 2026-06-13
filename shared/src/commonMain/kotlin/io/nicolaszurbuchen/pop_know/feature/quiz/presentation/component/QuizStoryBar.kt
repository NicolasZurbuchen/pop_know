package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.nicolaszurbuchen.pop_know.infra.design.theme.popKnowColors
import io.nicolaszurbuchen.pop_know.infra.design.theme.spacing

@Composable
fun QuizStoryBar(
    totalQuestions: Int,
    currentIndex: Int,
    timerSeconds: Int,
    maxTimerSeconds: Int,
) {
    val animatedFraction by animateFloatAsState(
        targetValue = timerSeconds.toFloat() / maxTimerSeconds.toFloat().coerceAtLeast(1f),
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        label = "storyProgress",
    )
    val neonColor = MaterialTheme.popKnowColors.accent
    val trackColor = MaterialTheme.colorScheme.outline

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs),
    ) {
        repeat(totalQuestions) { index ->
            val fillFraction = when {
                index < currentIndex -> 1f
                index == currentIndex -> animatedFraction
                else -> 0f
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(3.dp)
                    .clip(CircleShape)
                    .background(trackColor),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fillFraction)
                        .background(neonColor),
                )
            }
        }
    }
}