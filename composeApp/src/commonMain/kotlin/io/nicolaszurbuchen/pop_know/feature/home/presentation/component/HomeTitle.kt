package io.nicolaszurbuchen.pop_know.feature.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.pop_know.core.presentation.UiText
import io.nicolaszurbuchen.pop_know.core.presentation.asString
import io.nicolaszurbuchen.pop_know.core.ui.theme.SpaceGroteskFontFamily
import io.nicolaszurbuchen.pop_know.core.ui.theme.appColors
import io.nicolaszurbuchen.pop_know.core.ui.theme.spacing
import popknow.composeapp.generated.resources.Res
import popknow.composeapp.generated.resources.home_title_lower
import popknow.composeapp.generated.resources.home_title_subtitle
import popknow.composeapp.generated.resources.home_title_upper

@Composable
fun HomeTitle(
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
                    .background(color = MaterialTheme.appColors.neonAccent)
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
                    color = MaterialTheme.appColors.neonAccent,
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