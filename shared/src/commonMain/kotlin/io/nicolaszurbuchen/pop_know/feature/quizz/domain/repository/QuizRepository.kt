package io.nicolaszurbuchen.pop_know.feature.quizz.domain.repository

import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.GameResult
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.TriviaQuestion

interface QuizRepository {
    suspend fun fetchQuestions(amount: Int): List<TriviaQuestion>
    suspend fun saveAnswer(gameId: Long, question: TriviaQuestion, selectedAnswer: String?, status: AnswerStatus)
    suspend fun getLastGameResult(): GameResult?
}
