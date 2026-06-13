package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.mapper

import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuestionProgress
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuizSessionState
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.QuizChoiceUiModel
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.QuizUiModel

object QuizUiMapper {
    fun map(
        state: QuizSessionState,
        timerSeconds: Int,
        shuffledAnswers: List<List<String>>,
    ): QuizUiModel {
        val progress = state.currentQuestion
        val question = when (progress) {
            is QuestionProgress.Unanswered -> progress.question
            is QuestionProgress.Answered -> progress.question
        }
        val answered = progress as? QuestionProgress.Answered
        val currentShuffled = shuffledAnswers.getOrElse(state.currentIndex) {
            (question.incorrectAnswers + question.correctAnswer).shuffled()
        }

        val choices = currentShuffled.mapIndexed { index, text ->
            val status: AnswerStatus? = when {
                answered == null -> null
                answered.selectedAnswer == text && text == question.correctAnswer -> AnswerStatus.CORRECT
                answered.selectedAnswer == text -> AnswerStatus.INCORRECT
                answered.selectedAnswer == null && text == question.correctAnswer -> null
                else -> null
            }
            QuizChoiceUiModel(
                letter = ('A' + index).toString(),
                text = text,
                answerStatus = status,
            )
        }

        val resultChoice: QuizChoiceUiModel? = when {
            answered == null -> null
            answered.status == AnswerStatus.TIMEOUT -> QuizChoiceUiModel(
                letter = "",
                text = "",
                answerStatus = AnswerStatus.TIMEOUT,
            )
            else -> choices.firstOrNull { it.answerStatus != null }
        }

        val score = state.score
        val total = state.questionStates.size

        return QuizUiModel(
            questionText = question.question,
            categoryText = question.category.category.uppercase(),
            difficulty = question.difficulty,
            progressText = "${(state.currentIndex + 1).twoDigits()}/${total.twoDigits()}",
            scoreText = "${score.totalCorrect}/${score.totalAnswered}",
            choices = choices,
            resultChoice = resultChoice,
            totalQuestions = total,
            currentIndex = state.currentIndex,
            isAnswered = answered != null,
            isLastQuestion = state.currentIndex == total - 1,
            timerSeconds = timerSeconds,
            maxTimerSeconds = 15,
        )
    }

    private fun Int.twoDigits() = if (this < 10) "0$this" else "$this"
}
