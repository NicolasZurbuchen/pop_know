package io.nicolaszurbuchen.pop_know.common.trivia.presentation.model

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty

enum class DifficultyUi {
    EASY,
    MEDIUM,
    HARD,
}

fun Difficulty.toUi() = when (this) {
    Difficulty.EASY -> DifficultyUi.EASY
    Difficulty.MEDIUM -> DifficultyUi.MEDIUM
    Difficulty.HARD -> DifficultyUi.HARD
}
