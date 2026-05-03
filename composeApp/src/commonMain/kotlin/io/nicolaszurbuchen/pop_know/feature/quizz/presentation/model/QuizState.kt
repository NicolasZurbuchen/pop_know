package io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model

import io.nicolaszurbuchen.pop_know.core.presentation.UiText
import io.nicolaszurbuchen.pop_know.core.presentation.mvi.UiState
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.QuestionProgress

data class QuizState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val content: QuizContent? = null,
) : UiState {
    val isAnswered: Boolean
        get() = content?.session?.currentQuestion is QuestionProgress.Answered

    val isLastQuestion: Boolean
        get() = content?.let {
            it.session.currentIndex == it.session.questionStates.size - 1
        } ?: false
}
