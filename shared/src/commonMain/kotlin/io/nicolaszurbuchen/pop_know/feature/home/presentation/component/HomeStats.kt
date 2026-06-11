package io.nicolaszurbuchen.pop_know.feature.home.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.pop_know.core.presentation.UiText
import io.nicolaszurbuchen.pop_know.core.presentation.asString
import io.nicolaszurbuchen.pop_know.core.ui.theme.JetBrainsMonoFontFamily
import io.nicolaszurbuchen.pop_know.core.ui.theme.SpaceGroteskFontFamily
import io.nicolaszurbuchen.pop_know.core.ui.theme.spacing
import org.jetbrains.compose.resources.stringResource
import popknow.shared.generated.resources.Res
import popknow.shared.generated.resources.home_stats_correct
import popknow.shared.generated.resources.home_stats_played
import popknow.shared.generated.resources.home_stats_ratio

@Composable
fun HomeStats(
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
                shape = MaterialTheme.shapes.small
            )
            .padding(
                horizontal = MaterialTheme.spacing.lg,
                vertical = MaterialTheme.spacing.md,
            )
    ) {
        HomeStatsItem(
            label = stringResource(Res.string.home_stats_played),
            value = played.asString(),
            alignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
        )
        VerticalDivider(
            modifier = Modifier
                .height(48.dp),
            color = MaterialTheme.colorScheme.outline,
        )
        HomeStatsItem(
            label = stringResource(Res.string.home_stats_correct),
            value = correct.asString(),
            alignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
        )
        VerticalDivider(
            modifier = Modifier
                .height(48.dp),
            color = MaterialTheme.colorScheme.outline,
        )
        HomeStatsItem(
            label = stringResource(Res.string.home_stats_ratio),
            value = ratio.asString(),
            alignment = Alignment.End,
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
private fun HomeStatsItem(
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