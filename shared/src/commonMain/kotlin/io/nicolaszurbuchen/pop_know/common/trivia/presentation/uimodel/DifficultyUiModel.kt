package io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import io.nicolaszurbuchen.pop_know.app.design.theme.popKnowGameColors
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.DifficultyUiModel.EASY
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.DifficultyUiModel.HARD
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.DifficultyUiModel.MEDIUM
import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import popknow.shared.generated.resources.Res
import popknow.shared.generated.resources.common_difficulty_easy
import popknow.shared.generated.resources.common_difficulty_hard
import popknow.shared.generated.resources.common_difficulty_medium

enum class DifficultyUiModel {
    EASY,
    MEDIUM,
    HARD,
}

fun Difficulty.toUiModel() = when (this) {
    Difficulty.EASY -> EASY
    Difficulty.MEDIUM -> MEDIUM
    Difficulty.HARD -> HARD
}

fun DifficultyUiModel.toText(): UiText = when (this) {
    EASY -> UiText.Resource(Res.string.common_difficulty_easy)
    MEDIUM -> UiText.Resource(Res.string.common_difficulty_medium)
    HARD -> UiText.Resource(Res.string.common_difficulty_hard)
}

@Composable
@ReadOnlyComposable
fun DifficultyUiModel.color(): Color = when (this) {
    EASY -> MaterialTheme.popKnowGameColors.difficultyEasy
    MEDIUM -> MaterialTheme.popKnowGameColors.difficultyMedium
    HARD -> MaterialTheme.popKnowGameColors.difficultyHard
}
