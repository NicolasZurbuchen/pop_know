package io.nicolaszurbuchen.pop_know.feature.quiz.data.repository

import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local.QuizLocalDataSource
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local.mapper.QuizLocalMapper
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.QuizRemoteDataSource
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.mapper.QuizRemoteMapper
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.GameResult
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.repository.QuizRepository

class QuizRepositoryImpl(
    private val remoteDataSource: QuizRemoteDataSource,
    private val localDataSource: QuizLocalDataSource,
    private val remoteMapper: QuizRemoteMapper,
    private val localMapper: QuizLocalMapper,
) : QuizRepository {

    override suspend fun fetchQuestions(amount: Int): List<TriviaQuestion> {
        val localCategories = localDataSource.getCategories()
        val categories = if (localCategories.isEmpty()) {
            val fetchedDto = remoteDataSource.fetchCategories()
            val entities = fetchedDto.map { remoteMapper.mapDtoToEntity(it) }
            localDataSource.saveCategories(entities)
            entities.map { localMapper.mapEntityToDomain(it) }
        } else {
            localCategories.map { localMapper.mapEntityToDomain(it) }
        }
        
        return remoteDataSource.fetchQuestions(amount).map { 
            remoteMapper.mapDtoToDomain(it, categories)
        }
    }

    override suspend fun saveAnswer(
        gameId: Long,
        question: TriviaQuestion,
        selectedAnswer: String?,
        status: AnswerStatus,
    ) {
        localDataSource.saveAnswer(
            gameId = gameId,
            type = localMapper.mapToDbString(question.questionType),
            difficulty = localMapper.mapToDbString(question.difficulty),
            categoryId = question.category.id.toLong(),
            question = question.question,
            correctAnswer = question.correctAnswer,
            incorrectAnswers = question.incorrectAnswers,
            selectedAnswer = selectedAnswer,
            status = localMapper.mapToDbString(status),
        )
    }

    override suspend fun getLastGameResult(): GameResult? {
        val rows = localDataSource.getLastGame()
        return if (rows.isEmpty()) null else GameResult(rows.map { localMapper.mapEntityToDomain(it) })
    }
}
