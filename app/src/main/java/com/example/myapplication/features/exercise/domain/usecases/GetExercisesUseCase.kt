package com.example.myapplication.features.exercise.domain.usecases

import com.example.myapplication.features.exercise.domain.repositories.ExerciseRepository
import com.example.myapplication.features.exercise.domain.entities.Exercise

class GetExercisesUseCase(
    private val repository: ExerciseRepository
) {

    suspend operator fun invoke(bodyPart: String? = null): Result<List<Exercise>> {
        return try {
            val exercises = if (bodyPart != null) {
                repository.getExercisesByBodyPart(bodyPart)
            } else {
                repository.getExercises()
            }

            val filteredCharacters = exercises.filter { it.name.isNotBlank() }

            if (filteredCharacters.isEmpty()) {
                Result.failure(Exception("No se encontraron ejercicios válidos"))
            } else {
                Result.success(filteredCharacters)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}