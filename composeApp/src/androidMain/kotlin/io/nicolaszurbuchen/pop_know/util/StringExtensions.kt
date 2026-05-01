package io.nicolaszurbuchen.pop_know.util

import android.text.Html

actual fun String.decodeHtml(): String {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
}