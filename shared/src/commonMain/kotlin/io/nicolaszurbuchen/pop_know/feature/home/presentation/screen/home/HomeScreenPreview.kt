package io.nicolaszurbuchen.pop_know.feature.home.presentation.screen.home

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
import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import popknow.shared.generated.resources.Res
import popknow.shared.generated.resources.img_error_database

class HomeUiModelProvider : PreviewParameterProvider<HomeUiModel> {
    override val values = sequenceOf(
        HomeUiModel(isLoading = true, error = null, stats = null, hasHistory = false),
        HomeUiModel(
            isLoading = false,
            error = null,
            stats = AnswerStatsUiModel(
                totalAnswered = 10,
                totalCorrect = 8,
                accuracy = 0.8f,
            ),
            hasHistory = true,
        ),
        HomeUiModel(
            isLoading = false,
            error = AppErrorUiModel(
                title = UiText.Raw("Database connection lost"),
                subtitle = UiText.Raw("Please check your internet connection or try again later."),
                icon = Icons.Outlined.Storage,
                imageRes = Res.drawable.img_error_database,
            ),
            stats = null,
            hasHistory = false,
        ),
    )
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview(
    @PreviewParameter(HomeUiModelProvider::class) state: HomeUiModel,
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
