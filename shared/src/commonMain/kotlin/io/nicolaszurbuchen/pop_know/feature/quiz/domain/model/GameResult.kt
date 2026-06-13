package io.nicolaszurbuchen.pop_know.feature.quiz.domain.model

import io.nicolaszurbuchen.pop_know.common.domain.AnswerStats

data class GameResult(
    val questions: List<AnsweredQuestionResult>,
) {
    val correctCount: Int = questions.count { it.status == AnswerStatus.CORRECT }
    val incorrectCount: Int = questions.count { it.status == AnswerStatus.INCORRECT }
    val timeoutCount: Int = questions.count { it.status == AnswerStatus.TIMEOUT }
    val score: AnswerStats = AnswerStats(
        totalAnswered = questions.size,
        totalCorrect = correctCount,
        accuracy = if (questions.isEmpty()) 0f else correctCount.toFloat() / questions.size,
    )
}
