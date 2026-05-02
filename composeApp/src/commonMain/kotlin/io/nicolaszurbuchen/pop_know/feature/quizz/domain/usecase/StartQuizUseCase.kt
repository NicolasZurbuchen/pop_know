package io.nicolaszurbuchen.pop_know.feature.quizz.domain.usecase

import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.QuestionProgress
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.QuizSession
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.repository.QuizRepository

class StartQuizUseCase(private val repository: QuizRepository) {
    suspend operator fun invoke(): QuizSession {
        val questions = repository.fetchQuestions(amount = 10)
        return QuizSession(questionStates = questions.map { QuestionProgress.Unanswered(it) })
    }
}
