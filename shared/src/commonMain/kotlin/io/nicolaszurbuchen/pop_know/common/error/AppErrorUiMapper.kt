package io.nicolaszurbuchen.pop_know.common.error

import io.nicolaszurbuchen.pop_know.infra.ui.UiText
import popknow.shared.generated.resources.Res
import popknow.shared.generated.resources.error_database_generic_subtitle
import popknow.shared.generated.resources.error_database_insert_failed_title
import popknow.shared.generated.resources.error_database_query_failed_title
import popknow.shared.generated.resources.error_network_http_subtitle_default
import popknow.shared.generated.resources.error_network_http_title
import popknow.shared.generated.resources.error_network_timeout_subtitle
import popknow.shared.generated.resources.error_network_timeout_title
import popknow.shared.generated.resources.error_network_unavailable_subtitle
import popknow.shared.generated.resources.error_network_unavailable_title
import popknow.shared.generated.resources.error_trivia_generic_title
import popknow.shared.generated.resources.error_trivia_invalid_parameter_subtitle
import popknow.shared.generated.resources.error_trivia_no_results_subtitle
import popknow.shared.generated.resources.error_trivia_no_results_title
import popknow.shared.generated.resources.error_trivia_rate_limit_subtitle
import popknow.shared.generated.resources.error_unexpected_subtitle
import popknow.shared.generated.resources.error_unexpected_title
import popknow.shared.generated.resources.img_error_database
import popknow.shared.generated.resources.img_error_network
import popknow.shared.generated.resources.img_error_trivia
import popknow.shared.generated.resources.img_error_unexpected

fun AppError.toUiModel(): AppErrorUiModel = when (this) {
    is AppError.Network.Unavailable -> AppErrorUiModel(
        title = UiText.Resource(Res.string.error_network_unavailable_title),
        subtitle = UiText.Resource(Res.string.error_network_unavailable_subtitle),
        imageRes = Res.drawable.img_error_network,
    )

    is AppError.Network.Timeout -> AppErrorUiModel(
        title = UiText.Resource(Res.string.error_network_timeout_title),
        subtitle = UiText.Resource(Res.string.error_network_timeout_subtitle),
        imageRes = Res.drawable.img_error_network,
    )

    is AppError.Network.Http -> AppErrorUiModel(
        title = UiText.Resource(Res.string.error_network_http_title),
        subtitle = serverMessage?.let { UiText.Raw(it) }
            ?: UiText.Resource(Res.string.error_network_http_subtitle_default),
        imageRes = Res.drawable.img_error_network,
    )

    is AppError.Database.QueryFailed -> AppErrorUiModel(
        title = UiText.Resource(Res.string.error_database_query_failed_title),
        subtitle = UiText.Resource(Res.string.error_database_generic_subtitle),
        imageRes = Res.drawable.img_error_database,
    )

    is AppError.Database.InsertFailed -> AppErrorUiModel(
        title = UiText.Resource(Res.string.error_database_insert_failed_title),
        subtitle = UiText.Resource(Res.string.error_database_generic_subtitle),
        imageRes = Res.drawable.img_error_database,
    )

    is AppError.Trivia.NoResults -> AppErrorUiModel(
        title = UiText.Resource(Res.string.error_trivia_no_results_title),
        subtitle = UiText.Resource(Res.string.error_trivia_no_results_subtitle),
        imageRes = Res.drawable.img_error_trivia,
    )

    is AppError.Trivia.InvalidParameter -> AppErrorUiModel(
        title = UiText.Resource(Res.string.error_trivia_generic_title),
        subtitle = UiText.Resource(Res.string.error_trivia_invalid_parameter_subtitle),
        imageRes = Res.drawable.img_error_trivia,
    )

    is AppError.Trivia.RateLimit -> AppErrorUiModel(
        title = UiText.Resource(Res.string.error_trivia_generic_title),
        subtitle = UiText.Resource(Res.string.error_trivia_rate_limit_subtitle),
        imageRes = Res.drawable.img_error_trivia,
    )

    is AppError.Unexpected -> AppErrorUiModel(
        title = UiText.Resource(Res.string.error_unexpected_title),
        subtitle = UiText.Resource(Res.string.error_unexpected_subtitle),
        imageRes = Res.drawable.img_error_unexpected,
    )
}