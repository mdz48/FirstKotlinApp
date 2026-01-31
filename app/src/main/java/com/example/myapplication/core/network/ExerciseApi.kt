package com.example.myapplication.core.network

import com.example.myapplication.features.exercise.data.datasources.remote.model.ExercisesResponse
import retrofit2.http.GET

interface ExerciseApi {

    @GET("exercises")
    suspend fun getExercises(): ExercisesResponse
}