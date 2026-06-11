package io.nicolaszurbuchen.pop_know.feature.quizz.domain.usecase

import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.QuizSession

class AdvanceQuestionUseCase {
    operator fun invoke(session: QuizSession) {
        session.advance()
    }
}
