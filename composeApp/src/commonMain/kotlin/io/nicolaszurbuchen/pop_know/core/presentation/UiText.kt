package io.nicolaszurbuchen.pop_know.core.presentation

sealed class UiText {
    data class Dynamic(val value: String) : UiText()
    data class Resource(val resId: Int) : UiText()
}