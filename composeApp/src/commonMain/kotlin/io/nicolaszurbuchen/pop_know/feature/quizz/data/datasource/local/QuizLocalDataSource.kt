package io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.local

import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.TriviaQuestion

interface QuizLocalDataSource {
    fun saveQuestions(questions: List<Pair<TriviaQuestion, String>>, answeredAt: Long)
}
