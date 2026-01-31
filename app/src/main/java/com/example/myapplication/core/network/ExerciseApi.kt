package com.example.myapplication.core.network

import com.example.myapplication.features.exercise.data.datasources.remote.model.ExercisesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseApi {

    @GET("exercises")
    suspend fun getExercises(
        @Query("limit") limit: Int
    ): ExercisesResponse

    @GET("exercises/filter")
    suspend fun getExercisesByBodyPart(
        @Query("limit") limit: Int,
        @Query("bodyParts") bodyPart: String
    ): ExercisesResponse

}