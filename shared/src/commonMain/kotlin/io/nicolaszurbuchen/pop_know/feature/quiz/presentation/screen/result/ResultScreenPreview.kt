package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.AnswerStatsUi
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.DifficultyUi
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.infra.design.theme.PopKnowTheme

class ResultStateProvider : PreviewParameterProvider<ResultState> {
    override val values = sequenceOf(
        ResultState(isLoading = true),
        ResultState(
            content = GameResultUi(
                questions = listOf(
                    AnsweredQuestionResultUi("Q1", "A1", "A1", AnswerStatus.CORRECT, "Cat", DifficultyUi.EASY),
                    AnsweredQuestionResultUi("Q2", "A2", "W2", AnswerStatus.INCORRECT, "Cat", DifficultyUi.EASY),
                    AnsweredQuestionResultUi("Q3", "A3", null, AnswerStatus.TIMEOUT, "Cat", DifficultyUi.EASY),
                    AnsweredQuestionResultUi("Q4", "A4", "A4", AnswerStatus.CORRECT, "Cat", DifficultyUi.EASY),
                    AnsweredQuestionResultUi("Q5", "A5", "A5", AnswerStatus.CORRECT, "Cat", DifficultyUi.EASY),
                ),
                correctCount = 3,
                incorrectCount = 1,
                timeoutCount = 1,
                score = AnswerStatsUi(5, 3, 0.6f)
            )
        ),
        ResultState(
            error = AppError.Database.QueryFailed(Exception("Failed to load results")),
        ),
    )
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun ResultScreenPreview(
    @PreviewParameter(ResultStateProvider::class) state: ResultState,
) {
    PopKnowTheme {
        ResultScreen(
            state = state,
            onNavigateHomeClick = {},
            onPlayAgainClick = {},
            onViewStatsClick = {},
            onRetryClick = {},
        )
    }
}
