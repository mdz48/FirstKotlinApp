package com.example.myapplication.features.magicnumber.presentation.screens

data class MagicNumberUIState(
    val secretNumber: Int = 0,
    val userGuess: String = "",
    val feedback: String = "¡Adivina el número entre 1 y 100!",
    val isGameWon: Boolean = false,
    val feedbackType: FeedbackType = FeedbackType.NEUTRAL,
    val lastDistance: Int = Int.MAX_VALUE
)

enum class FeedbackType {
    NEUTRAL, COLD, WARM, HOT, VERY_HOT, WIN
}

enum class MagicNumberEventType {
    START_NEW_GAME,
    UPDATE_GUESS,
    SUBMIT_GUESS
}

data class MagicNumberEvent(
    val type: MagicNumberEventType,
    val guessValue: String = ""
)
