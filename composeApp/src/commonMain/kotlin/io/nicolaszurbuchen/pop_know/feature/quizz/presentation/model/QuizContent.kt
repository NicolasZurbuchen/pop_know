package io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model

import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.QuizSession

data class QuizContent(
    val session: QuizSession,
    val timerSeconds: Int,
    val shuffledAnswers: List<String>,
)
