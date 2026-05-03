package io.nicolaszurbuchen.pop_know.feature.quizz.domain.model

import io.nicolaszurbuchen.pop_know.core.domain.Difficulty

data class AnsweredQuestionResult(
    val question: String,
    val correctAnswer: String,
    val selectedAnswer: String?,
    val status: AnswerStatus,
    val categoryName: String,
    val difficulty: Difficulty,
)
