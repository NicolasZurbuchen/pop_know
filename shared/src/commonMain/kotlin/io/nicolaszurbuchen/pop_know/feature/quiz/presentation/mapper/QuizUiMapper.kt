package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.mapper

import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.toUi
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuestionProgress
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuizSessionState
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.QuizChoiceUi
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.QuizUi

object QuizUiMapper {
    fun map(
        state: QuizSessionState,
        timerSeconds: Int,
        shuffledAnswers: List<List<String>>,
    ): QuizUi {
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
                text == question.correctAnswer -> AnswerStatus.CORRECT
                else -> null
            }
            QuizChoiceUi(
                letter = ('A' + index).toString(),
                text = text,
                answerStatus = status,
            )
        }

        val resultChoice: QuizChoiceUi? = when {
            answered == null -> null
            answered.status == AnswerStatus.TIMEOUT -> QuizChoiceUi(
                letter = "",
                text = "",
                answerStatus = AnswerStatus.TIMEOUT,
            )
            else -> choices.firstOrNull { it.text == answered.selectedAnswer }
        }

        val score = state.score
        val total = state.questionStates.size

        return QuizUi(
            questionText = question.question,
            categoryText = question.category.category.uppercase(),
            difficulty = question.difficulty.toUi(),
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
