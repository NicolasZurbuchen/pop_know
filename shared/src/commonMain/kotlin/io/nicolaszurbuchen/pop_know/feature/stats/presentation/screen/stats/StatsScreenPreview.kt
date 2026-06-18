package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.nicolaszurbuchen.pop_know.app.design.theme.PopKnowTheme
import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.AnswerStatsUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.CategoryUi
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.DifficultyUi
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.model.CategoryStatsUi
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.model.DifficultyStatsUi
import io.nicolaszurbuchen.pop_know.feature.stats.presentation.model.FullStatsUi

class StatsStateProvider : PreviewParameterProvider<StatsState> {
    override val values = sequenceOf(
        StatsState(isLoading = true),
        StatsState(
            stats = FullStatsUi(
                summary = AnswerStatsUiModel(100, 75, 0.75f),
                perDifficulty = listOf(
                    DifficultyStatsUi(DifficultyUi.EASY, AnswerStatsUiModel(40, 35, 0.875f)),
                    DifficultyStatsUi(DifficultyUi.MEDIUM, AnswerStatsUiModel(40, 30, 0.75f)),
                    DifficultyStatsUi(DifficultyUi.HARD, AnswerStatsUiModel(20, 10, 0.5f)),
                ),
                perCategory = listOf(
                    CategoryStatsUi(CategoryUi(1, "General Knowledge"), AnswerStatsUiModel(30, 25, 0.83f)),
                    CategoryStatsUi(CategoryUi(2, "Science"), AnswerStatsUiModel(20, 15, 0.75f)),
                    CategoryStatsUi(CategoryUi(3, "History"), AnswerStatsUiModel(50, 35, 0.7f)),
                ),
            )
        ),
        StatsState(error = AppError.Database.QueryFailed(Exception("Database connection error"))),
    )
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun StatsScreenPreview(
    @PreviewParameter(StatsStateProvider::class) state: StatsState,
) {
    PopKnowTheme {
        StatsScreen(
            state = state,
            onBackClick = {},
            onRetryClick = {},
        )
    }
}
