package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuizSession
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class QuizReducerTest {

    private val reducer = QuizStoreFactory.ReducerImpl

    // region QuizLoading

    @Test
    fun quizLoading_setsLoadingTrueAndClearsInitialError() {
        val state = QuizState(isLoading = false, initialError = AppError.Network.Unavailable)

        val result = with(reducer) { state.reduce(QuizMessage.QuizLoading) }

        assertEquals(true, result.isLoading)
        assertNull(result.initialError)
    }

    // endregion

    // region QuizLoaded

    @Test
    fun quizLoaded_setsSessionShuffledAnswersAndResetsTimer() {
        val state = QuizState(isLoading = true)
        val session = sampleSession()
        val shuffled = listOf(listOf("A", "B", "C", "D"))

        val result = with(reducer) {
            state.reduce(QuizMessage.QuizLoaded(session, shuffled))
        }

        assertEquals(false, result.isLoading)
        assertEquals(session, result.session)
        assertEquals(shuffled, result.shuffledAnswers)
        assertEquals(15, result.timerSeconds)
    }

    // endregion

    // region SessionUpdated

    @Test
    fun sessionUpdated_replacesSessionOnly() {
        val state = QuizState(session = sampleSession(), timerSeconds = 7)
        val newSession = sampleSession()

        val result = with(reducer) { state.reduce(QuizMessage.SessionUpdated(newSession)) }

        assertEquals(newSession, result.session)
        assertEquals(7, result.timerSeconds) // untouched by this message
    }

    // endregion

    // region TimerTick

    @Test
    fun timerTick_updatesSecondsRemainingOnly() {
        val state = QuizState(timerSeconds = 15)

        val result = with(reducer) { state.reduce(QuizMessage.TimerTick(9)) }

        assertEquals(9, result.timerSeconds)
    }

    // endregion

    // region ErrorOccurred

    @Test
    fun errorOccurred_setsInitialErrorAndClearsLoading() {
        val state = QuizState(isLoading = true)

        val result = with(reducer) {
            state.reduce(QuizMessage.ErrorOccurred(AppError.Network.Unavailable))
        }

        assertEquals(false, result.isLoading)
        assertEquals(AppError.Network.Unavailable, result.initialError)
    }

    // endregion

    // region InsertionError

    @Test
    fun insertionError_setsErrorWithoutTouchingLoadingOrSession() {
        val state = QuizState(isLoading = false, session = sampleSession())

        val result = with(reducer) {
            state.reduce(QuizMessage.InsertionError(AppError.Database.InsertFailed(RuntimeException())))
        }

        assertEquals(true, result.insertionError is AppError.Database.InsertFailed)
        assertEquals(false, result.isLoading)
    }

    @Test
    fun insertionError_null_clearsError() {
        val state = QuizState(insertionError = AppError.Network.Unavailable)

        val result = with(reducer) { state.reduce(QuizMessage.InsertionError(null)) }

        assertNull(result.insertionError)
    }

    // endregion

    // region ToggleQuitDialog

    @Test
    fun toggleQuitDialog_setsFlagBothWays() {
        val state = QuizState(isQuitDialogOpen = false)

        val opened = with(reducer) { state.reduce(QuizMessage.ToggleQuitDialog(true)) }
        val closed = with(reducer) { opened.reduce(QuizMessage.ToggleQuitDialog(false)) }

        assertEquals(true, opened.isQuitDialogOpen)
        assertEquals(false, closed.isQuitDialogOpen)
    }

    // endregion

    private fun sampleSession() = QuizSession(questionStates = emptyList())
}