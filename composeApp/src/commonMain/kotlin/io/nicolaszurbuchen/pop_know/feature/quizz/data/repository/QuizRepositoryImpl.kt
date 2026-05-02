package io.nicolaszurbuchen.pop_know.feature.quizz.data.repository

import io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.local.QuizLocalDataSource
import io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.remote.QuizRemoteDataSource
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.QuestionProgress
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.QuizSession
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.TriviaQuestion
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.repository.QuizRepository
import kotlin.time.Clock

class QuizRepositoryImpl(
    private val remoteDataSource: QuizRemoteDataSource,
    private val localDataSource: QuizLocalDataSource,
) : QuizRepository {

    override suspend fun fetchQuestions(amount: Int): List<TriviaQuestion> =
        remoteDataSource.fetchQuestions(amount)

    override suspend fun saveSession(session: QuizSession) {
        val answeredAt = Clock.System.now().toEpochMilliseconds()
        val questions = session.questionStates
            .filterIsInstance<QuestionProgress.Answered>()
            .map { Pair(it.question, it.selectedAnswer ?: "") }
        localDataSource.saveQuestions(questions, answeredAt)
    }
}
