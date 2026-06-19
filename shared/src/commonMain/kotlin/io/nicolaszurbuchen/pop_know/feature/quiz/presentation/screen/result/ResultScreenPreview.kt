package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.nicolaszurbuchen.pop_know.app.design.theme.PopKnowTheme
import io.nicolaszurbuchen.pop_know.common.error.AppErrorUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.AnswerStatsUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.DifficultyUiModel
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz.AnswerStatusUiModel
import io.nicolaszurbuchen.pop_know.infra.ui.UiText

class ResultUiModelProvider : PreviewParameterProvider<ResultUiModel> {
    override val values =
        sequenceOf(
            ResultUiModel(isLoading = true, error = null, content = null),
            ResultUiModel(
                isLoading = false,
                error = null,
                content =
                    GameResultUiModel(
                        questions =
                            listOf(
                                AnsweredQuestionResultUiModel("Q1", "A1", "A1", AnswerStatusUiModel.CORRECT, "Cat", DifficultyUiModel.EASY),
                                AnsweredQuestionResultUiModel(
                                    "Q2",
                                    "A2",
                                    "W2",
                                    AnswerStatusUiModel.INCORRECT,
                                    "Cat",
                                    DifficultyUiModel.EASY,
                                ),
                                AnsweredQuestionResultUiModel("Q3", "A3", null, AnswerStatusUiModel.TIMEOUT, "Cat", DifficultyUiModel.EASY),
                                AnsweredQuestionResultUiModel("Q4", "A4", "A4", AnswerStatusUiModel.CORRECT, "Cat", DifficultyUiModel.EASY),
                                AnsweredQuestionResultUiModel("Q5", "A5", "A5", AnswerStatusUiModel.CORRECT, "Cat", DifficultyUiModel.EASY),
                            ),
                        correctCount = 3,
                        incorrectCount = 1,
                        timeoutCount = 1,
                        score = AnswerStatsUiModel(5, 3, 0.6f),
                    ),
            ),
            ResultUiModel(
                isLoading = false,
                error =
                    AppErrorUiModel(
                        title = UiText.Raw("Failed to load results"),
                        subtitle = UiText.Raw("An error occurred while fetching your game summary."),
                        icon = Icons.Outlined.Storage,
                    ),
                content = null,
            ),
        )
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
private fun ResultScreenPreview(
    @PreviewParameter(ResultUiModelProvider::class) state: ResultUiModel,
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
