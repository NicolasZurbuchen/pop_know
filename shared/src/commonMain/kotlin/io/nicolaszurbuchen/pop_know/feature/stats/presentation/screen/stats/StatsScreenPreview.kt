package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

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
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.CategoryUiModel
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.uimodel.DifficultyUiModel
import io.nicolaszurbuchen.pop_know.infra.ui.UiText

class StatsUiModelProvider : PreviewParameterProvider<StatsUiModel> {
    override val values = sequenceOf(
        StatsUiModel(isLoading = true, error = null, stats = null, isClearDialogOpen = false),
        StatsUiModel(
            isLoading = false,
            error = null,
            stats = StatsDataUiModel(
                summary = AnswerStatsUiModel(100, 75, 0.75f),
                perDifficulty = listOf(
                    StatsDifficultyUiModel(DifficultyUiModel.EASY, AnswerStatsUiModel(40, 35, 0.875f)),
                    StatsDifficultyUiModel(DifficultyUiModel.MEDIUM, AnswerStatsUiModel(40, 30, 0.75f)),
                    StatsDifficultyUiModel(DifficultyUiModel.HARD, AnswerStatsUiModel(20, 10, 0.5f)),
                ),
                perCategory = listOf(
                    StatsCategoryUiModel(CategoryUiModel(1, "General Knowledge"), AnswerStatsUiModel(30, 25, 0.83f)),
                    StatsCategoryUiModel(CategoryUiModel(2, "Science"), AnswerStatsUiModel(20, 15, 0.75f)),
                    StatsCategoryUiModel(CategoryUiModel(3, "History"), AnswerStatsUiModel(50, 35, 0.7f)),
                ),
            ),
            isClearDialogOpen = false
        ),
        StatsUiModel(
            isLoading = false,
            error = AppErrorUiModel(
                title = UiText.Raw("Database connection error"),
                subtitle = UiText.Raw("An error occurred while loading your statistics."),
                icon = Icons.Outlined.Storage,
            ),
            stats = null,
            isClearDialogOpen = false
        ),
        StatsUiModel(
            isLoading = false,
            error = null,
            stats = StatsDataUiModel(
                summary = AnswerStatsUiModel(100, 75, 0.75f),
                perDifficulty = listOf(
                    StatsDifficultyUiModel(DifficultyUiModel.EASY, AnswerStatsUiModel(40, 35, 0.875f)),
                    StatsDifficultyUiModel(DifficultyUiModel.MEDIUM, AnswerStatsUiModel(40, 30, 0.75f)),
                    StatsDifficultyUiModel(DifficultyUiModel.HARD, AnswerStatsUiModel(20, 10, 0.5f)),
                ),
                perCategory = listOf(
                    StatsCategoryUiModel(CategoryUiModel(1, "General Knowledge"), AnswerStatsUiModel(30, 25, 0.83f)),
                ),
            ),
            isClearDialogOpen = true
        ),
    )
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun StatsScreenPreview(
    @PreviewParameter(StatsUiModelProvider::class) state: StatsUiModel,
) {
    PopKnowTheme {
        StatsScreen(
            state = state,
            onBackClick = {},
            onRetryClick = {},
            onClearClick = {},
            onConfirmClear = {},
            onDismissClear = {},
        )
    }
}
