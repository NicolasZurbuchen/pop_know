package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuestionProgress
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuizSession
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class QuizUiMapperTest {

    // region top-level passthrough

    @Test
    fun toUiModel_passesThroughLoadingAndDialogFlags() {
        val state = QuizState(isLoading = true, isQuitDialogOpen = true)

        val result = state.toUiModel()

        assertEquals(true, result.isLoading)
        assertEquals(true, result.isQuitDialogOpen)
    }

    @Test
    fun toUiModel_nullSession_producesNullQuizData() {
        val state = QuizState(session = null)

        val result = state.toUiModel()

        assertNull(result.quizData)
    }

    @Test
    fun toUiModel_nullErrors_mapToNullUiModels() {
        val state = QuizState(initialError = null, insertionError = null)

        val result = state.toUiModel()

        assertNull(result.initialError)
        assertNull(result.insertionError)
    }

    @Test
    fun toUiModel_nonNullErrors_areMapped() {
        val state = QuizState(
            initialError = AppError.Network.Unavailable,
            insertionError = AppError.Database.InsertFailed(RuntimeException()),
        )

        val result = state.toUiModel()

        assertTrue(result.initialError != null)
        assertTrue(result.insertionError != null)
    }

    // endregion

    // region choices — status assignment

    @Test
    fun quizData_unansweredQuestion_allChoicesHaveNullStatus() {
        val question = sampleQuestion(correctAnswer = "Paris")
        val state = stateWith(question, shuffled = listOf("Paris", "Lyon", "Nice", "Nantes"))

        val result = state.toUiModel().quizData!!

        assertTrue(result.choices.all { it.status == null })
        assertEquals(false, result.isAnswered)
    }

    @Test
    fun quizData_answeredCorrectly_selectedChoiceMarkedCorrect() {
        val question = sampleQuestion(correctAnswer = "Paris")
        val state = stateWith(
            question,
            shuffled = listOf("Paris", "Lyon", "Nice", "Nantes"),
            answered = QuestionProgress.Answered(question, "Paris", AnswerStatus.CORRECT),
        )

        val result = state.toUiModel().quizData!!

        val parisChoice = result.choices.first { it.text == "Paris" }
        assertEquals(AnswerStatusUiModel.CORRECT, parisChoice.status)
        assertEquals(true, result.isAnswered)
    }

    @Test
    fun quizData_answeredIncorrectly_selectedChoiceMarkedIncorrectAndCorrectAnswerStillMarkedCorrect() {
        val question = sampleQuestion(correctAnswer = "Paris")
        val state = stateWith(
            question,
            shuffled = listOf("Paris", "Lyon", "Nice", "Nantes"),
            answered = QuestionProgress.Answered(question, "Lyon", AnswerStatus.INCORRECT),
        )

        val result = state.toUiModel().quizData!!

        assertEquals(AnswerStatusUiModel.INCORRECT, result.choices.first { it.text == "Lyon" }.status)
        assertEquals(AnswerStatusUiModel.CORRECT, result.choices.first { it.text == "Paris" }.status)
        assertNull(result.choices.first { it.text == "Nice" }.status)
    }

    @Test
    fun quizData_choicesAreLetteredSequentially() {
        val question = sampleQuestion(correctAnswer = "Paris")
        val state = stateWith(question, shuffled = listOf("Paris", "Lyon", "Nice", "Nantes"))

        val result = state.toUiModel().quizData!!

        assertEquals(listOf("A", "B", "C", "D"), result.choices.map { it.letter })
    }

    // endregion

    // region resultChoice

    @Test
    fun resultChoice_unanswered_isNull() {
        val question = sampleQuestion(correctAnswer = "Paris")
        val state = stateWith(question, shuffled = listOf("Paris", "Lyon", "Nice", "Nantes"))

        val result = state.toUiModel().quizData!!

        assertNull(result.resultChoice)
    }

    @Test
    fun resultChoice_timeout_isSentinelWithTimeoutStatus() {
        val question = sampleQuestion(correctAnswer = "Paris")
        val state = stateWith(
            question,
            shuffled = listOf("Paris", "Lyon", "Nice", "Nantes"),
            answered = QuestionProgress.Answered(question, null, AnswerStatus.TIMEOUT),
        )

        val result = state.toUiModel().quizData!!

        assertEquals(AnswerStatusUiModel.TIMEOUT, result.resultChoice?.status)
        assertEquals("", result.resultChoice?.text)
    }

    @Test
    fun resultChoice_answeredNormally_matchesSelectedChoice() {
        val question = sampleQuestion(correctAnswer = "Paris")
        val state = stateWith(
            question,
            shuffled = listOf("Paris", "Lyon", "Nice", "Nantes"),
            answered = QuestionProgress.Answered(question, "Lyon", AnswerStatus.INCORRECT),
        )

        val result = state.toUiModel().quizData!!

        assertEquals("Lyon", result.resultChoice?.text)
    }

    @Test
    fun resultChoice_selectedAnswerNotAmongChoices_silentlyReturnsNull_bugDocumentation() {
        // Documents a real (low-probability but unguarded) edge case: resultChoice is
        // resolved via choices.firstOrNull { it.text == answered.selectedAnswer }. If
        // selectedAnswer doesn't textually match any current choice — encoding drift,
        // whitespace, or any other mismatch between what was persisted and what's
        // rendered — this silently returns null with no error, and the UI would show
        // no indication of what the user picked.
        val question = sampleQuestion(correctAnswer = "Paris")
        val state = stateWith(
            question,
            shuffled = listOf("Paris", "Lyon", "Nice", "Nantes"),
            answered = QuestionProgress.Answered(question, "Marseille", AnswerStatus.INCORRECT),
        )

        val result = state.toUiModel().quizData!!

        assertNull(result.resultChoice)
    }

    // endregion

    // region shuffledAnswers fallback

    @Test
    fun shuffledAnswersMissingForCurrentIndex_fallsBackToAdHocShuffle() {
        // Documents current behavior: if shuffledAnswers doesn't have an entry for
        // currentIndex, a fresh (potentially different every call) shuffle is computed
        // instead of a stable one. Asserting only on set membership, not order, since
        // order is randomized by design in this fallback path.
        val question = sampleQuestion(correctAnswer = "Paris")
        val state = QuizState(
            session = QuizSession(questionStates = listOf(QuestionProgress.Unanswered(question))),
            shuffledAnswers = emptyList(), // no entry for index 0
        )

        val result = state.toUiModel().quizData!!

        assertEquals(setOf("Paris", "Wrong1", "Wrong2", "Wrong3"), result.choices.map { it.text }.toSet())
    }

    // endregion

    // region progress / score formatting

    @Test
    fun progressText_padsSingleDigitsToTwoDigits() {
        val questions = List(12) { sampleQuestion(correctAnswer = "A") }
        val state = QuizState(
            session = QuizSession(
                questionStates = questions.map { QuestionProgress.Unanswered(it) },
                currentIndex = 0,
            ),
            shuffledAnswers = questions.map { listOf("A", "B", "C", "D") },
        )

        val result = state.toUiModel().quizData!!

        assertEquals("01/12", result.progressText)
    }

    @Test
    fun progressText_doesNotPadDoubleDigits() {
        val questions = List(12) { sampleQuestion(correctAnswer = "A") }
        val state = QuizState(
            session = QuizSession(
                questionStates = questions.map { QuestionProgress.Unanswered(it) },
                currentIndex = 11,
            ),
            shuffledAnswers = questions.map { listOf("A", "B", "C", "D") },
        )

        val result = state.toUiModel().quizData!!

        assertEquals("12/12", result.progressText)
    }

    @Test
    fun scoreText_reflectsAnsweredQuestionsOnly() {
        val q1 = sampleQuestion(correctAnswer = "A")
        val q2 = sampleQuestion(correctAnswer = "B")
        val state = QuizState(
            session = QuizSession(
                questionStates = listOf(
                    QuestionProgress.Answered(q1, "A", AnswerStatus.CORRECT),
                    QuestionProgress.Unanswered(q2),
                ),
            ),
            shuffledAnswers = listOf(listOf("A", "X", "Y", "Z"), listOf("B", "X", "Y", "Z")),
        )

        val result = state.toUiModel().quizData!!

        assertEquals("1/1", result.scoreText)
    }

    // endregion

    // region isLastQuestion boundary

    @Test
    fun isLastQuestion_trueOnFinalIndex() {
        val questions = listOf(sampleQuestion(correctAnswer = "A"), sampleQuestion(correctAnswer = "B"))
        val state = QuizState(
            session = QuizSession(
                questionStates = questions.map { QuestionProgress.Unanswered(it) },
                currentIndex = 1,
            ),
            shuffledAnswers = questions.map { listOf("A", "B", "C", "D") },
        )

        val result = state.toUiModel().quizData!!

        assertEquals(true, result.isLastQuestion)
    }

    @Test
    fun isLastQuestion_falseBeforeFinalIndex() {
        val questions = listOf(sampleQuestion(correctAnswer = "A"), sampleQuestion(correctAnswer = "B"))
        val state = QuizState(
            session = QuizSession(
                questionStates = questions.map { QuestionProgress.Unanswered(it) },
                currentIndex = 0,
            ),
            shuffledAnswers = questions.map { listOf("A", "B", "C", "D") },
        )

        val result = state.toUiModel().quizData!!

        assertEquals(false, result.isLastQuestion)
    }

    // endregion

    private fun stateWith(
        question: TriviaQuestion,
        shuffled: List<String>,
        answered: QuestionProgress.Answered? = null,
    ) = QuizState(
        session = QuizSession(
            questionStates = listOf(answered ?: QuestionProgress.Unanswered(question)),
        ),
        shuffledAnswers = listOf(shuffled),
    )

    private fun sampleQuestion(correctAnswer: String) = TriviaQuestion(
        questionType = QuestionType.MULTIPLE,
        difficulty = Difficulty.EASY,
        category = Category(id = 1, category = "General Knowledge"),
        question = "Sample question?",
        correctAnswer = correctAnswer,
        incorrectAnswers = listOf("Wrong1", "Wrong2", "Wrong3"),
    )
}