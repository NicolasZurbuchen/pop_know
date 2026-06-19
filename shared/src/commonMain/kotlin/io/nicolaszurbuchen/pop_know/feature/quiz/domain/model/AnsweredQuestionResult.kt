package io.nicolaszurbuchen.pop_know.feature.quiz.domain.model

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty

data class AnsweredQuestionResult(
    val question: String,
    val correctAnswer: String,
    val selectedAnswer: String?,
    val status: AnswerStatus,
    val categoryName: String?,
    val difficulty: Difficulty,
)
