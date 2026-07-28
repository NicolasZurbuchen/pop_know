package io.nicolaszurbuchen.pop_know.feature.quiz.domain.model

import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class QuizSessionTest {

    // region submitAnswer — status determination

    @Test
    fun submitAnswer_matchingAnswer_marksCorrect() {
        val session = sessionOf(question(correctAnswer = "Paris"))

        val result = session.submitAnswer("Paris")

        val answered = result.questionStates[0] as QuestionProgress.Answered
        assertEquals(AnswerStatus.CORRECT, answered.status)
    }

    @Test
    fun submitAnswer_nonMatchingAnswer_marksIncorrect() {
        val session = sessionOf(question(correctAnswer = "Paris"))

        val result = session.submitAnswer("Lyon")

        val answered = result.questionStates[0] as QuestionProgress.Answered
        assertEquals(AnswerStatus.INCORRECT, answered.status)
    }

    @Test
    fun submitAnswer_nullAnswer_marksTimeout() {
        val session = sessionOf(question(correctAnswer = "Paris"))

        val result = session.submitAnswer(null)

        val answered = result.questionStates[0] as QuestionProgress.Answered
        assertEquals(AnswerStatus.TIMEOUT, answered.status)
        assertEquals(null, answered.selectedAnswer)
    }

    @Test
    fun submitAnswer_isCaseSensitive() {
        val session = sessionOf(question(correctAnswer = "Paris"))

        val result = session.submitAnswer("paris")

        val answered = result.questionStates[0] as QuestionProgress.Answered
        assertEquals(AnswerStatus.INCORRECT, answered.status)
    }

    @Test
    fun submitAnswer_onAlreadyAnsweredQuestion_returnsUnchangedSession_bugDocumentation() {
        // Known, deliberately unguarded gap: submitAnswer's cast-to-Unanswered guard
        // prevents re-mutating session state, but SubmitAnswerUseCase's own check for
        // "is currentQuestion now Answered" can't distinguish "just answered by this
        // call" from "was already Answered" — meaning a second invoke() at the use-case
        // level will re-persist the same answer. This test only documents the session's
        // own behavior (unchanged state); the persistence duplication is covered in
        // SubmitAnswerUseCaseTest.
        val alreadyAnswered = sessionOf(question(correctAnswer = "Paris"))
            .submitAnswer("Paris")

        val result = alreadyAnswered.submitAnswer("Lyon")

        assertEquals(alreadyAnswered, result)
    }

    // endregion

    // region advance

    @Test
    fun advance_onAnsweredQuestion_incrementsIndexAndMarksAdvanced() {
        val session = sessionOf(question(correctAnswer = "Paris"))
            .submitAnswer("Paris")

        val result = session.advance()

        assertEquals(1, result.currentIndex)
        val advanced = result.questionStates[0] as QuestionProgress.Answered
        assertEquals(true, advanced.advancedToNext)
    }

    @Test
    fun advance_onUnansweredQuestion_returnsUnchangedSession() {
        val session = sessionOf(question(correctAnswer = "Paris"))

        val result = session.advance()

        assertEquals(session, result)
    }

    // endregion

    // region currentQuestion

    @Test
    fun currentQuestion_withinBounds_returnsExpectedState() {
        val q = question(correctAnswer = "Paris")
        val session = sessionOf(q)

        assertEquals(QuestionProgress.Unanswered(q), session.currentQuestion)
    }

    @Test
    fun currentQuestion_pastLastQuestion_throwsIndexOutOfBounds_bugDocumentation() {
        // Known, deliberately unfixed gap for this showcase app: currentQuestion uses
        // direct indexing, so calling it after advancing past the last question throws
        // a raw IndexOutOfBoundsException rather than a domain AppException. The
        // conversation explicitly chose not to fix this — documenting current behavior.
        val session = sessionOf(question(correctAnswer = "Paris"))
            .submitAnswer("Paris")
            .advance()

        assertFailsWith<IndexOutOfBoundsException> { session.currentQuestion }
    }

    // endregion

    // region score

    @Test
    fun score_noAnsweredQuestions_returnsZeroedStats() {
        val session = sessionOf(question(correctAnswer = "Paris"))

        val score = session.score

        assertEquals(0, score.totalAnswered)
        assertEquals(0, score.totalCorrect)
        assertEquals(0f, score.accuracy)
    }

    @Test
    fun score_mixedResults_computesCorrectCountsAndAccuracy() {
        val session = QuizSession(
            questionStates = listOf(
                QuestionProgress.Answered(question(correctAnswer = "A"), "A", AnswerStatus.CORRECT),
                QuestionProgress.Answered(question(correctAnswer = "B"), "X", AnswerStatus.INCORRECT),
                QuestionProgress.Unanswered(question(correctAnswer = "C")),
            ),
        )

        val score = session.score

        assertEquals(2, score.totalAnswered)
        assertEquals(1, score.totalCorrect)
        assertEquals(0.5f, score.accuracy)
    }

    // endregion

    private fun sessionOf(vararg questions: TriviaQuestion) = QuizSession(
        questionStates = questions.map { QuestionProgress.Unanswered(it) },
    )

    private fun question(correctAnswer: String) = TriviaQuestion(
        questionType = QuestionType.MULTIPLE,
        difficulty = Difficulty.EASY,
        category = Category(id = 1, category = "General Knowledge"),
        question = "Sample question?",
        correctAnswer = correctAnswer,
        incorrectAnswers = listOf("Wrong1", "Wrong2", "Wrong3"),
    )
}