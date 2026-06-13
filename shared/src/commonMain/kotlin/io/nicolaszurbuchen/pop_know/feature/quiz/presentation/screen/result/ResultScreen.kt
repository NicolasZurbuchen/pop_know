package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.GameResult
import io.nicolaszurbuchen.pop_know.infra.design.component.PopKnowButton
import io.nicolaszurbuchen.pop_know.infra.design.component.PopKnowButtonVariant
import io.nicolaszurbuchen.pop_know.infra.design.component.PopKnowPerformanceTag
import io.nicolaszurbuchen.pop_know.infra.design.component.PopKnowQuestionResultDot
import io.nicolaszurbuchen.pop_know.infra.design.component.PopKnowSectionLabel
import io.nicolaszurbuchen.pop_know.infra.design.component.PopKnowTopBar
import io.nicolaszurbuchen.pop_know.infra.design.theme.SpaceGroteskFontFamily
import io.nicolaszurbuchen.pop_know.infra.design.theme.spacing
import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import io.nicolaszurbuchen.pop_know.infra.ui.asString
import popknow.shared.generated.resources.Res
import popknow.shared.generated.resources.result_appbar_left
import popknow.shared.generated.resources.result_home
import popknow.shared.generated.resources.result_performance_cold
import popknow.shared.generated.resources.result_performance_hot
import popknow.shared.generated.resources.result_performance_legendary
import popknow.shared.generated.resources.result_performance_warm
import popknow.shared.generated.resources.result_play_again
import popknow.shared.generated.resources.result_section_by_question
import popknow.shared.generated.resources.result_section_ratio
import popknow.shared.generated.resources.result_stats
import popknow.shared.generated.resources.result_summary
import kotlin.math.roundToInt

@Composable
fun ResultScreen(
    state: ResultState,
    onNavigateHomeClick: () -> Unit,
    onPlayAgainClick: () -> Unit,
    onViewStatsClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        PopKnowTopBar(
            left = UiText.Resource(Res.string.result_appbar_left),
            right = state.content?.let { 
                UiText.Raw("${it.score.totalCorrect} / ${it.score.totalAnswered}")
            },
        )

        Box(modifier = Modifier.weight(1f)) {
            when {
                state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                state.content == null -> Text(
                    text = "No results available.",
                    modifier = Modifier.align(Alignment.Center)
                )
                else -> ResultContent(
                    result = state.content,
                )
            }
        }

        ResultButtons(
            onNavigateHomeClick = onNavigateHomeClick,
            onPlayAgainClick = onPlayAgainClick,
            onViewStatsClick = onViewStatsClick,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ResultContent(
    result: GameResult,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.spacing.md),
    ) {
        PopKnowSectionLabel(
            text = UiText.Resource(Res.string.result_section_ratio),
            showSlashes = true,
            modifier = Modifier.padding(top = MaterialTheme.spacing.xxl)
        )

        Text(
            text = "${(result.score.accuracy * 100).roundToInt()}%",
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 120.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = SpaceGroteskFontFamily,
                lineHeight = 120.sp,
            ),
            modifier = Modifier.padding(top = MaterialTheme.spacing.xs)
        )

        val performanceLevel = getPerformanceLevel(result.score.accuracy)
        PopKnowPerformanceTag(
            text = performanceLevel.asString().uppercase(),
            modifier = Modifier.padding(top = MaterialTheme.spacing.sm)
        )

        Text(
            text = UiText.Resource(
                Res.string.result_summary,
                listOf(result.correctCount, result.score.totalAnswered, result.timeoutCount)
            ).asString(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = MaterialTheme.spacing.lg)
        )

        PopKnowSectionLabel(
            text = UiText.Resource(Res.string.result_section_by_question),
            modifier = Modifier.padding(top = MaterialTheme.spacing.xxl)
        )

        FlowRow(
            modifier = Modifier
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
private fun ResultButtons(
    onNavigateHomeClick: () -> Unit,
    onPlayAgainClick: () -> Unit,
    onViewStatsClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(MaterialTheme.spacing.md)
            .padding(bottom = MaterialTheme.spacing.lg),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
    ) {
        PopKnowButton(
            text = UiText.Resource(Res.string.result_play_again),
            onClick = onPlayAgainClick,
            variant = PopKnowButtonVariant.Primary,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
        ) {
            PopKnowButton(
                text = UiText.Resource(Res.string.result_stats),
                onClick = onViewStatsClick,
                variant = PopKnowButtonVariant.Secondary,
                modifier = Modifier.weight(1f)
            )
            PopKnowButton(
                text = UiText.Resource(Res.string.result_home),
                onClick = onNavigateHomeClick,
                variant = PopKnowButtonVariant.Secondary,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private fun getPerformanceLevel(accuracy: Float): UiText = when {
    accuracy >= 0.85f -> UiText.Resource(Res.string.result_performance_legendary)
    accuracy >= 0.60f -> UiText.Resource(Res.string.result_performance_hot)
    accuracy >= 0.25f -> UiText.Resource(Res.string.result_performance_warm)
    else -> UiText.Resource(Res.string.result_performance_cold)
}
