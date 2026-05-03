package io.nicolaszurbuchen.pop_know.feature.quizz.presentation

import androidx.lifecycle.viewModelScope
import io.nicolaszurbuchen.pop_know.core.domain.TriviaError
import io.nicolaszurbuchen.pop_know.core.presentation.UiText
import io.nicolaszurbuchen.pop_know.core.presentation.mvi.MviViewModel
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.QuestionProgress
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.usecase.SaveAnswerUseCase
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.usecase.StartQuizUseCase
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.QuizContent
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.QuizEffect
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.QuizIntent
import io.nicolaszurbuchen.pop_know.feature.quizz.presentation.model.QuizState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Clock

class QuizViewModel(
    private val startQuizUseCase: StartQuizUseCase,
    private val saveAnswerUseCase: SaveAnswerUseCase,
) : MviViewModel<QuizState, QuizIntent, QuizEffect>(
    initialState = QuizState(isLoading = true),
) {
    private var gameId: Long = 0L
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
            gameId = Clock.System.now().toEpochMilliseconds()
            val session = startQuizUseCase()
            val answers = shuffleAnswers(session.currentQuestion)
            updateState { copy(isLoading = false, content = QuizContent(session, 15, answers)) }
            startTimer()
        }
    }

    private fun handleSelectAnswer(answer: String) {
        val current = state.value.content ?: return
        if (current.session.currentQuestion !is QuestionProgress.Unanswered) return
        timerJob?.cancel()
        val progress = current.session.currentQuestion as QuestionProgress.Unanswered
        val question = progress.question
        val status = if (answer == question.correctAnswer) AnswerStatus.CORRECT else AnswerStatus.INCORRECT
        val updated = current.session.answer(answer, status)
        updateState { copy(content = content?.copy(session = updated)) }
        launch { saveAnswerUseCase(gameId, question, answer, status) }
    }

    private fun handleNext() {
        val current = state.value.content ?: return
        val advanced = current.session.advance()
        val answers = shuffleAnswers(advanced.currentQuestion)
        updateState { copy(content = QuizContent(advanced, 15, answers)) }
        startTimer()
    }

    private fun handleSeeResult() {
        timerJob?.cancel()
        emitEffect(QuizEffect.NavigateToResult)
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            for (remaining in 14 downTo 0) {
                delay(1000)
                val current = state.value.content ?: return@launch
                if (current.session.currentQuestion is QuestionProgress.Answered) return@launch
                updateState { copy(content = content?.copy(timerSeconds = remaining)) }
            }
            val current = state.value.content ?: return@launch
            if (current.session.currentQuestion is QuestionProgress.Unanswered) {
                val progress = current.session.currentQuestion as QuestionProgress.Unanswered
                val question = progress.question
                val timedOut = current.session.timeout()
                updateState { copy(content = content?.copy(session = timedOut, timerSeconds = 0)) }
                launch { saveAnswerUseCase(gameId, question, null, AnswerStatus.TIMEOUT) }
            }
        }
    }

    private fun shuffleAnswers(progress: QuestionProgress): List<String> {
        val question = when (progress) {
            is QuestionProgress.Unanswered -> progress.question
            is QuestionProgress.Answered -> progress.question
        }
        return (question.incorrectAnswers + question.correctAnswer).shuffled()
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
