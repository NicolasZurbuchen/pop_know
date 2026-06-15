package io.nicolaszurbuchen.pop_know.feature.quiz.data.datasource.local

import io.nicolaszurbuchen.pop_know.cache.CategoryQueries
import io.nicolaszurbuchen.pop_know.cache.QuestionHistoryQueries
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Category
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.Difficulty
import io.nicolaszurbuchen.pop_know.common.trivia.domain.model.QuestionType
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnswerStatus
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.AnsweredQuestionResult
import io.nicolaszurbuchen.pop_know.feature.quiz.domain.model.TriviaQuestion
import kotlin.time.Clock

class QuizLocalDataSourceImpl(
    private val questionHistoryQueries: QuestionHistoryQueries,
    private val categoryQueries: CategoryQueries,
) : QuizLocalDataSource {

    override fun saveAnswer(
        gameId: Long,
        question: TriviaQuestion,
        selectedAnswer: String?,
        status: AnswerStatus,
    ) {
        questionHistoryQueries.insertHistory(
            game_id = gameId,
            type = question.questionType.toDbString(),
            difficulty = question.difficulty.toDbString(),
            category_id = question.category.id.toLong(),
            question = question.question,
            correct_answer = question.correctAnswer,
            incorrect_answers = question.incorrectAnswers,
            selected_answer = selectedAnswer,
            status = status.toDbString(),
            answered_at = Clock.System.now().toEpochMilliseconds(),
        )
    }

    override fun getLastGame(): List<AnsweredQuestionResult> {
        return questionHistoryQueries.getLastGame { _, _, _, difficulty,
            question, correct_answer, _, selected_answer, status, _, category_name ->
            AnsweredQuestionResult(
                question = question,
                correctAnswer = correct_answer,
                selectedAnswer = selected_answer,
                status = status.toAnswerStatus(),
                categoryName = category_name,
                difficulty = difficulty.toDifficulty(),
            )
        }.executeAsList()
    }

    override fun getCategories(): List<io.nicolaszurbuchen.pop_know.cache.Category> {
        return categoryQueries.getAllCategories().executeAsList()
    }

    override fun saveCategories(categories: List<Category>) {
        categories.forEach { category ->
            categoryQueries.insertCategory(id = category.id.toLong(), name = category.category)
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

    private fun AnswerStatus.toDbString(): String = when (this) {
        AnswerStatus.CORRECT -> "CORRECT"
        AnswerStatus.INCORRECT -> "INCORRECT"
        AnswerStatus.TIMEOUT -> "TIMEOUT"
    }

    private fun String.toAnswerStatus(): AnswerStatus = when (this) {
        "CORRECT" -> AnswerStatus.CORRECT
        "INCORRECT" -> AnswerStatus.INCORRECT
        else -> AnswerStatus.TIMEOUT
    }

    private fun String.toDifficulty(): Difficulty = when (this) {
        "easy" -> Difficulty.EASY
        "medium" -> Difficulty.MEDIUM
        else -> Difficulty.HARD
    }
}
