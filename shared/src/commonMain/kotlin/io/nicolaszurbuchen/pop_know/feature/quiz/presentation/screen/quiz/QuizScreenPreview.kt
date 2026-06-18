package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.nicolaszurbuchen.pop_know.app.design.theme.PopKnowTheme
import io.nicolaszurbuchen.pop_know.common.error.AppErrorUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.DifficultyUiModel
import io.nicolaszurbuchen.pop_know.infra.ui.UiText

class QuizUiModelProvider : PreviewParameterProvider<QuizUiModel> {
    override val values = sequenceOf(
        QuizUiModel(
            isLoading = true,
            initialError = null,
            insertionError = null,
            quizData = null,
            isQuitDialogOpen = false
        ),
        QuizUiModel(
            isLoading = false,
            initialError = null,
            insertionError = null,
            quizData = QuizDataUiModel(
                questionText = "Which of the following is NOT a primary color?",
                categoryText = "Art",
                difficulty = DifficultyUiModel.EASY,
                progressText = "01/10",
                scoreText = "0",
                choices = listOf(
                    QuizChoiceUiModel("A", "Red", null),
                    QuizChoiceUiModel("B", "Blue", null),
                    QuizChoiceUiModel("C", "Green", null),
                    QuizChoiceUiModel("D", "Yellow", null),
                ),
                resultChoice = null,
                totalQuestions = 10,
                currentIndex = 0,
                isAnswered = false,
                isLastQuestion = false,
                timerSeconds = 15,
                maxTimerSeconds = 15,
            ),
            isQuitDialogOpen = false
        ),
        QuizUiModel(
            isLoading = false,
            initialError = null,
            insertionError = AppErrorUiModel(
                title = UiText.Raw("Failed to save answer"),
                subtitle = UiText.Raw("An error occurred while saving your progress."),
                icon = Icons.Outlined.Storage,
            ),
            quizData = QuizDataUiModel(
                questionText = "Which of the following is NOT a primary color?",
                categoryText = "Art",
                difficulty = DifficultyUiModel.HARD,
                progressText = "01/10",
                scoreText = "0",
                choices = listOf(
                    QuizChoiceUiModel("A", "Red", null),
                    QuizChoiceUiModel("B", "Blue", null),
                    QuizChoiceUiModel("C", "Green", QuizAnswerStatusUi.CORRECT),
                    QuizChoiceUiModel("D", "Yellow", null),
                ),
                resultChoice = QuizChoiceUiModel("C", "Green", QuizAnswerStatusUi.CORRECT),
                totalQuestions = 10,
                currentIndex = 0,
                isAnswered = true,
                isLastQuestion = false,
                timerSeconds = 10,
                maxTimerSeconds = 15,
            ),
            isQuitDialogOpen = false,
        ),
        QuizUiModel(
            isLoading = false,
            initialError = null,
            insertionError = null,
            quizData = QuizDataUiModel(
                questionText = "Which of the following is NOT a primary color?",
                categoryText = "Art",
                difficulty = DifficultyUiModel.EASY,
                progressText = "01/10",
                scoreText = "0",
                choices = listOf(
                    QuizChoiceUiModel("A", "Red", null),
                    QuizChoiceUiModel("B", "Blue", null),
                    QuizChoiceUiModel("C", "Green", null),
                    QuizChoiceUiModel("D", "Yellow", null),
                ),
                resultChoice = null,
                totalQuestions = 10,
                currentIndex = 0,
                isAnswered = false,
                isLastQuestion = false,
                timerSeconds = 15,
                maxTimerSeconds = 15,
            ),
            isQuitDialogOpen = true,
        ),
        QuizUiModel(
            isLoading = false,
            initialError = null,
            insertionError = null,
            quizData = QuizDataUiModel(
                questionText = "The sky is green.",
                categoryText = "Science",
                difficulty = DifficultyUiModel.EASY,
                progressText = "02/10",
                scoreText = "0/1",
                choices = listOf(
                    QuizChoiceUiModel("A", "True", QuizAnswerStatusUi.INCORRECT),
                    QuizChoiceUiModel("B", "False", QuizAnswerStatusUi.CORRECT),
                ),
                resultChoice = QuizChoiceUiModel("A", "True", QuizAnswerStatusUi.INCORRECT),
                totalQuestions = 10,
                currentIndex = 1,
                isAnswered = true,
                isLastQuestion = false,
                timerSeconds = 12,
                maxTimerSeconds = 15,
            ),
            isQuitDialogOpen = false
        ),
        QuizUiModel(
            isLoading = false,
            initialError = null,
            insertionError = null,
            quizData = QuizDataUiModel(
                questionText = "What is the capital of France?",
                categoryText = "Geography",
                difficulty = DifficultyUiModel.MEDIUM,
                progressText = "03/10",
                scoreText = "1/2",
                choices = listOf(
                    QuizChoiceUiModel("A", "Berlin", null),
                    QuizChoiceUiModel("B", "Paris", QuizAnswerStatusUi.CORRECT),
                    QuizChoiceUiModel("C", "London", null),
                    QuizChoiceUiModel("D", "Madrid", null),
                ),
                resultChoice = QuizChoiceUiModel("", "", QuizAnswerStatusUi.TIMEOUT),
                totalQuestions = 10,
                currentIndex = 2,
                isAnswered = true,
                isLastQuestion = false,
                timerSeconds = 0,
                maxTimerSeconds = 15,
            ),
            isQuitDialogOpen = false
        ),
        QuizUiModel(
            isLoading = false,
            initialError = AppErrorUiModel(
                title = UiText.Raw("No internet connection"),
                subtitle = UiText.Raw("Please check your network settings."),
                icon = Icons.Outlined.WifiOff,
            ),
            insertionError = null,
            quizData = null,
            isQuitDialogOpen = false
        ),
    )
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun QuizScreenPreview(
    @PreviewParameter(QuizUiModelProvider::class) state: QuizUiModel,
) {
    PopKnowTheme {
        QuizScreen(
            state = state,
            onSelectAnswer = {},
            onNextClick = {},
            onSeeResultClick = {},
            onRetryClick = {},
            onDismissInsertionErrorClick = {},
            onShowQuitDialog = {},
            onConfirmQuit = {},
        )
    }
}
