package io.nicolaszurbuchen.pop_know.feature.stats.presentation.screen.stats

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.nicolaszurbuchen.pop_know.common.domain.AnswerStats
import io.nicolaszurbuchen.pop_know.common.domain.Category
import io.nicolaszurbuchen.pop_know.common.domain.Difficulty
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.CategoryStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.DifficultyStats
import io.nicolaszurbuchen.pop_know.feature.stats.domain.model.FullStats
import io.nicolaszurbuchen.pop_know.infra.design.theme.PopKnowTheme
import io.nicolaszurbuchen.pop_know.infra.ui.UiText

class StatsStateProvider : PreviewParameterProvider<StatsState> {
    override val values = sequenceOf(
        StatsState(isLoading = true),
        StatsState(
            stats = FullStats(
                summary = AnswerStats(100, 75, 0.75f),
                perDifficulty = listOf(
                    DifficultyStats(Difficulty.EASY, AnswerStats(40, 35, 0.875f)),
                    DifficultyStats(Difficulty.MEDIUM, AnswerStats(40, 30, 0.75f)),
                    DifficultyStats(Difficulty.HARD, AnswerStats(20, 10, 0.5f)),
                ),
                perCategory = listOf(
                    CategoryStats(Category(1, "General Knowledge"), AnswerStats(30, 25, 0.83f)),
                    CategoryStats(Category(2, "Science"), AnswerStats(20, 15, 0.75f)),
                    CategoryStats(Category(3, "History"), AnswerStats(50, 35, 0.7f)),
                ),
            )
        ),
        StatsState(error = UiText.Raw("Could not load statistics")),
    )
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun StatsScreenPreview(
    @PreviewParameter(StatsStateProvider::class) state: StatsState,
) {
    PopKnowTheme {
        Surface {
            StatsScreen(
                state = state,
                onBackClick = {},
            )
        }
    }
}
