package io.nicolaszurbuchen.pop_know.feature.quiz.data.repository

import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local.QuizLocalDataSource
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local.mapper.toDomain
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local.mapper.toValue
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.QuizRemoteDataSource
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.mapper.toDomain
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.mapper.toEntity
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.GameResult
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.repository.QuizRepository
import io.nicolaszurbuchen.pop_know.infra.platform.decodeHtml

class QuizRepositoryImpl(
    private val remoteDataSource: QuizRemoteDataSource,
    private val localDataSource: QuizLocalDataSource,
    private val decodeHtml: (String) -> String = String::decodeHtml,
) : QuizRepository {
    override suspend fun fetchQuestions(amount: Int): List<TriviaQuestion> {
        val localCategories = localDataSource.getCategories()
        val categories =
            if (localCategories.isEmpty()) {
                val fetchedDto = remoteDataSource.fetchCategories()
                val entities = fetchedDto.map { it.toEntity(decodeHtml) }
                localDataSource.saveCategories(entities)
                entities.map { it.toDomain() }
            } else {
                localCategories.map { it.toDomain() }
            }

        return remoteDataSource.fetchQuestions(amount).map { it.toDomain(categories, decodeHtml) }
    }

    override suspend fun saveAnswer(
        gameId: Long,
        question: TriviaQuestion,
        selectedAnswer: String?,
        status: AnswerStatus,
    ) {
        localDataSource.saveAnswer(
            gameId = gameId,
            type = question.questionType.toValue(),
            difficulty = question.difficulty.toValue(),
            categoryId = question.category.id.toLong(),
            question = question.question,
            correctAnswer = question.correctAnswer,
            incorrectAnswers = question.incorrectAnswers,
            selectedAnswer = selectedAnswer,
            status = status.toValue(),
        )
    }

    override suspend fun getLastGameResult(): GameResult? {
        val rows = localDataSource.getLastGame()
        return if (rows.isEmpty()) null else GameResult(rows.map { it.toDomain() })
    }
}
