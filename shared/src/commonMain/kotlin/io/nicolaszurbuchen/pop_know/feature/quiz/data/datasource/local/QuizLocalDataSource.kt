package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local

import io.nicolaszurbuchen.pop_know.common.domain.Category
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnsweredQuestionResult
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion

interface QuizLocalDataSource {
    fun saveAnswer(gameId: Long, question: TriviaQuestion, selectedAnswer: String?, status: AnswerStatus)
    fun getLastGame(): List<AnsweredQuestionResult>
    fun getCategories(): List<Category>
    fun saveCategories(categories: List<Category>)
}
