package io.nicolaszurbuchen.pop_know.common.error

import androidx.compose.ui.graphics.vector.ImageVector
import io.nicolaszurbuchen.pop_know.infra.ui.UiText

data class AppErrorUiModel(
    val title: UiText,
    val subtitle: UiText,
    val icon: ImageVector,
)
