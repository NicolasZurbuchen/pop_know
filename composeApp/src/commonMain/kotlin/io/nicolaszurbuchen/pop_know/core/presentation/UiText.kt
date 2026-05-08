package io.nicolaszurbuchen.pop_know.core.presentation

import androidx.compose.runtime.Composable
import io.nicolaszurbuchen.pop_know.core.presentation.UiText.Dynamic
import io.nicolaszurbuchen.pop_know.core.presentation.UiText.Resource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed class UiText {
    data class Dynamic(val value: String) : UiText()
    data class Resource(val resId: StringResource, val args: List<Any> = emptyList()) : UiText()
}

@Composable
fun UiText.asString(): String = when (this) {
    is Dynamic -> value
    is Resource -> stringResource(resId, *args.toTypedArray())
}