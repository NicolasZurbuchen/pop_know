package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.result

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.nicolaszurbuchen.pop_know.common.domain.Difficulty
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnsweredQuestionResult
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.GameResult
import io.nicolaszurbuchen.pop_know.infra.design.theme.PopKnowTheme

class ResultStateProvider : PreviewParameterProvider<ResultState> {
    override val values = sequenceOf(
        ResultState(isLoading = true),
        ResultState(
            content = GameResult(
                questions = listOf(
                    AnsweredQuestionResult("Q1", "A1", "A1", AnswerStatus.CORRECT, "Cat", Difficulty.EASY),
                    AnsweredQuestionResult("Q2", "A2", "W2", AnswerStatus.INCORRECT, "Cat", Difficulty.EASY),
                    AnsweredQuestionResult("Q3", "A3", null, AnswerStatus.TIMEOUT, "Cat", Difficulty.EASY),
                    AnsweredQuestionResult("Q4", "A4", "A4", AnswerStatus.CORRECT, "Cat", Difficulty.EASY),
                    AnsweredQuestionResult("Q5", "A5", "A5", AnswerStatus.CORRECT, "Cat", Difficulty.EASY),
                ),
            )
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
        Surface {
            ResultScreen(
                state = state,
                onNavigateHomeClick = {},
                onPlayAgainClick = {},
                onViewStatsClick = {},
            )
        }
    }
}
