package io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.remote

import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.TriviaQuestion

interface QuizRemoteDataSource {
    suspend fun fetchQuestions(amount: Int): List<TriviaQuestion>
}
