package io.nicolaszurbuchen.pop_know.feature.quiz.domain.fake

import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.GameResult
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.repository.QuizRepository

class FakeQuizRepository : QuizRepository {

    // fetchQuestions
    var fetchQuestionsResult: List<TriviaQuestion> = emptyList()
    var fetchQuestionsError: Throwable? = null
    var lastRequestedAmount: Int? = null
        private set

    // saveAnswer
    var saveAnswerError: Throwable? = null
    val savedAnswers = mutableListOf<SavedAnswer>()

    // getLastGameResult
    var lastGameResult: GameResult? = null

    override suspend fun fetchQuestions(amount: Int): List<TriviaQuestion> {
        lastRequestedAmount = amount
        fetchQuestionsError?.let { throw it }
        return fetchQuestionsResult
    }

    override suspend fun saveAnswer(
        gameId: Long,
        question: TriviaQuestion,
        selectedAnswer: String?,
        status: AnswerStatus,
    ) {
        saveAnswerError?.let { throw it }
        savedAnswers += SavedAnswer(gameId, question, selectedAnswer, status)
    }

    override suspend fun getLastGameResult(): GameResult? = lastGameResult

    data class SavedAnswer(
        val gameId: Long,
        val question: TriviaQuestion,
        val selectedAnswer: String?,
        val status: AnswerStatus,
    )
}