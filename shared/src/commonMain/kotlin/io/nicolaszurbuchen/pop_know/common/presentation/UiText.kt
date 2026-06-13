package io.nicolaszurbuchen.pop_know.common.presentation

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed class UiText {
    data class Dynamic(val value: String) : UiText()
    data class Resource(val resId: StringResource, val args: List<Any> = emptyList()) : UiText()
}

@Composable
fun UiText.asString(): String = when (this) {
    is UiText.Dynamic -> value
    is UiText.Resource -> stringResource(resId, *args.toTypedArray())
}