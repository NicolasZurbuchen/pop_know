package io.nicolaszurbuchen.pop_know.feature.quizz.domain.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuizSession(
    val gameId: Long,
    questions: List<TriviaQuestion>,
) {
    private val _state = MutableStateFlow(
        QuizSessionState(questionStates = questions.map { QuestionProgress.Unanswered(it) })
    )
    val state: StateFlow<QuizSessionState> = _state.asStateFlow()

    fun submitAnswer(answer: String) {
        _state.update { current ->
            val progress = current.questionStates[current.currentIndex] as? QuestionProgress.Unanswered
                ?: return
            val status = if (answer == progress.question.correctAnswer) AnswerStatus.CORRECT else AnswerStatus.INCORRECT
            current.copy(
                questionStates = current.questionStates.toMutableList().also {
                    it[current.currentIndex] = QuestionProgress.Answered(
                        question = progress.question,
                        selectedAnswer = answer,
                        status = status,
                    )
                }
            )
        }
    }

    fun timeout() {
        _state.update { current ->
            val progress = current.questionStates[current.currentIndex] as? QuestionProgress.Unanswered
                ?: return
            current.copy(
                questionStates = current.questionStates.toMutableList().also {
                    it[current.currentIndex] = QuestionProgress.Answered(
                        question = progress.question,
                        selectedAnswer = null,
                        status = AnswerStatus.TIMEOUT,
                    )
                }
            )
        }
    }

    fun advance() {
        _state.update { current ->
            val answered = current.questionStates[current.currentIndex] as? QuestionProgress.Answered
                ?: return
            val updated = current.questionStates.toMutableList().also {
                it[current.currentIndex] = answered.copy(advancedToNext = true)
            }
            current.copy(questionStates = updated, currentIndex = current.currentIndex + 1)
        }
    }
}
