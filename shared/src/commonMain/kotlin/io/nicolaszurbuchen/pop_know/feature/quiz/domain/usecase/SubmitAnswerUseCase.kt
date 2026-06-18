package io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase

import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.error.AppException
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuestionProgress
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuizSession
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.repository.QuizRepository

class SubmitAnswerUseCase(
    private val repository: QuizRepository,
) {
    suspend operator fun invoke(session: QuizSession, answer: String?): QuizSession {
        val nextSession = session.submitAnswer(answer)
        val answered = nextSession.currentQuestion as? QuestionProgress.Answered ?: return nextSession

        try {
            repository.saveAnswer(
                gameId = nextSession.gameId,
                question = answered.question,
                selectedAnswer = answered.selectedAnswer,
                status = answered.status,
            )
        } catch (e: Exception) {
            throw AppException(AppError.Database.InsertFailed(e))
        }

        return nextSession
    }
}
