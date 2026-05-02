package io.nicolaszurbuchen.pop_know.feature.quizz.data.datasource.local

import io.nicolaszurbuchen.pop_know.cache.QuestionHistoryQueries
import io.nicolaszurbuchen.pop_know.core.domain.Difficulty
import io.nicolaszurbuchen.pop_know.core.domain.QuestionType
import io.nicolaszurbuchen.pop_know.feature.quizz.domain.model.TriviaQuestion

class QuizLocalDataSourceImpl(
    private val queries: QuestionHistoryQueries,
) : QuizLocalDataSource {

    override fun saveQuestions(questions: List<Pair<TriviaQuestion, String>>, answeredAt: Long) {
        queries.transaction {
            questions.forEach { (question, selectedAnswer) ->
                queries.insertHistory(
                    type = question.questionType.toDbString(),
                    difficulty = question.difficulty.toDbString(),
                    category_id = question.category.id.toLong(),
                    question = question.question,
                    correct_answer = question.correctAnswer,
                    incorrect_answers = question.incorrectAnswers,
                    selected_answer = selectedAnswer,
                    answered_at = answeredAt,
                )
            }
        }
    }

    private fun QuestionType.toDbString(): String = when (this) {
        QuestionType.MULTIPLE -> "multiple"
        QuestionType.BOOLEAN -> "boolean"
    }

    private fun Difficulty.toDbString(): String = when (this) {
        Difficulty.EASY -> "easy"
        Difficulty.MEDIUM -> "medium"
        Difficulty.HARD -> "hard"
    }
}
