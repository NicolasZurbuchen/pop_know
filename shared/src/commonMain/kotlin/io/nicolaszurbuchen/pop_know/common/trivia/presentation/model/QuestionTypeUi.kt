package io.nicolaszurbuchen.pop_know.common.trivia.presentation.model

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType

enum class QuestionTypeUi {
    MULTIPLE,
    BOOLEAN,
}

fun QuestionType.toUi() = when (this) {
    QuestionType.MULTIPLE -> QuestionTypeUi.MULTIPLE
    QuestionType.BOOLEAN -> QuestionTypeUi.BOOLEAN
}
