package io.nicolaszurbuchen.pop_know.core.domain

data class TriviaQuestion(
    val questionType: QuestionType,
    val difficulty: Difficulty,
    val category: Category,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>
) {
}