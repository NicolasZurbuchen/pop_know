package io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase

import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuizSession

class AdvanceQuestionUseCase {
    operator fun invoke(session: QuizSession) {
        session.advance()
    }
}
