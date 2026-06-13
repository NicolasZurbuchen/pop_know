package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.nicolaszurbuchen.pop_know.common.domain.Difficulty
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.infra.design.theme.PopKnowTheme
import io.nicolaszurbuchen.pop_know.infra.ui.UiText

class QuizStateProvider : PreviewParameterProvider<QuizState> {
    override val values = sequenceOf(
        QuizState(isLoading = true),
        QuizState(
            content = QuizUiModel(
                questionText = "Which of the following is NOT a primary color?",
                categoryText = "Art",
                difficulty = Difficulty.EASY,
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
                maxTimerSeconds = 30,
            )
        ),
        QuizState(
            content = QuizUiModel(
                questionText = "Which of the following is NOT a primary color?",
                categoryText = "Art",
                difficulty = Difficulty.EASY,
                progressText = "01/10",
                scoreText = "0",
                choices = listOf(
                    QuizChoiceUiModel("A", "Red", null),
                    QuizChoiceUiModel("B", "Blue", null),
                    QuizChoiceUiModel("C", "Green", AnswerStatus.CORRECT),
                    QuizChoiceUiModel("D", "Yellow", null),
                ),
                resultChoice = QuizChoiceUiModel("C", "Green", AnswerStatus.CORRECT),
                totalQuestions = 10,
                currentIndex = 0,
                isAnswered = true,
                isLastQuestion = false,
                timerSeconds = 10,
                maxTimerSeconds = 30,
            )
        ),
        QuizState(error = UiText.Raw("Failed to load quiz")),
    )
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun QuizScreenPreview(
    @PreviewParameter(QuizStateProvider::class) state: QuizState,
) {
    PopKnowTheme {
        Surface {
            QuizScreen(
                state = state,
                onSelectAnswer = {},
                onNextClick = {},
                onSeeResultClick = {},
            )
        }
    }
}
