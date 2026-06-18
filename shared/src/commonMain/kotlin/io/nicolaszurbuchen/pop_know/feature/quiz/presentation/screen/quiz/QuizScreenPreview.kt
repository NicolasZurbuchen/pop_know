package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.nicolaszurbuchen.pop_know.app.design.theme.PopKnowTheme
import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.DifficultyUi
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus

class QuizStateProvider : PreviewParameterProvider<QuizState> {
    override val values = sequenceOf(
        QuizState(isLoading = true),
        QuizState(
            content = QuizUi(
                questionText = "Which of the following is NOT a primary color?",
                categoryText = "Art",
                difficulty = DifficultyUi.EASY,
                progressText = "01/10",
                scoreText = "0",
                choices = listOf(
                    QuizChoiceUi("A", "Red", null),
                    QuizChoiceUi("B", "Blue", null),
                    QuizChoiceUi("C", "Green", null),
                    QuizChoiceUi("D", "Yellow", null),
                ),
                resultChoice = null,
                totalQuestions = 10,
                currentIndex = 0,
                isAnswered = false,
                isLastQuestion = false,
                timerSeconds = 15,
                maxTimerSeconds = 30,
            )
        ),
        QuizState(
            content = QuizUi(
                questionText = "Which of the following is NOT a primary color?",
                categoryText = "Art",
                difficulty = DifficultyUi.HARD,
                progressText = "01/10",
                scoreText = "0",
                choices = listOf(
                    QuizChoiceUi("A", "Red", null),
                    QuizChoiceUi("B", "Blue", null),
                    QuizChoiceUi("C", "Green", AnswerStatus.CORRECT),
                    QuizChoiceUi("D", "Yellow", null),
                ),
                resultChoice = QuizChoiceUi("C", "Green", AnswerStatus.CORRECT),
                totalQuestions = 10,
                currentIndex = 0,
                isAnswered = true,
                isLastQuestion = false,
                timerSeconds = 10,
                maxTimerSeconds = 30,
            ),
            insertionError = AppError.Database.InsertFailed(Exception("Failed to save answer")),
        ),
        QuizState(
            content = QuizUi(
                questionText = "Which of the following is NOT a primary color?",
                categoryText = "Art",
                difficulty = DifficultyUi.EASY,
                progressText = "01/10",
                scoreText = "0",
                choices = listOf(
                    QuizChoiceUi("A", "Red", null),
                    QuizChoiceUi("B", "Blue", null),
                    QuizChoiceUi("C", "Green", null),
                    QuizChoiceUi("D", "Yellow", null),
                ),
                resultChoice = null,
                totalQuestions = 10,
                currentIndex = 0,
                isAnswered = false,
                isLastQuestion = false,
                timerSeconds = 15,
                maxTimerSeconds = 30,
            ),
            isQuitDialogOpen = true,
        ),
        QuizState(
            content = QuizUi(
                questionText = "The sky is green.",
                categoryText = "Science",
                difficulty = DifficultyUi.EASY,
                progressText = "02/10",
                scoreText = "100",
                choices = listOf(
                    QuizChoiceUi("A", "True", AnswerStatus.INCORRECT),
                    QuizChoiceUi("B", "False", AnswerStatus.CORRECT),
                ),
                resultChoice = QuizChoiceUi("A", "True", AnswerStatus.INCORRECT),
                totalQuestions = 10,
                currentIndex = 1,
                isAnswered = true,
                isLastQuestion = false,
                timerSeconds = 12,
                maxTimerSeconds = 30,
            )
        ),
        QuizState(
            content = QuizUi(
                questionText = "What is the capital of France?",
                categoryText = "Geography",
                difficulty = DifficultyUi.MEDIUM,
                progressText = "03/10",
                scoreText = "100",
                choices = listOf(
                    QuizChoiceUi("A", "Berlin", null),
                    QuizChoiceUi("B", "Paris", AnswerStatus.CORRECT),
                    QuizChoiceUi("C", "London", null),
                    QuizChoiceUi("D", "Madrid", null),
                ),
                resultChoice = QuizChoiceUi("", "", AnswerStatus.TIMEOUT),
                totalQuestions = 10,
                currentIndex = 2,
                isAnswered = true,
                isLastQuestion = false,
                timerSeconds = 0,
                maxTimerSeconds = 30,
            )
        ),
        QuizState(initialError = AppError.Network.Unavailable),
    )
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun QuizScreenPreview(
    @PreviewParameter(QuizStateProvider::class) state: QuizState,
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
