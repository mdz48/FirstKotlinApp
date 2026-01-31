package com.example.myapplication.features.exercise.di

import com.example.myapplication.core.di.AppContainer
import com.example.myapplication.features.exercise.domain.usecases.GetExercisesUseCase
import com.example.myapplication.features.exercise.presentation.viewmodels.ExerciseViewModelFactory


class ExercisesModule(
    private val appContainer: AppContainer) {

    private fun provideGetExerciesUseCase(): GetExercisesUseCase {
        return GetExercisesUseCase(appContainer.exerciseRepository)
    }

    fun provideExercisesViewModelFactory(): ExerciseViewModelFactory {
        return ExerciseViewModelFactory(
            getExercisesUseCase = provideGetExerciesUseCase()
        )
    }
}
