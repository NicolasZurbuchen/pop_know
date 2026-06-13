package io.nicolaszurbuchen.pop_know.feature.quiz.domain.repository

import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.GameResult
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion

interface QuizRepository {
    suspend fun fetchQuestions(amount: Int): List<TriviaQuestion>
    suspend fun saveAnswer(gameId: Long, question: TriviaQuestion, selectedAnswer: String?, status: AnswerStatus)
    suspend fun getLastGameResult(): GameResult?
}
