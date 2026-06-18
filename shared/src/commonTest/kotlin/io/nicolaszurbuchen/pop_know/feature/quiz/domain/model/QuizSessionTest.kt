package io.nicolaszurbuchen.pop_know.feature.quiz.domain.model

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QuizSessionTest {

    private val question = TriviaQuestion(
        questionType = QuestionType.MULTIPLE,
        difficulty = Difficulty.EASY,
        category = Category(1, "General"),
        question = "What is 1+1?",
        correctAnswer = "2",
        incorrectAnswers = listOf("1", "3", "4")
    )

    private val session = QuizSession(
        gameId = 1L,
        questionStates = listOf(QuestionProgress.Unanswered(question))
    )

    @Test
    fun `submitAnswer should mark as CORRECT when answer is right`() {
        val result = session.submitAnswer("2")
        val current = result.questionStates[0] as QuestionProgress.Answered
        assertEquals(AnswerStatus.CORRECT, current.status)
        assertEquals("2", current.selectedAnswer)
    }

    @Test
    fun `submitAnswer should mark as INCORRECT when answer is wrong`() {
        val result = session.submitAnswer("3")
        val current = result.questionStates[0] as QuestionProgress.Answered
        assertEquals(AnswerStatus.INCORRECT, current.status)
        assertEquals("3", current.selectedAnswer)
    }

    @Test
    fun `submitAnswer should mark as TIMEOUT when answer is null`() {
        val result = session.submitAnswer(null)
        val current = result.questionStates[0] as QuestionProgress.Answered
        assertEquals(AnswerStatus.TIMEOUT, current.status)
        assertEquals(null, current.selectedAnswer)
    }

    @Test
    fun `advance should move to next question after answered`() {
        val answered = session.submitAnswer("2")
        val advanced = answered.advance()
        assertEquals(1, advanced.currentIndex)
        val prev = advanced.questionStates[0] as QuestionProgress.Answered
        assertTrue(prev.advancedToNext)
    }

    @Test
    fun `advance should do nothing when not yet answered`() {
        val advanced = session.advance()
        assertEquals(0, advanced.currentIndex)
    }

    @Test
    fun `submitAnswer should do nothing when already answered`() {
        val answered = session.submitAnswer("2")
        val doubleAnswered = answered.submitAnswer("3")
        val current = doubleAnswered.questionStates[0] as QuestionProgress.Answered
        assertEquals(AnswerStatus.CORRECT, current.status)
        assertEquals("2", current.selectedAnswer)
    }
}
