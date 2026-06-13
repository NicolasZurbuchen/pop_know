package io.nicolaszurbuchen.pop_know.infra.design.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.pop_know.common.presentation.UiText
import io.nicolaszurbuchen.pop_know.common.presentation.asString
import io.nicolaszurbuchen.pop_know.infra.design.theme.JetBrainsMonoFontFamily
import io.nicolaszurbuchen.pop_know.infra.design.theme.spacing

@Composable
fun PopKnowTopBar(
    modifier: Modifier = Modifier,
    left: UiText? = null,
    center: UiText? = null,
    right: UiText? = null,
    onBack: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(TopAppBarDefaults.MediumAppBarCollapsedHeight)
            .padding(
                horizontal = MaterialTheme.spacing.md,
                vertical = MaterialTheme.spacing.sm,
            ),
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart),
        ) {
            onBack?.let {
                IconButton(onClick = it) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
            left?.let { PopKnowTopBarText(it.asString()) }
        }
        center?.let {
            PopKnowTopBarText(
                text = it.asString(),
                modifier = Modifier
                    .align(Alignment.Center),
            )
        }
        right?.let {
            PopKnowTopBarText(
                text = it.asString(),
                modifier = Modifier
                    .align(Alignment.CenterEnd),
            )
        }
    }
}

@Composable
private fun PopKnowTopBarText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text.uppercase(),
        style = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = JetBrainsMonoFontFamily,
            letterSpacing = 1.5.sp,
            lineHeight = 16.sp,
        ),
        modifier = modifier,
    )
}