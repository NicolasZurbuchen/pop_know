package io.nicolaszurbuchen.pop_know.feature.quizz.domain.usecase

import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.QuestionProgress
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.QuizSession
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.repository.QuizRepository

class SubmitAnswerUseCase(
    private val repository: QuizRepository,
) {
    suspend operator fun invoke(session: QuizSession, answer: String?) {
        if (answer != null) session.submitAnswer(answer) else session.timeout()

        val allAnswered = session.state.value.questionStates.all { it is QuestionProgress.Answered }
        if (allAnswered) {
            session.state.value.questionStates
                .filterIsInstance<QuestionProgress.Answered>()
                .forEach { progress ->
                    repository.saveAnswer(
                        gameId = session.gameId,
                        question = progress.question,
                        selectedAnswer = progress.selectedAnswer,
                        status = progress.status,
                    )
                }
        }
    }
}
