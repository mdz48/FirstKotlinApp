package com.example.myapplication.features.exercise.presentation.screens

import com.example.myapplication.features.exercise.domain.entities.Exercise

data class ExercisesUiState(
    val isLoading: Boolean = false,
    val exercises: List<Exercise> = emptyList(),
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val isFilterExpanded: Boolean = false,
    val selectedBodyPart: String? = null
)