package io.nicolaszurbuchen.pop_know.feature.quizz.domain.model

import io.nicolaszurbuchen.pop_know.core.domain.AnswerStats

data class QuizSession(
    val questionStates: List<QuestionProgress>,
    val currentIndex: Int = 0,
) {
    val isComplete: Boolean
        get() = currentIndex >= questionStates.size

    val currentQuestion: QuestionProgress
        get() = questionStates[currentIndex]

    val score: AnswerStats
        get() {
            val answered = questionStates.filterIsInstance<QuestionProgress.Answered>()
            val correct = answered.count { it.status == AnswerStatus.CORRECT }
            return AnswerStats(
                totalAnswered = answered.size,
                totalCorrect = correct,
                accuracy = if (answered.isEmpty()) 0f
                else correct / answered.size.toFloat()
            )
        }

    fun answer(selectedAnswer: String?, status: AnswerStatus): QuizSession =
        copy(
            questionStates = questionStates.toMutableList().also {
                it[currentIndex] = QuestionProgress.Answered(
                    question = (questionStates[currentIndex] as QuestionProgress.Unanswered).question,
                    selectedAnswer = selectedAnswer,
                    status = status
                )
            }
        )

    fun timeout(): QuizSession =
        answer(selectedAnswer = null, status = AnswerStatus.TIMEOUT)

    fun advance(): QuizSession {
        val updated = questionStates.toMutableList().also {
            val current = it[currentIndex] as QuestionProgress.Answered
            it[currentIndex] = current.copy(advancedToNext = true)
        }
        return copy(
            questionStates = updated,
            currentIndex = currentIndex + 1
        )
    }
}