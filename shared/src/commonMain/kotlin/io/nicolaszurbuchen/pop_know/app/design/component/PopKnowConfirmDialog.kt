package io.nicolaszurbuchen.pop_know.app.design.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.nicolaszurbuchen.pop_know.app.design.theme.SpaceGroteskFontFamily

@Composable
fun PopKnowConfirmDialog(
    title: String,
    text: String,
    confirmText: String,
    dismissText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style =
                    MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = SpaceGroteskFontFamily,
                    ),
            )
        },
        text = {
            Text(
                text = text,
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = SpaceGroteskFontFamily,
                    ),
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = confirmText.uppercase(),
                    style =
                        MaterialTheme.typography.labelLarge.copy(
                            fontFamily = SpaceGroteskFontFamily,
                            color = MaterialTheme.colorScheme.primary,
                        ),
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = dismissText.uppercase(),
                    style =
                        MaterialTheme.typography.labelLarge.copy(
                            fontFamily = SpaceGroteskFontFamily,
                            color = MaterialTheme.colorScheme.secondary,
                        ),
                )
            }
        },
        shape = MaterialTheme.shapes.large,
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier,
    )
}
