package com.example.myapplication.core.di

import android.content.Context

import com.example.myapplication.core.network.ExerciseApi
import com.example.myapplication.features.exercise.data.repositories.ExercisesRepositoryImpl
import com.example.myapplication.features.exercise.domain.repositories.ExerciseRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AppContainer(context: Context) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://exercisedb-api.vercel.app/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val exerciseApi: ExerciseApi by lazy {
        retrofit.create(ExerciseApi::class.java)
    }

    val exerciseRepository: ExerciseRepository by lazy {
        ExercisesRepositoryImpl(exerciseApi)
    }
}

