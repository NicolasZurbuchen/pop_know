package io.nicolaszurbuchen.pop_know.app.design.component

import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.pop_know.app.design.theme.SpaceGroteskFontFamily
import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import io.nicolaszurbuchen.pop_know.infra.ui.asString

@Composable
fun PopKnowTextButton(
    text: UiText,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    enabled: Boolean = true,
) {
    TextButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(40.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = contentColor,
        ),
    ) {
        Text(
            text = text.asString().uppercase(),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = SpaceGroteskFontFamily,
                letterSpacing = 0.sp,
            ),
        )
    }
}
