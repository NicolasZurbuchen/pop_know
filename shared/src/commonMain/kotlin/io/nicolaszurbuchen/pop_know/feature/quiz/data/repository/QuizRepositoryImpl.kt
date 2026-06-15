package io.nicolaszurbuchen.pop_know.feature.quiz.data.repository

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.mapper.CategoryMapper
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.remote.mapper.TriviaQuestionMapper
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local.QuizLocalDataSource
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.QuizRemoteDataSource
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.GameResult
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.repository.QuizRepository

class QuizRepositoryImpl(
    private val remoteDataSource: QuizRemoteDataSource,
    private val localDataSource: QuizLocalDataSource,
    private val categoryMapper: CategoryMapper,
    private val triviaQuestionMapper: TriviaQuestionMapper,
) : QuizRepository {

    override suspend fun fetchQuestions(amount: Int): List<TriviaQuestion> {
        val categories = localDataSource.getCategories().ifEmpty {
            val fetched = remoteDataSource.fetchCategories().map { categoryMapper.toDomain(it) }
            localDataSource.saveCategories(fetched)
            fetched
        }
        return remoteDataSource.fetchQuestions(categories, amount).map { triviaQuestionMapper.toDomain(it, categories) }
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
