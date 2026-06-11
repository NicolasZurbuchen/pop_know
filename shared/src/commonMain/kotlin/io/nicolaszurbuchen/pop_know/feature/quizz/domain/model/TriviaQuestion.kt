package io.nicolaszurbuchen.pop_know.feature.quizz.domain.model

import io.nicolaszurbuchen.pop_know.core.domain.Category
import io.nicolaszurbuchen.pop_know.core.domain.Difficulty
import io.nicolaszurbuchen.pop_know.core.domain.QuestionType

data class TriviaQuestion(
    val questionType: QuestionType,
    val difficulty: Difficulty,
    val category: Category,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>
) {
}