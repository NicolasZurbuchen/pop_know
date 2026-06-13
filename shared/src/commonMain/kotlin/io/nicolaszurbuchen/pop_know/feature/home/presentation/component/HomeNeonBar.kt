package io.nicolaszurbuchen.pop_know.feature.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.pop_know.infra.design.theme.SpaceGroteskFontFamily
import io.nicolaszurbuchen.pop_know.infra.design.theme.popKnowColors
import io.nicolaszurbuchen.pop_know.infra.design.theme.spacing

@Composable
fun PopKnowNeonBar(
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.popKnowColors.accent)
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