package io.nicolaszurbuchen.pop_know.infra.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import io.nicolaszurbuchen.pop_know.infra.design.theme.Black
import io.nicolaszurbuchen.pop_know.infra.design.theme.appColors
import io.nicolaszurbuchen.pop_know.infra.design.theme.spacing

@Composable
fun PopKnowPerformanceTag(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.appColors.neonAccent,
                shape = RectangleShape
            )
            .padding(
                horizontal = MaterialTheme.spacing.sm,
                vertical = MaterialTheme.spacing.xs
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = Black
        )
    }
}