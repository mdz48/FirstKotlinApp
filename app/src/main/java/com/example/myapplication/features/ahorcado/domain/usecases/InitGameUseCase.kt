package com.example.myapplication.features.ahorcado.domain.usecases

class InitGameUseCase {

    private val words = listOf(
        "UP", "SUCHIAPA", "MEXICO", "AMOR", "PROGRAMACION",
        "DESARROLLO", "ANDROID", "KOTLIN", "COMPOSE"
    )

    fun getRandomWord(): String {
        return words.random()
    }

    fun initRandomWord(word: String): List<Char> {
        return List(word.length) { '_' }
    }

    fun getMaxWordLength(): Int {
        return words.maxOfOrNull { it.length } ?: 10
    }

}