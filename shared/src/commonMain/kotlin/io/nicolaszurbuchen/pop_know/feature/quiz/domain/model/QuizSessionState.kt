package io.nicolaszurbuchen.pop_know.feature.quiz.domain.model

import io.nicolaszurbuchen.pop_know.common.domain.AnswerStats

data class QuizSessionState(
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
                accuracy = if (answered.isEmpty()) 0f else correct / answered.size.toFloat(),
            )
        }
}
