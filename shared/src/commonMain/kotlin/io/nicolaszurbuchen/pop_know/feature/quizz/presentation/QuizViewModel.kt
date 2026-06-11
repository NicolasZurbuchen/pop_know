package io.nicolaszurbuchen.pop_know.feature.quizz.presentation

import androidx.lifecycle.viewModelScope
import io.nicolaszurbuchen.pop_know.core.domain.TriviaError
import io.nicolaszurbuchen.pop_know.core.presentation.UiText
import io.nicolaszurbuchen.pop_know.core.presentation.mvi.MviViewModel
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.QuestionProgress
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.QuizSession
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.usecase.AdvanceQuestionUseCase
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.usecase.StartQuizUseCase
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.usecase.SubmitAnswerUseCase
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.mapper.QuizUiMapper
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.QuizEffect
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.QuizIntent
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.QuizState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class QuizViewModel(
    private val gameId: Long,
    private val startQuizUseCase: StartQuizUseCase,
    private val submitAnswerUseCase: SubmitAnswerUseCase,
    private val advanceQuestionUseCase: AdvanceQuestionUseCase,
) : MviViewModel<QuizState, QuizIntent, QuizEffect>(
    initialState = QuizState(isLoading = true),
) {
    private var session: QuizSession? = null
    private lateinit var shuffledAnswers: List<List<String>>
    private val timerSeconds = MutableStateFlow(15)
    private var timerJob: Job? = null

    init {
        startQuiz()
    }

    override fun onIntent(intent: QuizIntent) {
        when (intent) {
            is QuizIntent.SelectAnswer -> handleSelectAnswer(intent.answer)
            QuizIntent.Next -> handleNext()
            QuizIntent.SeeResult -> handleSeeResult()
        }
    }

    private fun startQuiz() {
        launch(
            onError = { error ->
                updateState { copy(isLoading = false, error = error.toUiText()) }
            }
        ) {
            val quiz = startQuizUseCase(gameId)
            session = quiz
            shuffledAnswers = quiz.state.value.questionStates.map { progress ->
                val question = (progress as QuestionProgress.Unanswered).question
                (question.incorrectAnswers + question.correctAnswer).shuffled()
            }
            observeSession(quiz)
            startTimer()
        }
    }

    private fun observeSession(quiz: QuizSession) {
        viewModelScope.launch {
            combine(quiz.state, timerSeconds) { sessionState, seconds ->
                QuizUiMapper.map(sessionState, seconds, shuffledAnswers)
            }.collect { uiModel ->
                updateState { copy(isLoading = false, content = uiModel) }
            }
        }
    }

    private fun handleSelectAnswer(answer: String) {
        val quiz = session ?: return
        if (state.value.content?.isAnswered == true) return
        timerJob?.cancel()
        launch { submitAnswerUseCase(quiz, answer) }
    }

    private fun handleNext() {
        val quiz = session ?: return
        advanceQuestionUseCase(quiz)
        startTimer()
    }

    private fun handleSeeResult() {
        timerJob?.cancel()
        emitEffect(QuizEffect.NavigateToResult)
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerSeconds.value = 15
        timerJob = viewModelScope.launch {
            for (remaining in 14 downTo 0) {
                delay(1000)
                if (state.value.content?.isAnswered == true) return@launch
                timerSeconds.value = remaining
            }
            val quiz = session ?: return@launch
            if (state.value.content?.isAnswered == false) {
                launch { submitAnswerUseCase(quiz, null) }
            }
        }
    }

    override fun onCleared() {
        timerJob?.cancel()
        super.onCleared()
    }

    private fun TriviaError.toUiText(): UiText = UiText.Dynamic(
        when (this) {
            TriviaError.NetworkError -> "Network error. Please check your connection."
            TriviaError.NoResults -> "No questions available for this selection."
            TriviaError.RateLimit -> "Too many requests. Please wait a moment."
            is TriviaError.Unknown -> "Something went wrong."
            TriviaError.InvalidParameter -> "Something went wrong."
        }
    )
}
