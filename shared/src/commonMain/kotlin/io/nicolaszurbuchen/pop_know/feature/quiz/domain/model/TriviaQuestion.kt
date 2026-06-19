package io.nicolaszurbuchen.pop_know.feature.quiz.domain.model

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType

data class TriviaQuestion(
    val questionType: QuestionType,
    val difficulty: Difficulty,
    val category: Category,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
)
