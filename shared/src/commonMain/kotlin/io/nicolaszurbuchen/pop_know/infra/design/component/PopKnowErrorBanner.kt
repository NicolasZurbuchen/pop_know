package io.nicolaszurbuchen.pop_know.infra.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.pop_know.infra.design.theme.SpaceGroteskFontFamily
import io.nicolaszurbuchen.pop_know.infra.design.theme.spacing
import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import popknow.shared.generated.resources.Res
import popknow.shared.generated.resources.error_dismiss
import popknow.shared.generated.resources.error_retry

@Composable
fun PopKnowErrorBanner(
    text: String,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(MaterialTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ErrorOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onError,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = SpaceGroteskFontFamily,
                    fontSize = 14.sp,
                ),
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            onRetry?.let {
                PopKnowSecondaryButton(
                    text = UiText.Resource(Res.string.error_retry),
                    onClick = it,
                    isFullWidth = false,
                    showArrow = false,
                    modifier = Modifier.height(40.dp)
                )
            }

            onDismiss?.let {
                PopKnowSecondaryButton(
                    text = UiText.Resource(Res.string.error_dismiss),
                    onClick = it,
                    isFullWidth = false,
                    showArrow = false,
                    modifier = Modifier
                        .padding(start = MaterialTheme.spacing.sm)
                        .height(40.dp)
                )
            }
        }
    }
}
