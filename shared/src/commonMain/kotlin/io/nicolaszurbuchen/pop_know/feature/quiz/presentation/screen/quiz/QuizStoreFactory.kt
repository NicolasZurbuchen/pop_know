package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.screen.quiz

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.nicolaszurbuchen.pop_know.common.error.AppError
import io.nicolaszurbuchen.pop_know.common.error.AppException
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuestionProgress
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.QuizSession
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase.StartQuizUseCase
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase.SubmitAnswerUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface QuizStore : Store<QuizIntent, QuizState, QuizLabel>

class QuizStoreFactory(
    private val storeFactory: StoreFactory,
    private val startQuiz: StartQuizUseCase,
    private val submitAnswer: SubmitAnswerUseCase,
) {
    fun create(gameId: Long): QuizStore =
        object :
            QuizStore,
            Store<QuizIntent, QuizState, QuizLabel> by storeFactory.create(
                name = "QuizStore",
                initialState = QuizState(isLoading = true),
                bootstrapper = BootstrapperImpl(),
                executorFactory = { ExecutorImpl(gameId) },
                reducer = ReducerImpl,
            ) {}

    private class BootstrapperImpl : CoroutineBootstrapper<QuizAction>() {
        override fun invoke() {
            dispatch(QuizAction.StartQuiz)
        }
    }

    private inner class ExecutorImpl(private val gameId: Long) :
        CoroutineExecutor<QuizIntent, QuizAction, QuizState, QuizMessage, QuizLabel>() {

        private var timerJob: Job? = null

        override fun executeAction(action: QuizAction) {
            when (action) {
                QuizAction.StartQuiz -> performStartQuiz()
            }
        }

        override fun executeIntent(intent: QuizIntent) {
            val session = state().session
            when (intent) {
                is QuizIntent.SelectAnswer -> handleSelectAnswer(session, intent.answer)
                QuizIntent.Next -> handleNext(session)
                QuizIntent.SeeResult -> handleSeeResult()
                QuizIntent.Retry -> performStartQuiz()
                QuizIntent.DismissInsertionError -> dispatch(QuizMessage.InsertionError(null))
                is QuizIntent.ShowQuitDialog -> dispatch(QuizMessage.ToggleQuitDialog(intent.show))
                QuizIntent.ConfirmQuit -> {
                    dispatch(QuizMessage.ToggleQuitDialog(false))
                    publish(QuizLabel.NavigateBack)
                }
            }
        }

        private fun performStartQuiz() {
            dispatch(QuizMessage.QuizLoading)
            scope.launch {
                try {
                    val quiz = startQuiz(gameId)
                    val shuffled = quiz.questionStates.map { progress ->
                        val question = (progress as QuestionProgress.Unanswered).question
                        (question.incorrectAnswers + question.correctAnswer).shuffled()
                    }
                    dispatch(QuizMessage.QuizLoaded(quiz, shuffled))
                    startTimer()
                } catch (e: Exception) {
                    val error = (e as? AppException)?.error ?: AppError.Unexpected(e)
                    dispatch(QuizMessage.ErrorOccurred(error))
                }
            }
        }

        private fun handleSelectAnswer(session: QuizSession?, answer: String?) {
            if (session == null) return
            if (session.currentQuestion is QuestionProgress.Answered) return

            timerJob?.cancel()
            scope.launch {
                try {
                    val updatedSession = submitAnswer(session, answer)
                    dispatch(QuizMessage.SessionUpdated(updatedSession))
                } catch (e: Exception) {
                    val error = (e as? AppException)?.error ?: AppError.Unexpected(e)
                    dispatch(QuizMessage.InsertionError(error))
                }
            }
        }

        private fun handleNext(session: QuizSession?) {
            if (session == null) return
            val updatedSession = session.advance()
            dispatch(QuizMessage.SessionUpdated(updatedSession))
            startTimer()
        }

        private fun handleSeeResult() {
            timerJob?.cancel()
            publish(QuizLabel.NavigateToResult)
        }

        private fun startTimer() {
            timerJob?.cancel()
            dispatch(QuizMessage.TimerTick(15))
            timerJob = scope.launch {
                for (remaining in 14 downTo 0) {
                    delay(1000)
                    dispatch(QuizMessage.TimerTick(remaining))
                }
                delay(1000)
                handleSelectAnswer(state().session, null)
            }
        }
    }

    private object ReducerImpl : Reducer<QuizState, QuizMessage> {
        override fun QuizState.reduce(msg: QuizMessage): QuizState =
            when (msg) {
                QuizMessage.QuizLoading -> copy(
                    isLoading = true,
                    initialError = null,
                )
                is QuizMessage.QuizLoaded -> copy(
                    isLoading = false,
                    session = msg.session,
                    shuffledAnswers = msg.shuffledAnswers,
                    timerSeconds = 15,
                )
                is QuizMessage.SessionUpdated -> copy(
                    session = msg.session,
                )
                is QuizMessage.TimerTick -> copy(
                    timerSeconds = msg.secondsRemaining,
                )
                is QuizMessage.ErrorOccurred -> copy(
                    isLoading = false,
                    initialError = msg.error,
                )
                is QuizMessage.InsertionError -> copy(
                    insertionError = msg.error,
                )
                is QuizMessage.ToggleQuitDialog -> copy(
                    isQuitDialogOpen = msg.show,
                )
            }
    }
}
