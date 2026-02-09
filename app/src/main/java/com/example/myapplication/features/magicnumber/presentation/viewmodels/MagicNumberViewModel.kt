package com.example.myapplication.features.magicnumber.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myapplication.features.magicnumber.presentation.screens.FeedbackType
import com.example.myapplication.features.magicnumber.presentation.screens.MagicNumberEvent
import com.example.myapplication.features.magicnumber.presentation.screens.MagicNumberEventType
import com.example.myapplication.features.magicnumber.presentation.screens.MagicNumberUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.abs
import kotlin.random.Random

class MagicNumberViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MagicNumberUIState())
    val uiState: StateFlow<MagicNumberUIState> = _uiState.asStateFlow()

    init {
        startNewGame()
    }

    fun onEvent(event: MagicNumberEvent) {
        when (event.type) {
            MagicNumberEventType.START_NEW_GAME -> startNewGame()
            MagicNumberEventType.UPDATE_GUESS -> updateGuess(event.guessValue)
            MagicNumberEventType.SUBMIT_GUESS -> submitGuess()
        }
    }

    private fun startNewGame() {
        val secretNumber = Random.nextInt(1, 101)
        _uiState.update {
            MagicNumberUIState(
                secretNumber = secretNumber,
                feedback = "¡Adivina el número entre 1 y 100!"
            )
        }
    }

    private fun updateGuess(guess: String) {
        _uiState.update { it.copy(userGuess = guess) }
    }

    private fun submitGuess() {
        val currentState = _uiState.value
        val guessNumber = currentState.userGuess.toIntOrNull()

        if (guessNumber == null || guessNumber !in 1..100) {
            _uiState.update {
                it.copy(
                    feedback = "Por favor, ingresa un número entre 1 y 100",
                    feedbackType = FeedbackType.NEUTRAL
                )
            }
            return
        }

        val distance = abs(currentState.secretNumber - guessNumber)
        val (feedback, feedbackType) = generateFeedback(guessNumber, currentState.secretNumber, distance, currentState.lastDistance)

        if (guessNumber == currentState.secretNumber) {
            _uiState.update {
                it.copy(
                    feedback = feedback,
                    feedbackType = feedbackType,
                    isGameWon = true,
                    userGuess = ""
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    feedback = feedback,
                    feedbackType = feedbackType,
                    userGuess = "",
                    lastDistance = distance
                )
            }
        }
    }

    private fun generateFeedback(guess: Int, secret: Int, distance: Int, lastDistance: Int): Pair<String, FeedbackType> {
        if (guess == secret) {
            return "¡Correcto! El número era $secret" to FeedbackType.WIN
        }

        val isGettingCloser = lastDistance != Int.MAX_VALUE && distance < lastDistance
        val isGettingFarther = lastDistance != Int.MAX_VALUE && distance > lastDistance

        val proximityMessage = when {
            distance <= 3 -> "CALIENTE Muy cerca" to FeedbackType.VERY_HOT
            distance <= 10 -> "Caliente Te estás acercando" to FeedbackType.HOT
            distance <= 20 -> "Tibio, vas por buen camino" to FeedbackType.WARM
            else -> "Frío, estás lejos" to FeedbackType.COLD
        }

        val directionHint = when {
            guess < secret -> "El número es MAYOR"
            else -> "El número es MENOR"
        }

        val trendMessage = when {
            isGettingCloser && distance <= 10 -> " Te estas acercando"
            isGettingFarther -> " Te estás alejando"
            else -> ""
        }

        return "${proximityMessage.first}\n$directionHint$trendMessage" to proximityMessage.second
    }
}
