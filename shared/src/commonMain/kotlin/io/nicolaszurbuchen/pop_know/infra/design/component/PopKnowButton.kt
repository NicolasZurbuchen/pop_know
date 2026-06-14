package io.nicolaszurbuchen.pop_know.infra.design.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.pop_know.infra.design.theme.SpaceGroteskFontFamily
import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import io.nicolaszurbuchen.pop_know.infra.ui.asString

enum class PopKnowButtonVariant { Primary, Secondary }

@Composable
fun PopKnowButton(
    text: UiText,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: PopKnowButtonVariant = PopKnowButtonVariant.Primary,
    showArrow: Boolean = true,
    enabled: Boolean = true,
    isFullWidth: Boolean = true
) {
    val buttonModifier = if (isFullWidth) modifier.fillMaxWidth() else modifier
    
    when (variant) {
        PopKnowButtonVariant.Primary -> Button(
            onClick = onClick,
            enabled = enabled,
            modifier = buttonModifier
                .height(56.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Row(
                modifier = if (isFullWidth) Modifier.fillMaxWidth() else Modifier,
                horizontalArrangement = if (isFullWidth) Arrangement.SpaceBetween else Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text.asString().uppercase(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = SpaceGroteskFontFamily,
                        letterSpacing = 0.sp,
                        lineHeight = 27.sp,
                    ),
                )
                if (showArrow) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }

        PopKnowButtonVariant.Secondary -> OutlinedButton(
            onClick = onClick,
            enabled = enabled,
            modifier = buttonModifier
                .height(56.dp),
            shape = MaterialTheme.shapes.extraLarge,
            border = BorderStroke(
                width = 1.5.dp,
                color = MaterialTheme.colorScheme.primary
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Row(
                horizontalArrangement = if (isFullWidth) Arrangement.SpaceBetween else Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = if (isFullWidth) Modifier.fillMaxWidth() else Modifier,
            ) {
                Text(
                    text = text.asString().uppercase(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = SpaceGroteskFontFamily,
                        letterSpacing = 0.sp,
                        lineHeight = 27.sp,
                    ),
                )
                if (showArrow) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }
    }
}