package io.nicolaszurbuchen.pop_know.core.data.data_source.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.logging.Logger
import kotlinx.serialization.json.Json

fun createHttpClient(): HttpClient {
    return HttpClient(httpClientEngine()) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            //logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        defaultRequest {
            url("https://opentdb.com/")
        }
    }
}