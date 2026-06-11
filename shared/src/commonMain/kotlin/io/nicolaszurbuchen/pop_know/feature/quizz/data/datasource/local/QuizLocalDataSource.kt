package io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.local

import io.nicolaszurbuchen.pop_know.core.domain.Category
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.AnsweredQuestionResult
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.TriviaQuestion

interface QuizLocalDataSource {
    fun saveAnswer(gameId: Long, question: TriviaQuestion, selectedAnswer: String?, status: AnswerStatus)
    fun getLastGame(): List<AnsweredQuestionResult>
    fun getCategories(): List<Category>
    fun saveCategories(categories: List<Category>)
}
