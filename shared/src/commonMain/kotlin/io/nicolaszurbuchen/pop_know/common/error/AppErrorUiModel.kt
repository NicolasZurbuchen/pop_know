package io.nicolaszurbuchen.pop_know.common.error

import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import org.jetbrains.compose.resources.DrawableResource

data class AppErrorUiModel(
    val title: UiText,
    val subtitle: UiText,
    val imageRes: DrawableResource,
)