package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.nicolaszurbuchen.pop_know.cache.AppDatabase
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.CategoryEntity
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.QuestionHistoryEntity
import io.nicolaszurbuchen.pop_know.infra.database.stringListAdapter
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class QuizLocalDataSourceImplTest {

    private lateinit var driver: SqlDriver
    private lateinit var dataSource: QuizLocalDataSourceImpl

    @BeforeTest
    fun setup() {
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        AppDatabase.Schema.create(driver)
        val database = AppDatabase(
            driver = driver,
            QuestionHistoryEntityAdapter = QuestionHistoryEntity.Adapter(
                incorrect_answersAdapter = stringListAdapter,
            ),
        )
        dataSource = QuizLocalDataSourceImpl(
            questionHistoryQueries = database.questionHistoryQueries,
            categoryQueries = database.categoryQueries,
            nowMillis = { 12345L },
        )
    }

    @AfterTest
    fun tearDown() {
        driver.close()
    }

    // region saveCategories / getCategories

    @Test
    fun saveCategories_thenGetCategories_roundTripsCorrectly() {
        val categories = listOf(
            CategoryEntity(id = 9L, name = "General Knowledge"),
            CategoryEntity(id = 10L, name = "Science"),
        )

        dataSource.saveCategories(categories)
        val result = dataSource.getCategories()

        assertEquals(categories.toSet(), result.toSet())
    }

    @Test
    fun getCategories_whenEmpty_returnsEmptyList() {
        val result = dataSource.getCategories()

        assertEquals(emptyList(), result)
    }

    // endregion

    // region saveAnswer / getLastGame

    @Test
    fun saveAnswer_thenGetLastGame_roundTripsCorrectly() {
        dataSource.saveCategories(listOf(CategoryEntity(id = 1L, name = "History")))
        dataSource.saveAnswer(
            gameId = 1L,
            type = "multiple",
            difficulty = "easy",
            categoryId = 1L,
            question = "Q?",
            correctAnswer = "A",
            incorrectAnswers = listOf("B", "C", "D"),
            selectedAnswer = "A",
            status = "CORRECT",
        )

        val result = dataSource.getLastGame()

        assertEquals(1, result.size)
        val saved = result.first()
        assertEquals("Q?", saved.question)
        assertEquals("A", saved.correct_answer)
        assertEquals(listOf("B", "C", "D"), saved.incorrect_answers)
        assertEquals("A", saved.selected_answer)
        assertEquals("CORRECT", saved.status)
        assertEquals(12345L, saved.answered_at)
    }

    @Test
    fun saveAnswer_nullSelectedAnswer_persistsAsNull() {
        dataSource.saveCategories(listOf(CategoryEntity(id = 1L, name = "History")))
        dataSource.saveAnswer(
            gameId = 1L,
            type = "boolean",
            difficulty = "hard",
            categoryId = 1L,
            question = "Q?",
            correctAnswer = "True",
            incorrectAnswers = listOf("False"),
            selectedAnswer = null,
            status = "TIMEOUT",
        )

        val result = dataSource.getLastGame()

        assertNull(result.first().selected_answer)
    }

    @Test
    fun getLastGame_whenEmpty_returnsEmptyList() {
        val result = dataSource.getLastGame()

        assertEquals(emptyList(), result)
    }

    // endregion
}