package io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase

import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuestionProgress
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuizSession
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.repository.QuizRepository
import kotlin.time.Clock

class StartQuizUseCase(
    private val repository: QuizRepository,
) {
    suspend operator fun invoke(): QuizSession {
        val questions = repository.fetchQuestions(amount = 10)
        return QuizSession(
            gameId = Clock.System.now().toEpochMilliseconds(),
            questionStates = questions.map { QuestionProgress.Unanswered(it) },
        )
    }
}
