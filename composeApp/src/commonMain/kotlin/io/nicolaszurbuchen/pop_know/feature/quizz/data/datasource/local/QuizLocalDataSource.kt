package io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.local

import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.TriviaQuestion

interface QuizLocalDataSource {
    fun saveQuestion(question: TriviaQuestion, selectedAnswer: String, answeredAt: Long)
}
