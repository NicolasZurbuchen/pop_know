package io.nicolaszurbuchen.pop_know.feature.quiz.domain.model

data class AnsweredQuestionResult(
    val question: String,
    val correctAnswer: String,
    val selectedAnswer: String?,
    val status: AnswerStatus,
    val categoryName: String,
    val difficulty: io.nicolaszurbuchen.pop_know.common.domain.Difficulty,
)
