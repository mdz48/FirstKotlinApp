package com.example.myapplication.features.exercise.domain.repositories
import com.example.myapplication.features.exercise.domain.entities.Exercise


interface ExerciseRepository {
    suspend fun getExercises(): List<Exercise>
    suspend fun getExercisesByBodyPart(bodyPart: String): List<Exercise>
}