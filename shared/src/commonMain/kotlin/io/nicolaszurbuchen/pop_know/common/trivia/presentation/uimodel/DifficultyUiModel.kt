package io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty

enum class DifficultyUiModel {
    EASY,
    MEDIUM,
    HARD,
}

fun Difficulty.toUiModel() = when (this) {
    Difficulty.EASY -> DifficultyUiModel.EASY
    Difficulty.MEDIUM -> DifficultyUiModel.MEDIUM
    Difficulty.HARD -> DifficultyUiModel.HARD
}
