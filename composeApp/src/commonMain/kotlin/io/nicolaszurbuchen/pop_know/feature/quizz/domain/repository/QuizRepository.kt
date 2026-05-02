package io.nicolaszurbuchen.pop_know.feature.quizz.domain.repository

import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.QuizSession
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.TriviaQuestion

interface QuizRepository {
    suspend fun fetchQuestions(amount: Int): List<TriviaQuestion>
    suspend fun saveSession(session: QuizSession)
}
