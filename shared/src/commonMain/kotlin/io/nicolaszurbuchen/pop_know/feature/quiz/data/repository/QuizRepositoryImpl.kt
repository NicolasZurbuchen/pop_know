package io.nicolaszurbuchen.pop_know.feature.quiz.data.repository

import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local.QuizLocalDataSource
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.QuizRemoteDataSource
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.GameResult
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.repository.QuizRepository

class QuizRepositoryImpl(
    private val remoteDataSource: QuizRemoteDataSource,
    private val localDataSource: QuizLocalDataSource,
) : QuizRepository {

    override suspend fun fetchQuestions(amount: Int): List<TriviaQuestion> {
        val categories = localDataSource.getCategories().ifEmpty {
            val fetched = remoteDataSource.fetchCategories()
            localDataSource.saveCategories(fetched)
            fetched
        }
        return remoteDataSource.fetchQuestions(categories, amount)
    }

    override suspend fun saveAnswer(
        gameId: Long,
        question: TriviaQuestion,
        selectedAnswer: String?,
        status: AnswerStatus,
    ) {
        localDataSource.saveAnswer(gameId, question, selectedAnswer, status)
    }

    override suspend fun getLastGameResult(): GameResult? {
        val rows = localDataSource.getLastGame()
        return if (rows.isEmpty()) null else GameResult(rows)
    }
}
