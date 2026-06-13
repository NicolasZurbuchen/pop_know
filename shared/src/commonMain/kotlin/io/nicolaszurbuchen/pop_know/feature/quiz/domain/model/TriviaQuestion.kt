package io.nicolaszurbuchen.pop_know.feature.quiz.domain.model

data class TriviaQuestion(
    val questionType: io.nicolaszurbuchen.pop_know.common.domain.QuestionType,
    val difficulty: io.nicolaszurbuchen.pop_know.common.domain.Difficulty,
    val category: io.nicolaszurbuchen.pop_know.common.domain.Category,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>
) {
}