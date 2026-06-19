package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result

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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.pop_know.app.design.component.PopKnowErrorView
import io.nicolaszurbuchen.pop_know.app.design.component.PopKnowPrimaryButton
import io.nicolaszurbuchen.pop_know.app.design.component.PopKnowSecondaryButton
import io.nicolaszurbuchen.pop_know.app.design.component.PopKnowSectionLabel
import io.nicolaszurbuchen.pop_know.app.design.component.PopKnowTopBar
import io.nicolaszurbuchen.pop_know.app.design.theme.SpaceGroteskFontFamily
import io.nicolaszurbuchen.pop_know.app.design.theme.popKnowColors
import io.nicolaszurbuchen.pop_know.app.design.theme.spacing
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.AnswerStatusUiModel
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.backgroundColor
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.contentColor
import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import io.nicolaszurbuchen.pop_know.infra.ui.asString
import popknow.shared.generated.resources.Res
import popknow.shared.generated.resources.home_view_stats
import popknow.shared.generated.resources.quiz_result_no_results
import popknow.shared.generated.resources.result_appbar_left
import popknow.shared.generated.resources.result_home
import popknow.shared.generated.resources.result_performance_cold
import popknow.shared.generated.resources.result_performance_hot
import popknow.shared.generated.resources.result_performance_legendary
import popknow.shared.generated.resources.result_performance_warm
import popknow.shared.generated.resources.result_play_again
import popknow.shared.generated.resources.result_section_by_question
import popknow.shared.generated.resources.result_section_ratio
import popknow.shared.generated.resources.result_summary
import kotlin.math.roundToInt

@Composable
fun ResultScreen(
    state: ResultUiModel,
    onNavigateHomeClick: () -> Unit,
    onPlayAgainClick: () -> Unit,
    onViewStatsClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        PopKnowTopBar(
            left = UiText.Resource(Res.string.result_home),
            center = UiText.Resource(Res.string.result_appbar_left),
            right =
                state.content?.let {
                    UiText.Raw("${it.score.totalCorrect} / ${it.score.totalAnswered}")
                },
            onBack = onNavigateHomeClick,
        )

        Box(modifier = Modifier.weight(1f)) {
            when {
                state.isLoading -> {
                    Skeleton()
                }

                state.error != null -> {
                    PopKnowErrorView(
                        title = state.error.title,
                        subtitle = state.error.subtitle,
                        icon = state.error.icon,
                        onRetry = onRetryClick,
                    )
                }

                state.content == null -> {
                    Text(
                        text = UiText.Resource(Res.string.quiz_result_no_results).asString(),
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                else -> {
                    Content(
                        result = state.content,
                    )
                }
            }
        }

        ActionButtons(
            onPlayAgainClick = onPlayAgainClick,
            onViewStatsClick = onViewStatsClick,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Content(result: GameResultUiModel) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.spacing.md),
    ) {
        PopKnowSectionLabel(
            text = UiText.Resource(Res.string.result_section_ratio),
            showSlashes = true,
            modifier = Modifier.padding(top = MaterialTheme.spacing.xxl),
        )

        Text(
            text = "${(result.score.accuracy * 100).roundToInt()}%",
            style =
                TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 120.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = SpaceGroteskFontFamily,
                    lineHeight = 120.sp,
                ),
            modifier = Modifier.padding(top = MaterialTheme.spacing.xs),
        )

        val performanceLevel = getPerformanceLevel(result.score.accuracy)
        PopKnowPerformanceTag(
            text = performanceLevel.asString().uppercase(),
            modifier = Modifier.padding(top = MaterialTheme.spacing.sm),
        )

        Text(
            text =
                UiText.Resource(
                    Res.string.result_summary,
                    listOf(result.correctCount, result.score.totalAnswered, result.timeoutCount),
                ).asString(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = MaterialTheme.spacing.lg),
        )

        PopKnowSectionLabel(
            text = UiText.Resource(Res.string.result_section_by_question),
            modifier = Modifier.padding(top = MaterialTheme.spacing.xxl),
        )

        FlowRow(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.sm),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs),
        ) {
            result.questions.forEach { questionResult ->
                PopKnowQuestionResultDot(status = questionResult.status)
            }
        }
    }
}

@Composable
private fun ActionButtons(
    onPlayAgainClick: () -> Unit,
    onViewStatsClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .padding(MaterialTheme.spacing.md)
                .padding(bottom = MaterialTheme.spacing.lg),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
    ) {
        PopKnowPrimaryButton(
            text = UiText.Resource(Res.string.result_play_again),
            onClick = onPlayAgainClick,
        )

        PopKnowSecondaryButton(
            text = UiText.Resource(Res.string.home_view_stats),
            onClick = onViewStatsClick,
        )
    }
}

@Composable
private fun PopKnowPerformanceTag(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .background(
                    color = MaterialTheme.popKnowColors.accent,
                    shape = RectangleShape,
                )
                .padding(
                    horizontal = MaterialTheme.spacing.sm,
                    vertical = MaterialTheme.spacing.xs,
                ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.popKnowColors.onAccent,
        )
    }
}

@Composable
private fun PopKnowQuestionResultDot(
    status: AnswerStatusUiModel,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(24.dp)
                .background(
                    color = status.backgroundColor(),
                    shape = MaterialTheme.shapes.extraSmall,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector =
                when (status) {
                    AnswerStatusUiModel.CORRECT -> Icons.Default.Check
                    AnswerStatusUiModel.INCORRECT, AnswerStatusUiModel.TIMEOUT -> Icons.Default.Close
                },
            contentDescription = null,
            tint = status.contentColor(),
            modifier = Modifier.size(14.dp),
        )
    }
}

private fun getPerformanceLevel(accuracy: Float): UiText =
    when {
        accuracy >= 0.85f -> UiText.Resource(Res.string.result_performance_legendary)
        accuracy >= 0.60f -> UiText.Resource(Res.string.result_performance_hot)
        accuracy >= 0.25f -> UiText.Resource(Res.string.result_performance_warm)
        else -> UiText.Resource(Res.string.result_performance_cold)
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
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = spacing.md),
    ) {
        Box(
            modifier =
                Modifier
                    .padding(top = spacing.xxl)
                    .width(120.dp)
                    .height(20.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(popKnowColors.surface.copy(alpha = shimmerAlpha)),
        )

        Box(
            modifier =
                Modifier
                    .padding(top = spacing.xs)
                    .width(200.dp)
                    .height(120.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(popKnowColors.surface.copy(alpha = shimmerAlpha)),
        )

        Box(
            modifier =
                Modifier
                    .padding(top = spacing.sm)
                    .width(100.dp)
                    .height(24.dp)
                    .clip(CircleShape)
                    .background(popKnowColors.surface.copy(alpha = shimmerAlpha)),
        )

        Box(
            modifier =
                Modifier
                    .padding(top = spacing.lg)
                    .fillMaxWidth(0.8f)
                    .height(20.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(popKnowColors.surface.copy(alpha = shimmerAlpha)),
        )

        Box(
            modifier =
                Modifier
                    .padding(top = spacing.xxl)
                    .width(150.dp)
                    .height(20.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(popKnowColors.surface.copy(alpha = shimmerAlpha)),
        )

        Row(
            modifier =
                Modifier
                    .padding(top = spacing.sm)
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.xs),
        ) {
            repeat(5) {
                Box(
                    modifier =
                        Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(popKnowColors.surface.copy(alpha = shimmerAlpha)),
                )
            }
        }
    }
}
