package io.nicolaszurbuchen.pop_know.feature.quiz.domain.model

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.AnswerStats
import kotlin.time.Clock

data class QuizSession(
    val questionStates: List<QuestionProgress>,
    val gameId: Long = Clock.System.now().toEpochMilliseconds(),
    val currentIndex: Int = 0,
) {
    val currentQuestion: QuestionProgress
        get() = questionStates[currentIndex]

    val score: AnswerStats
        get() {
            val answered = questionStates.filterIsInstance<QuestionProgress.Answered>()
            val correct = answered.count { it.status == AnswerStatus.CORRECT }
            return AnswerStats(
                totalAnswered = answered.size,
                totalCorrect = correct,
                accuracy = if (answered.isEmpty()) 0f else correct / answered.size.toFloat(),
            )
        }

    fun submitAnswer(answer: String?): QuizSession {
        val progress =
            questionStates.getOrNull(currentIndex) as? QuestionProgress.Unanswered
                ?: return this

        val status =
            when (answer) {
                null -> AnswerStatus.TIMEOUT
                progress.question.correctAnswer -> AnswerStatus.CORRECT
                else -> AnswerStatus.INCORRECT
            }

        val updatedStates =
            questionStates.toMutableList().also {
                it[currentIndex] =
                    QuestionProgress.Answered(
                        question = progress.question,
                        selectedAnswer = answer,
                        status = status,
                    )
            }
        return copy(questionStates = updatedStates)
    }

    fun advance(): QuizSession {
        val answered =
            questionStates.getOrNull(currentIndex) as? QuestionProgress.Answered
                ?: return this

        val updatedStates =
            questionStates.toMutableList().also {
                it[currentIndex] = answered.copy(advancedToNext = true)
            }
        return copy(
            questionStates = updatedStates,
            currentIndex = currentIndex + 1,
        )
    }
}
