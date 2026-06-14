package io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.trivia.presentation.model.AnswerStatsUi
import io.nicolaszurbuchen.pop_know.infra.design.theme.PopKnowTheme

class HomeStateProvider : PreviewParameterProvider<HomeState> {
    override val values = sequenceOf(
        HomeState(isLoading = true),
        HomeState(
            stats = AnswerStatsUi(
                totalAnswered = 10,
                totalCorrect = 8,
                accuracy = 0.8f,
            )
        ),
        HomeState(
            error = AppError.Database.QueryFailed(Exception("Database connection lost")),
        ),
    )
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview(
    @PreviewParameter(HomeStateProvider::class) state: HomeState,
) {
    PopKnowTheme {
        HomeScreen(
            state = state,
            onStartRoundClick = {},
            onViewStatsClick = {},
            onRetryClick = {},
            onDismissErrorClick = {},
        )
    }
}
