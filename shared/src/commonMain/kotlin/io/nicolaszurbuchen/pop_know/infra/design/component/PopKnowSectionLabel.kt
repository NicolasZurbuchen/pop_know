package io.nicolaszurbuchen.pop_know.infra.design.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.pop_know.infra.design.theme.JetBrainsMonoFontFamily
import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import io.nicolaszurbuchen.pop_know.infra.ui.asString

@Composable
fun PopKnowSectionLabel(
    text: UiText,
    modifier: Modifier = Modifier,
    showSlashes: Boolean = false
) {
    Text(
        text = if (showSlashes) "// ${text.asString()}" else text.asString(),
        style = TextStyle(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = JetBrainsMonoFontFamily,
            letterSpacing = 2.sp,
            lineHeight = 17.sp,
        ),
        modifier = modifier,
    )
}