package io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType

enum class QuestionTypeUiModel {
    MULTIPLE,
    BOOLEAN,
}

fun QuestionType.toUiModel() =
    when (this) {
        QuestionType.MULTIPLE -> QuestionTypeUiModel.MULTIPLE
        QuestionType.BOOLEAN -> QuestionTypeUiModel.BOOLEAN
    }
