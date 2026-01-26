package com.example.myapplication.ahorcado.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.ahorcado.domain.usecases.InitGameUseCase

class AhorcadoScreenViewModel : ViewModel() {
    private val initGameUseCase: InitGameUseCase = InitGameUseCase()

    companion object {
        const val MAX_ERRORS = 6
    }

    // Estado del juego
    var secretWord by mutableStateOf("")
        private set

    var usedLetters by mutableStateOf(setOf<Char>())
        private set

    var errors by mutableIntStateOf(0)
        private set

    var gameState by mutableStateOf(GameState.IN_GAME)
        private set

    enum class GameState {
        IN_GAME, WIN, LOST
    }

    init {
        initGame()
    }

    fun initGame() {
        secretWord = initGameUseCase.getRandomWord()
        usedLetters = emptySet()
        errors = 0
        gameState = GameState.IN_GAME
    }

    // Palabra mostrada con guiones bajos para letras no adivinadas
    fun getDisplayWord(): String {
        return secretWord.map { char ->
            if (char in usedLetters) char else '_'
        }.joinToString(" ")
    }

    // Verificar si una letra está en la palabra
    fun guessLetter(letter: Char) {
        if (gameState != GameState.IN_GAME) return
        if (letter in usedLetters) return

        usedLetters = usedLetters + letter

        if (letter !in secretWord) {
            errors++
            if (errors >= MAX_ERRORS) {
                gameState = GameState.LOST
            }
        } else {
            // Verificar si ganó
            val allLettersGuessed = secretWord.all { it in usedLetters }
            if (allLettersGuessed) {
                gameState = GameState.WIN
            }
        }
    }
}