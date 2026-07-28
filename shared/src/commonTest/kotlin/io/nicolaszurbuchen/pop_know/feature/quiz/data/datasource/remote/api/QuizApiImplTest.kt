package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class QuizApiImplTest {

    // region getQuestions — request shape

    @Test
    fun getQuestions_alwaysIncludesAmountParam() {
        var capturedUrl: String? = null
        val api = apiWithMockEngine(triviaResponseJson) { request -> capturedUrl = request.url.toString() }

        runTest { api.getQuestions(amount = 10, categoryId = null, difficulty = null) }

        assertTrue(capturedUrl!!.contains("amount=10"))
    }

    @Test
    fun getQuestions_categoryIdNull_omitsCategoryParam() {
        var capturedUrl: String? = null
        val api = apiWithMockEngine(triviaResponseJson) { request -> capturedUrl = request.url.toString() }

        runTest { api.getQuestions(amount = 10, categoryId = null, difficulty = null) }

        assertFalse(capturedUrl!!.contains("category="))
    }

    @Test
    fun getQuestions_categoryIdProvided_includesCategoryParam() {
        var capturedUrl: String? = null
        val api = apiWithMockEngine(triviaResponseJson) { request -> capturedUrl = request.url.toString() }

        runTest { api.getQuestions(amount = 10, categoryId = 9, difficulty = null) }

        assertTrue(capturedUrl!!.contains("category=9"))
    }

    @Test
    fun getQuestions_difficultyProvided_includesDifficultyParam() {
        var capturedUrl: String? = null
        val api = apiWithMockEngine(triviaResponseJson) { request -> capturedUrl = request.url.toString() }

        runTest { api.getQuestions(amount = 10, categoryId = null, difficulty = "easy") }

        assertTrue(capturedUrl!!.contains("difficulty=easy"))
    }

    @Test
    fun getQuestions_hitsCorrectEndpoint() {
        var capturedPath: String? = null
        val api = apiWithMockEngine(triviaResponseJson) { request -> capturedPath = request.url.encodedPath }

        runTest { api.getQuestions(amount = 10, categoryId = null, difficulty = null) }

        assertTrue(capturedPath!!.endsWith("api.php"))
    }

    // endregion

    // region getCategories — request shape

    @Test
    fun getCategories_hitsCorrectEndpoint() {
        var capturedPath: String? = null
        val api = apiWithMockEngine(categoryResponseJson) { request -> capturedPath = request.url.encodedPath }

        runTest { api.getCategories() }

        assertTrue(capturedPath!!.endsWith("api_category.php"))
    }

    // endregion

    // region deserialization sanity

    @Test
    fun getQuestions_deserializesResponseBodyIntoDto() = runTest {
        val api = apiWithMockEngine(triviaResponseJson) { }

        val result = api.getQuestions(amount = 1, categoryId = null, difficulty = null)

        assertEquals(1, result.results.size)
    }

    // endregion

    private fun apiWithMockEngine(
        responseBody: String,
        onRequest: (io.ktor.client.request.HttpRequestData) -> Unit,
    ): QuizApi {
        val engine = MockEngine { request ->
            onRequest(request)
            respond(
                content = responseBody,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        val client = HttpClient(engine) {
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
        }
        return QuizApiImpl(client)
    }

    private val triviaResponseJson = """
        {"response_code":0,"results":[{"type":"multiple","difficulty":"easy","category":"General Knowledge","question":"Q?","correct_answer":"A","incorrect_answers":["B","C","D"]}]}
    """.trimIndent()

    private val categoryResponseJson = """
        {"trivia_categories":[{"id":9,"name":"General Knowledge"}]}
    """.trimIndent()
}