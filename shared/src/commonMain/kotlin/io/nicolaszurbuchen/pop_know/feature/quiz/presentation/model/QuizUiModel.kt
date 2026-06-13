package io.nicolaszurbuchen.pop_know.feature.quiz.presentation.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.nicolaszurbuchen.pop_know.common.domain.Difficulty
import io.nicolaszurbuchen.pop_know.infra.design.theme.popKnowGameColors

data class QuizUiModel(
    val questionText: String,
    val categoryText: String,
    val difficulty: Difficulty,
    val progressText: String,
    val scoreText: String,
    val choices: List<QuizChoiceUiModel>,
    val resultChoice: QuizChoiceUiModel?,
    val totalQuestions: Int,
    val currentIndex: Int,
    val isAnswered: Boolean,
    val isLastQuestion: Boolean,
    val timerSeconds: Int,
    val maxTimerSeconds: Int,
) {
    @Composable
    fun difficultyColor(): Color = when (difficulty) {
        Difficulty.EASY -> MaterialTheme.popKnowGameColors.difficultyEasy
        Difficulty.MEDIUM -> MaterialTheme.popKnowGameColors.difficultyMedium
        Difficulty.HARD -> MaterialTheme.popKnowGameColors.difficultyHard
    }

    fun difficultyName(): String = difficulty.name
}
