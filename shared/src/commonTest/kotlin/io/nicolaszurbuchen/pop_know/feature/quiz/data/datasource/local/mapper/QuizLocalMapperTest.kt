package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local.mapper

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.CategoryEntity
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.GetLastGame
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import kotlin.test.Test
import kotlin.test.assertEquals

class QuizLocalMapperTest {

    // region CategoryEntity.toDomain

    @Test
    fun toDomain_mapsIdAndNameCorrectly() {
        val entity = CategoryEntity(id = 9L, name = "Science")

        val result = entity.toDomain()

        assertEquals(Category(id = 9, category = "Science"), result)
    }

    // endregion

    // region GetLastGame.toDomain

    @Test
    fun toDomain_mapsAllFieldsCorrectly() {
        val row = lastGame(
            question = "What is the capital?",
            correct_answer = "Paris",
            selected_answer = "Lyon",
            status = "INCORRECT",
            category_name = "Geography",
            difficulty = "medium",
        )

        val result = row.toDomain()

        assertEquals("What is the capital?", result.question)
        assertEquals("Paris", result.correctAnswer)
        assertEquals("Lyon", result.selectedAnswer)
        assertEquals(AnswerStatus.INCORRECT, result.status)
        assertEquals("Geography", result.categoryName)
        assertEquals(Difficulty.MEDIUM, result.difficulty)
    }

    @Test
    fun toDomain_nullSelectedAnswer_mapsToNull() {
        val row = lastGame(selected_answer = null)

        val result = row.toDomain()

        assertEquals(null, result.selectedAnswer)
    }

    @Test
    fun toDomain_nullCategoryName_mapsToNull() {
        val row = lastGame(category_name = null)

        val result = row.toDomain()

        assertEquals(null, result.categoryName)
    }

    // endregion

    // region QuestionType <-> String round-trip

    @Test
    fun questionType_toValue_mapsEachEnumToExpectedString() {
        assertEquals("multiple", QuestionType.MULTIPLE.toValue())
        assertEquals("boolean", QuestionType.BOOLEAN.toValue())
    }

    // endregion

    // region Difficulty <-> String round-trip

    @Test
    fun difficulty_toValue_mapsEachEnumToExpectedString() {
        assertEquals("easy", Difficulty.EASY.toValue())
        assertEquals("medium", Difficulty.MEDIUM.toValue())
        assertEquals("hard", Difficulty.HARD.toValue())
    }

    @Test
    fun difficulty_toDifficultyEnum_mapsEasyAndMediumExplicitly() {
        assertEquals(Difficulty.EASY, "easy".toDifficultyEnum())
        assertEquals(Difficulty.MEDIUM, "medium".toDifficultyEnum())
    }

    @Test
    fun difficulty_toDifficultyEnum_mapsHardExplicitly() {
        assertEquals(Difficulty.HARD, "hard".toDifficultyEnum())
    }

    @Test
    fun difficulty_toDifficultyEnum_unrecognizedString_silentlyFallsBackToHard_bugDocumentation() {
        // Any unrecognized string currently falls back to HARD rather than failing loudly.
        // This is a local-DB read — an unrecognized value here means data corruption or a
        // migration bug, not external API flakiness, and arguably should throw instead.
        // This test documents current behavior; flip to assertFailsWith if this is fixed.
        assertEquals(Difficulty.HARD, "corrupted-value".toDifficultyEnum())
    }

    @Test
    fun difficulty_toValueThenToDifficultyEnum_roundTripsForAllRealValues() {
        Difficulty.entries.forEach { difficulty ->
            assertEquals(difficulty, difficulty.toValue().toDifficultyEnum())
        }
    }

    // endregion

    // region AnswerStatus <-> String round-trip

    @Test
    fun answerStatus_toValue_mapsEachEnumToExpectedString() {
        assertEquals("CORRECT", AnswerStatus.CORRECT.toValue())
        assertEquals("INCORRECT", AnswerStatus.INCORRECT.toValue())
        assertEquals("TIMEOUT", AnswerStatus.TIMEOUT.toValue())
    }

    @Test
    fun answerStatus_toAnswerStatusEnum_mapsCorrectAndIncorrectExplicitly() {
        assertEquals(AnswerStatus.CORRECT, "CORRECT".toAnswerStatusEnum())
        assertEquals(AnswerStatus.INCORRECT, "INCORRECT".toAnswerStatusEnum())
    }

    @Test
    fun answerStatus_toAnswerStatusEnum_timeoutMapsExplicitly() {
        assertEquals(AnswerStatus.TIMEOUT, "TIMEOUT".toAnswerStatusEnum())
    }

    @Test
    fun answerStatus_toAnswerStatusEnum_unrecognizedString_silentlyFallsBackToTimeout_bugDocumentation() {
        // Same pattern as Difficulty above — an unrecognized status string silently becomes
        // TIMEOUT rather than surfacing as an error, despite this being locally-authored data.
        assertEquals(AnswerStatus.TIMEOUT, "corrupted-value".toAnswerStatusEnum())
    }

    @Test
    fun answerStatus_toValueThenToAnswerStatusEnum_roundTripsForAllRealValues() {
        AnswerStatus.entries.forEach { status ->
            assertEquals(status, status.toValue().toAnswerStatusEnum())
        }
    }

    // endregion

    private fun lastGame(
        id: Long = 1L,
        game_id: Long = 1L,
        type: String = "multiple",
        difficulty: String = "easy",
        question: String = "Sample question?",
        correct_answer: String = "Correct",
        incorrect_answers: List<String> = listOf("A", "B", "C"),
        selected_answer: String? = "Correct",
        status: String = "CORRECT",
        answered_at: Long = 0L,
        category_name: String? = "General Knowledge",
    ) = GetLastGame(
        id = id,
        game_id = game_id,
        type = type,
        difficulty = difficulty,
        question = question,
        correct_answer = correct_answer,
        incorrect_answers = incorrect_answers,
        selected_answer = selected_answer,
        status = status,
        answered_at = answered_at,
        category_name = category_name,
    )
}