package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result.model

import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.AnswerStatsUi
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.GameResult

data class GameResultUi(
    val questions: List<AnsweredQuestionResultUi>,
    val correctCount: Int,
    val incorrectCount: Int,
    val timeoutCount: Int,
    val score: AnswerStatsUi,
)

fun GameResult.toUi() = GameResultUi(
    questions = questions.map { it.toUi() },
    correctCount = correctCount,
    incorrectCount = incorrectCount,
    timeoutCount = timeoutCount,
    score = AnswerStatsUi(
        totalAnswered = score.totalAnswered,
        totalCorrect = score.totalCorrect,
        accuracy = score.accuracy,
    )
)
