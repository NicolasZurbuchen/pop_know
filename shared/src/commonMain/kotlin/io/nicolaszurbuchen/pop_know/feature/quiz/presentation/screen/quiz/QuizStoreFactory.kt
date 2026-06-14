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
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase.AdvanceQuestionUseCase
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase.StartQuizUseCase
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.usecase.SubmitAnswerUseCase
import io.nicolaszurbuchen.pop_know.feature.quiz.presentation.mapper.QuizUiMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

interface QuizStore : Store<QuizIntent, QuizState, QuizLabel>

class QuizStoreFactory(
    private val storeFactory: StoreFactory,
    private val startQuiz: StartQuizUseCase,
    private val submitAnswer: SubmitAnswerUseCase,
    private val advanceQuestion: AdvanceQuestionUseCase,
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

        private var session: QuizSession? = null
        private var shuffledAnswers: List<List<String>> = emptyList()
        private val timerSeconds = MutableStateFlow(15)
        private var timerJob: Job? = null

        override fun executeAction(action: QuizAction) {
            when (action) {
                QuizAction.StartQuiz -> performStartQuiz()
            }
        }

        override fun executeIntent(intent: QuizIntent) {
            when (intent) {
                is QuizIntent.SelectAnswer -> handleSelectAnswer(intent.answer)
                QuizIntent.Next -> handleNext()
                QuizIntent.SeeResult -> handleSeeResult()
                QuizIntent.Retry -> performStartQuiz()
                QuizIntent.DismissInsertionError -> dispatch(QuizMessage.InsertionError(null))
                is QuizIntent.ShowQuitDialog -> dispatch(QuizMessage.ToggleQuitDialog(intent.show))
                QuizIntent.ConfirmQuit -> publish(QuizLabel.NavigateBack)
            }
        }

        private fun performStartQuiz() {
            dispatch(QuizMessage.QuizStarted)
            scope.launch {
                try {
                    val quiz = startQuiz(gameId)
                    session = quiz
                    shuffledAnswers = quiz.state.value.questionStates.map { progress ->
                        val question = (progress as QuestionProgress.Unanswered).question
                        (question.incorrectAnswers + question.correctAnswer).shuffled()
                    }
                    observeSession(quiz)
                    startTimer()
                } catch (e: Exception) {
                    val appError = when (e) {
                        is AppException -> e.error
                        else -> AppError.Unexpected(e)
                    }
                    dispatch(QuizMessage.ErrorOccurred(appError))
                }
            }
        }

        private fun observeSession(quiz: QuizSession) {
            combine(quiz.state, timerSeconds) { sessionState, seconds ->
                QuizUiMapper.map(sessionState, seconds, shuffledAnswers)
            }.onEach { ui ->
                dispatch(QuizMessage.QuizDataLoaded(ui))
            }.launchIn(scope)
        }

        private fun handleSelectAnswer(answer: String) {
            val quiz = session ?: return
            if (state().content?.isAnswered == true) return
            timerJob?.cancel()
            scope.launch {
                try {
                    submitAnswer(quiz, answer)
                } catch (e: Exception) {
                    dispatch(QuizMessage.InsertionError(AppError.Database.InsertFailed(e)))
                }
            }
        }

        private fun handleNext() {
            val quiz = session ?: return
            advanceQuestion(quiz)
            startTimer()
        }

        private fun handleSeeResult() {
            timerJob?.cancel()
            publish(QuizLabel.NavigateToResult)
        }

        private fun startTimer() {
            timerJob?.cancel()
            timerSeconds.value = 15
            timerJob = scope.launch {
                for (remaining in 14 downTo 0) {
                    delay(1000)
                    if (state().content?.isAnswered == true) return@launch
                    timerSeconds.value = remaining
                }
                delay(1000)
                val quiz = session ?: return@launch
                if (state().content?.isAnswered == false) {
                    submitAnswer(quiz, null)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<QuizState, QuizMessage> {
        override fun QuizState.reduce(msg: QuizMessage): QuizState =
            when (msg) {
                is QuizMessage.QuizDataLoaded -> copy(
                    isLoading = false,
                    content = msg.content,
                    initialError = null,
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
                QuizMessage.QuizStarted -> copy(isLoading = true, initialError = null)
            }
    }
}
