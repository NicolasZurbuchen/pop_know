package io.nicolaszurbuchen.pop_know.feature.quizz.domain.model

import io.nicolaszurbuchen.pop_know.core.domain.TriviaQuestion

sealed class QuestionProgress {
    data class Unanswered(
        val question: TriviaQuestion
    ) : QuestionProgress()

    data class Answered(
        val question: TriviaQuestion,
        val selectedAnswer: String?,
        val status: AnswerStatus,
        val advancedToNext: Boolean = false
    ) : QuestionProgress()
}