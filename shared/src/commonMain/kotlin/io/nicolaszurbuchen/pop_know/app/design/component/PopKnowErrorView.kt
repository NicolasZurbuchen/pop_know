package io.nicolaszurbuchen.pop_know.app.design.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.nicolaszurbuchen.pop_know.app.design.theme.spacing
import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import io.nicolaszurbuchen.pop_know.infra.ui.asString
import popknow.shared.generated.resources.Res
import popknow.shared.generated.resources.error_retry
import popknow.shared.generated.resources.error_unexpected_subtitle
import popknow.shared.generated.resources.error_unexpected_title

@Composable
fun PopKnowErrorView(
    modifier: Modifier = Modifier,
    title: UiText = UiText.Resource(Res.string.error_unexpected_title),
    subtitle: UiText = UiText.Resource(Res.string.error_unexpected_subtitle),
    icon: ImageVector = Icons.Default.ErrorOutline,
    onRetry: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.xl),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.error,
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

        Text(
            text = title.asString(),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))

        Text(
            text = subtitle.asString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.xl))

        PopKnowPrimaryButton(
            text = UiText.Resource(Res.string.error_retry),
            onClick = onRetry,
            isFullWidth = false,
        )
    }
}
