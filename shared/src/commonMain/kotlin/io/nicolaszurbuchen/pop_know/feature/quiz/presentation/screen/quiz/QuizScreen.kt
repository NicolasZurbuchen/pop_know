package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import io.nicolaszurbuchen.pop_know.app.design.component.PopKnowConfirmDialog
import io.nicolaszurbuchen.pop_know.app.design.component.PopKnowErrorBanner
import io.nicolaszurbuchen.pop_know.app.design.component.PopKnowErrorView
import io.nicolaszurbuchen.pop_know.app.design.component.PopKnowTopBar
import io.nicolaszurbuchen.pop_know.app.design.theme.popKnowColors
import io.nicolaszurbuchen.pop_know.app.design.theme.spacing
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.color
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.toText
import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import io.nicolaszurbuchen.pop_know.infra.ui.asString
import popknow.shared.generated.resources.Res
import popknow.shared.generated.resources.common_cancel
import popknow.shared.generated.resources.quiz_appbar_center
import popknow.shared.generated.resources.quiz_appbar_left
import popknow.shared.generated.resources.quiz_appbar_right
import popknow.shared.generated.resources.quiz_next
import popknow.shared.generated.resources.quiz_question_label
import popknow.shared.generated.resources.quiz_quit_dialog_confirm
import popknow.shared.generated.resources.quiz_quit_dialog_text
import popknow.shared.generated.resources.quiz_quit_dialog_title

@Composable
fun QuizScreen(
    state: QuizUiModel,
    onSelectAnswer: (String) -> Unit,
    onNextClick: () -> Unit,
    onSeeResultClick: () -> Unit,
    onRetryClick: () -> Unit,
    onDismissInsertionErrorClick: () -> Unit,
    onShowQuitDialog: (Boolean) -> Unit,
    onConfirmQuit: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        if (state.isQuitDialogOpen) {
            PopKnowConfirmDialog(
                title = UiText.Resource(Res.string.quiz_quit_dialog_title).asString(),
                text = UiText.Resource(Res.string.quiz_quit_dialog_text).asString(),
                confirmText = UiText.Resource(Res.string.quiz_quit_dialog_confirm).asString(),
                dismissText = UiText.Resource(Res.string.common_cancel).asString(),
                onConfirm = onConfirmQuit,
                onDismiss = { onShowQuitDialog(false) }
            )
        }

        when {
            state.isLoading -> Skeleton()

            state.initialError != null -> PopKnowErrorView(
                title = state.initialError.title,
                subtitle = state.initialError.subtitle,
                icon = state.initialError.icon,
                onRetry = onRetryClick,
            )

            state.quizData != null -> Column {
                PopKnowTopBar(
                    left = UiText.Resource(Res.string.quiz_appbar_left),
                    center = UiText.Resource(Res.string.quiz_appbar_center),
                    right = UiText.Resource(Res.string.quiz_appbar_right),
                    backIcon = Icons.Default.Close,
                    onBack = { onShowQuitDialog(true) }
                )
                state.insertionError?.let {
                    PopKnowErrorBanner(
                        text = state.insertionError.title,
                        icon = state.insertionError.icon,
                        onRetry = null,
                        onDismiss = onDismissInsertionErrorClick
                    )
                }
                SessionContent(
                    ui = state.quizData,
                    onSelectAnswer = onSelectAnswer,
                    onNextClick = onNextClick,
                    onSeeResultClick = onSeeResultClick,
                )
            }
        }
    }
}

@Composable
private fun SessionContent(
    ui: QuizDataUiModel,
    onSelectAnswer: (String) -> Unit,
    onNextClick: () -> Unit,
    onSeeResultClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = MaterialTheme.spacing.md,
                    vertical = MaterialTheme.spacing.md,
                ),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md),
        ) {
            StoryBar(
                totalQuestions = ui.totalQuestions,
                currentIndex = ui.currentIndex,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = UiText.Resource(Res.string.quiz_question_label).asString().uppercase(),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Text(
                        text = ui.progressText,
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
                Timer(
                    seconds = ui.timerSeconds,
                    maxSeconds = ui.maxTimerSeconds,
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(ui.difficulty.color(), CircleShape),
                    )
                    Spacer(Modifier.width(MaterialTheme.spacing.xs))
                }
                Spacer(Modifier.width(MaterialTheme.spacing.xs))
                Text(
                    text = "${ui.difficulty.toText().asString().uppercase()} · ${ui.categoryText}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }

            Text(
                text = ui.questionText,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(Modifier.height(MaterialTheme.spacing.xs))

            ui.choices.forEach { choice ->
                Answers(
                    choice = choice,
                    isAnswered = ui.isAnswered,
                    onSelect = { onSelectAnswer(choice.text) },
                )
            }
        }

        if (ui.isAnswered && ui.resultChoice != null) {
            ResultBar(
                resultChoice = ui.resultChoice,
                isLastQuestion = ui.isLastQuestion,
                onNextClick = onNextClick,
                onSeeResultClick = onSeeResultClick,
            )
        }
    }
}

@Composable
private fun Timer(seconds: Int, maxSeconds: Int) {
    val animatedFraction by animateFloatAsState(
        targetValue = seconds.toFloat() / maxSeconds.toFloat().coerceAtLeast(1f),
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        label = "timerArc",
    )
    val neonColor = MaterialTheme.popKnowColors.accent
    val trackColor = MaterialTheme.colorScheme.outline

    Box(
        modifier = Modifier.size(56.dp),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 3.dp.toPx()
            val arcDiameter = size.minDimension - strokeWidth
            val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
            val arcSize = Size(arcDiameter, arcDiameter)

            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            )
            drawArc(
                color = neonColor,
                startAngle = -90f,
                sweepAngle = 360f * animatedFraction,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            )
        }
        Text(
            text = "$seconds",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun Answers(
    choice: QuizChoiceUiModel,
    isAnswered: Boolean,
    onSelect: () -> Unit,
) {
    val isHighlighted = choice.status != null
    val backgroundColor = choice.backgroundColor()
    val showBorder = !isHighlighted
    val contentColor = choice.contentColor()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (showBorder) {
                    Modifier.border(1.5.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium)
                } else {
                    Modifier
                }
            )
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor)
            .clickable(enabled = !isAnswered) { onSelect() }
            .padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md),
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .border(1.5.dp, contentColor, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = choice.letter,
                    style = MaterialTheme.typography.labelLarge,
                    color = contentColor,
                )
            }
            Text(
                text = choice.text,
                style = MaterialTheme.typography.titleMedium,
                color = contentColor,
            )
        }
        if (choice.status == AnswerStatusUiModel.CORRECT) {
            Icon(Icons.Default.Check, contentDescription = null, tint = contentColor)
        } else if (choice.status == AnswerStatusUiModel.INCORRECT) {
            Icon(Icons.Default.Close, contentDescription = null, tint = contentColor)
        }
    }
}

@Composable
private fun StoryBar(
    totalQuestions: Int,
    currentIndex: Int,
) {
    val neonColor = MaterialTheme.popKnowColors.accent
    val trackColor = MaterialTheme.colorScheme.outline

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs),
    ) {
        repeat(totalQuestions) { index ->
            val fillFraction = if (index <= currentIndex) 1f else 0f
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

@Composable
private fun ResultBar(
    resultChoice: QuizChoiceUiModel,
    isLastQuestion: Boolean,
    onNextClick: () -> Unit,
    onSeeResultClick: () -> Unit,
) {
    val barColor = resultChoice.backgroundColor()
    val barContentColor = resultChoice.contentColor()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(barColor)
            .padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.lg),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = "· ${resultChoice.status?.toLabel()?.asString()?.uppercase() ?: ""}",
                style = MaterialTheme.typography.displaySmall,
                color = barContentColor,
            )
            Text(
                text = resultChoice.status?.toHeadline()?.asString()?.uppercase() ?: "",
                style = MaterialTheme.typography.headlineLarge,
                color = barContentColor,
            )
        }
        Button(
            onClick = {
                if (isLastQuestion) onSeeResultClick() else onNextClick()
            },
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.popKnowColors.textPrimary,
                contentColor = MaterialTheme.popKnowColors.background,
            ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs),
            ) {
                Text(
                    text = UiText.Resource(Res.string.quiz_next).asString().uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.popKnowColors.background,
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.popKnowColors.background,
                )
            }
        }
    }
}

@Composable
private fun Skeleton(modifier: Modifier = Modifier) {
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
