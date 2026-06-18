package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import io.nicolaszurbuchen.pop_know.common.error.toUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.toUiModel
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuestionProgress
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuizSession

fun QuizState.toUiModel(): QuizUiModel {
    return QuizUiModel(
        isLoading = isLoading,
        initialError = initialError?.toUiModel(),
        insertionError = insertionError?.toUiModel(),
        quizData = session?.toDataUiModel(timerSeconds, shuffledAnswers),
        isQuitDialogOpen = isQuitDialogOpen
    )
}

private fun QuizSession.toDataUiModel(
    timerSeconds: Int,
    shuffledAnswers: List<List<String>>
): QuizDataUiModel {
    val progress = currentQuestion
    val question = when (progress) {
        is QuestionProgress.Unanswered -> progress.question
        is QuestionProgress.Answered -> progress.question
    }
    val answered = progress as? QuestionProgress.Answered
    val currentShuffled = shuffledAnswers.getOrElse(currentIndex) {
        (question.incorrectAnswers + question.correctAnswer).shuffled()
    }

    val choices = currentShuffled.mapIndexed { index, text ->
        val status: QuizAnswerStatusUi? = when {
            answered == null -> null
            answered.selectedAnswer == text && text == question.correctAnswer -> QuizAnswerStatusUi.CORRECT
            answered.selectedAnswer == text -> QuizAnswerStatusUi.INCORRECT
            text == question.correctAnswer -> QuizAnswerStatusUi.CORRECT
            else -> null
        }
        QuizChoiceUiModel(
            letter = ('A' + index).toString(),
            text = text,
            status = status,
        )
    }

    val resultChoice: QuizChoiceUiModel? = when {
        answered == null -> null
        answered.status == AnswerStatus.TIMEOUT -> QuizChoiceUiModel(
            letter = "",
            text = "",
            status = QuizAnswerStatusUi.TIMEOUT,
        )
        else -> choices.firstOrNull { it.text == answered.selectedAnswer }
    }

    val scoreValue = score
    val total = questionStates.size

    return QuizDataUiModel(
        questionText = question.question,
        categoryText = question.category.category.uppercase(),
        difficulty = question.difficulty.toUiModel(),
        progressText = "${(currentIndex + 1).twoDigits()}/${total.twoDigits()}",
        scoreText = "${scoreValue.totalCorrect}/${scoreValue.totalAnswered}",
        choices = choices,
        resultChoice = resultChoice,
        totalQuestions = total,
        currentIndex = currentIndex,
        isAnswered = answered != null,
        isLastQuestion = currentIndex == total - 1,
        timerSeconds = timerSeconds,
        maxTimerSeconds = 15,
    )
}

private fun Int.twoDigits() = if (this < 10) "0$this" else "$this"
