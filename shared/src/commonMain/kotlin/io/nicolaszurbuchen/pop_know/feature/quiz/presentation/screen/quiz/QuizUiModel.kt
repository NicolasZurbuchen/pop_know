package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import io.nicolaszurbuchen.pop_know.app.design.theme.popKnowGameColors
import io.nicolaszurbuchen.pop_know.common.error.AppErrorUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.DifficultyUiModel
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.AnswerStatusUiModel.CORRECT
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.AnswerStatusUiModel.INCORRECT
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.AnswerStatusUiModel.TIMEOUT
import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import popknow.shared.generated.resources.Res
import popknow.shared.generated.resources.quiz_headline_nice
import popknow.shared.generated.resources.quiz_headline_nope
import popknow.shared.generated.resources.quiz_headline_slow
import popknow.shared.generated.resources.quiz_quiz_label_pointsLost
import popknow.shared.generated.resources.quiz_quiz_label_pointsWon
import popknow.shared.generated.resources.quiz_quiz_label_timedOut

data class QuizUiModel(
    val isLoading: Boolean,
    val initialError: AppErrorUiModel?,
    val insertionError: AppErrorUiModel?,
    val quizData: QuizDataUiModel?,
    val isQuitDialogOpen: Boolean,
)

data class QuizDataUiModel(
    val questionText: String,
    val categoryText: String,
    val difficulty: DifficultyUiModel,
    val progressText: String,
    val scoreText: String,
    val choices: List<QuizChoiceUiModel>,
    val resultChoice: QuizChoiceUiModel?,
    val totalQuestions: Int,
    val currentIndex: Int,
    val isAnswered: Boolean,
    val isLastQuestion: Boolean,
    val timerSeconds: Int,
    val maxTimerSeconds: Int,
)

data class QuizChoiceUiModel(
    val letter: String,
    val text: String,
    val status: AnswerStatusUiModel?,
)

enum class AnswerStatusUiModel {
    CORRECT,
    INCORRECT,
    TIMEOUT,
}

fun AnswerStatus.toUiModel() = when (this) {
    AnswerStatus.CORRECT -> CORRECT
    AnswerStatus.INCORRECT -> INCORRECT
    AnswerStatus.TIMEOUT -> TIMEOUT
}

fun AnswerStatusUiModel.toLabel(): UiText = when (this) {
    CORRECT -> UiText.Resource(Res.string.quiz_quiz_label_pointsWon)
    INCORRECT -> UiText.Resource(Res.string.quiz_quiz_label_pointsLost)
    TIMEOUT -> UiText.Resource(Res.string.quiz_quiz_label_timedOut)
}

fun AnswerStatusUiModel.toHeadline(): UiText = when (this) {
    CORRECT -> UiText.Resource(Res.string.quiz_headline_nice)
    INCORRECT -> UiText.Resource(Res.string.quiz_headline_nope)
    TIMEOUT -> UiText.Resource(Res.string.quiz_headline_slow)
}

@Composable
@ReadOnlyComposable
fun AnswerStatusUiModel.backgroundColor(): Color = when (this) {
    CORRECT -> MaterialTheme.popKnowGameColors.correct
    INCORRECT -> MaterialTheme.popKnowGameColors.wrong
    TIMEOUT -> MaterialTheme.popKnowGameColors.timeout
}

@Composable
@ReadOnlyComposable
fun AnswerStatusUiModel.contentColor(): Color = when (this) {
    CORRECT -> MaterialTheme.popKnowGameColors.onCorrect
    INCORRECT -> MaterialTheme.popKnowGameColors.onWrong
    TIMEOUT -> MaterialTheme.popKnowGameColors.onTimeout
}

@Composable
@ReadOnlyComposable
fun QuizChoiceUiModel.backgroundColor(): Color =
    status?.backgroundColor() ?: MaterialTheme.colorScheme.background

@Composable
@ReadOnlyComposable
fun QuizChoiceUiModel.contentColor(): Color =
    status?.contentColor() ?: MaterialTheme.colorScheme.onBackground
