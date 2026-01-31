package com.example.myapplication.features.exercise.data.repositories

import com.example.myapplication.core.network.ExerciseApi
import com.example.myapplication.features.exercise.data.datasources.remote.mapper.toDomain
import com.example.myapplication.features.exercise.domain.entities.Exercise
import com.example.myapplication.features.exercise.domain.repositories.ExerciseRepository

class ExercisesRepositoryImpl(
    private val api: ExerciseApi
) : ExerciseRepository {

    override suspend fun getExercises(): List<Exercise> {
        val response = api.getExercises()
        return response.data.map { it.toDomain() }
    }
}