package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.mapper

import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.CategoryEntity
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.CategoryDto
import io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.remote.dto.TriviaQuestionDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class QuizRemoteMapperTest {

    // a fake decoder with known, controllable behavior — no platform call involved
    private val fakeDecode: (String) -> String = { it.replace("&amp;", "&").replace("&#039;", "'") }
    private val noOpDecode: (String) -> String = { it }

    // region CategoryDto.toEntity

    @Test
    fun toEntity_mapsIdAndDecodedNameCorrectly() {
        val dto = CategoryDto(id = 9, category = "Rock &amp; Roll")

        val entity = dto.toEntity(decodeHtml = fakeDecode)

        assertEquals(CategoryEntity(id = 9L, name = "Rock & Roll"), entity)
    }

    @Test
    fun toEntity_callsProvidedDecodeHtmlOnName() {
        var callCount = 0
        val dto = CategoryDto(id = 1, category = "Anything")

        dto.toEntity(decodeHtml = { callCount++; it })

        assertEquals(1, callCount)
    }

    // endregion

    // region TriviaQuestionDto.toDomain — questionType

    @Test
    fun toDomain_typeMultiple_mapsToMultiple() {
        val dto = questionDto(type = "multiple")

        val result = dto.toDomain(categories = emptyList(), decodeHtml = noOpDecode)

        assertEquals(QuestionType.MULTIPLE, result.questionType)
    }

    @Test
    fun toDomain_typeBoolean_mapsToBoolean() {
        val dto = questionDto(type = "boolean")

        val result = dto.toDomain(categories = emptyList(), decodeHtml = noOpDecode)

        assertEquals(QuestionType.BOOLEAN, result.questionType)
    }

    @Test
    fun toDomain_unknownType_throwsIllegalArgumentException() {
        val dto = questionDto(type = "essay")

        assertFailsWith<IllegalArgumentException> {
            dto.toDomain(categories = emptyList(), decodeHtml = noOpDecode)
        }
    }

    // endregion

    // region TriviaQuestionDto.toDomain — difficulty

    @Test
    fun toDomain_difficultyEasy_mapsToEasy() {
        val dto = questionDto(difficulty = "easy")

        val result = dto.toDomain(categories = emptyList(), decodeHtml = noOpDecode)

        assertEquals(Difficulty.EASY, result.difficulty)
    }

    @Test
    fun toDomain_difficultyMedium_mapsToMedium() {
        val dto = questionDto(difficulty = "medium")

        val result = dto.toDomain(categories = emptyList(), decodeHtml = noOpDecode)

        assertEquals(Difficulty.MEDIUM, result.difficulty)
    }

    @Test
    fun toDomain_difficultyHard_mapsToHard() {
        val dto = questionDto(difficulty = "hard")

        val result = dto.toDomain(categories = emptyList(), decodeHtml = noOpDecode)

        assertEquals(Difficulty.HARD, result.difficulty)
    }

    @Test
    fun toDomain_unknownDifficulty_throwsIllegalArgumentException() {
        val dto = questionDto(difficulty = "impossible")

        assertFailsWith<IllegalArgumentException> {
            dto.toDomain(categories = emptyList(), decodeHtml = noOpDecode)
        }
    }

    // endregion

    // region TriviaQuestionDto.toDomain — category resolution

    @Test
    fun toDomain_categoryMatchesExistingList_usesMatchedCategory() {
        val dto = questionDto(category = "Science")
        val science = Category(id = 17, category = "Science")

        val result = dto.toDomain(
            categories = listOf(science, Category(id = 1, category = "History")),
            decodeHtml = noOpDecode,
        )

        assertEquals(science, result.category)
    }

    @Test
    fun toDomain_categoryNotInList_fallsBackToUnknownCategory() {
        val dto = questionDto(category = "Obscure Category")

        val result = dto.toDomain(
            categories = listOf(Category(id = 1, category = "History")),
            decodeHtml = noOpDecode,
        )

        assertEquals(Category(id = -1, category = "Obscure Category"), result.category)
    }

    @Test
    fun toDomain_categoryMatchingComparesRawCategoryNotDecoded_bugDocumentation() {
        // CategoryDto.toEntity() decodes the category name before it's stored/cached,
        // so `categories` here holds DECODED names (e.g. "Entertainment: Video Games®").
        // But toDomain() matches on the raw, un-decoded `category` field from the
        // question DTO ("Entertainment: Video Games&reg;"). These never match.
        // This test documents the current mismatch — it should fail once fixed,
        // at which point flip this assertion to expect a real match.
        val encodedCategoryName = "Entertainment: Video Games&reg;"
        val decodedCategoryName = "Entertainment: Video Games\u00AE"
        val dto = questionDto(category = encodedCategoryName)

        val result = dto.toDomain(
            categories = listOf(Category(id = 5, category = decodedCategoryName)),
            decodeHtml = noOpDecode, // irrelevant here — the bug is pre-decode field comparison
        )

        assertEquals(Category(id = -1, category = encodedCategoryName), result.category)
    }

    // endregion

    // region TriviaQuestionDto.toDomain — decodeHtml wiring

    @Test
    fun toDomain_appliesDecodeHtmlToQuestionCorrectAnswerAndIncorrectAnswers() {
        val dto = questionDto(
            question = "What&#039;s this?",
            correctAnswer = "Rock &amp; Roll",
            incorrectAnswers = listOf("Salt &amp; Pepper", "Plain"),
        )

        val result = dto.toDomain(categories = emptyList(), decodeHtml = fakeDecode)

        assertEquals("What's this?", result.question)
        assertEquals("Rock & Roll", result.correctAnswer)
        assertEquals(listOf("Salt & Pepper", "Plain"), result.incorrectAnswers)
    }

    @Test
    fun toDomain_callsProvidedDecodeHtmlOnceForQuestionAndAnswers() {
        var callCount = 0
        val dto = questionDto(incorrectAnswers = listOf("A", "B", "C"))

        dto.toDomain(categories = emptyList(), decodeHtml = { callCount++; it })

        // question + correctAnswer + 3 incorrectAnswers = 5 calls
        assertEquals(5, callCount)
    }

    @Test
    fun toDomain_emptyIncorrectAnswers_mapsToEmptyList() {
        val dto = questionDto(incorrectAnswers = emptyList())

        val result = dto.toDomain(categories = emptyList(), decodeHtml = noOpDecode)

        assertEquals(emptyList(), result.incorrectAnswers)
    }

    // endregion

    private fun questionDto(
        type: String = "multiple",
        difficulty: String = "easy",
        category: String = "General Knowledge",
        question: String = "Sample question?",
        correctAnswer: String = "Correct",
        incorrectAnswers: List<String> = listOf("Wrong1", "Wrong2", "Wrong3"),
    ) = TriviaQuestionDto(
        type = type,
        difficulty = difficulty,
        category = category,
        question = question,
        correctAnswer = correctAnswer,
        incorrectAnswers = incorrectAnswers,
    )
}