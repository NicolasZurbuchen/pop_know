package io.nicolaszurbuchen.pop_know.feature.home.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.pop_know.core.presentation.UiText
import io.nicolaszurbuchen.pop_know.core.presentation.asString
import io.nicolaszurbuchen.pop_know.core.ui.component.PopKnowButton
import io.nicolaszurbuchen.pop_know.core.ui.component.PopKnowButtonVariant
import io.nicolaszurbuchen.pop_know.feature.home.presentation.component.PopKnowNeonBar
import io.nicolaszurbuchen.pop_know.core.ui.component.PopKnowSectionLabel
import io.nicolaszurbuchen.pop_know.feature.home.presentation.component.HomeStats
import io.nicolaszurbuchen.pop_know.core.ui.component.PopKnowTopBar
import io.nicolaszurbuchen.pop_know.core.ui.theme.SpaceGroteskFontFamily
import io.nicolaszurbuchen.pop_know.core.ui.theme.spacing
import io.nicolaszurbuchen.pop_know.feature.home.presentation.component.HomeTitle
import io.nicolaszurbuchen.pop_know.feature.home.presentation.model.HomeIntent
import io.nicolaszurbuchen.pop_know.feature.home.presentation.model.HomeState
import popknow.composeapp.generated.resources.Res
import popknow.composeapp.generated.resources.home_appbar_center
import popknow.composeapp.generated.resources.home_appbar_left
import popknow.composeapp.generated.resources.home_appbar_right
import popknow.composeapp.generated.resources.home_section_title
import popknow.composeapp.generated.resources.home_start_round
import popknow.composeapp.generated.resources.home_stats_correct
import popknow.composeapp.generated.resources.home_stats_played
import popknow.composeapp.generated.resources.home_stats_ratio
import popknow.composeapp.generated.resources.home_stats_ratio_value
import popknow.composeapp.generated.resources.home_title_subtitle
import popknow.composeapp.generated.resources.home_view_stats
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        PopKnowTopBar(
            left = UiText.Resource(Res.string.home_appbar_left),
            center = UiText.Resource(Res.string.home_appbar_center),
            right = UiText.Resource(Res.string.home_appbar_right),
        )

        PopKnowNeonBar()

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

            HomeTitle()
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.spacing.md,
                    vertical = MaterialTheme.spacing.lg
                ),
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.primary,
                )
            } else {
                state.content?.let { content ->
                    HomeStats(
                        played = UiText.Dynamic(content.stats.totalAnswered.toString()),
                        correct = UiText.Dynamic(content.stats.totalCorrect.toString()),
                        ratio = UiText.Resource(Res.string.home_stats_ratio_value, listOf((content.stats.accuracy * 100).roundToInt()))
                    )
                }
            }

            PopKnowButton(
                text = UiText.Resource(Res.string.home_start_round),
                onClick = { onIntent(HomeIntent.NavigateToPlay) },
                variant = PopKnowButtonVariant.Primary,
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.sm)
            )

            if (state.hasHistory) {
                PopKnowButton(
                    text = UiText.Resource(Res.string.home_view_stats),
                    onClick = { onIntent(HomeIntent.NavigateToStats) },
                    variant = PopKnowButtonVariant.Secondary,
                )
            }
        }
    }
}
