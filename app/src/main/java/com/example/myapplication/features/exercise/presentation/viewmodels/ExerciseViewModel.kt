package com.example.myapplication.features.exercise.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.features.exercise.domain.usecases.GetExercisesUseCase
import com.example.myapplication.features.exercise.domain.usecases.GetExercisesByBodyPartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.myapplication.features.exercise.presentation.screens.ExercisesUiState
import kotlinx.coroutines.flow.update

class ExerciseViewModel(
    private val getExercisesUseCase: GetExercisesUseCase,
    private val getExercisesByBodyPartUseCase: GetExercisesByBodyPartUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExercisesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadExercises()
    }

    private fun loadExercises() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = getExercisesUseCase()
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { list ->
                        currentState.copy(isLoading = false, exercises = list)
                    },
                    onFailure = { error ->
                        currentState.copy(isLoading = false, error = error.message)
                    }
                )
            }
        }
    }

    private fun loadExercisesByBodyPart(bodyPart: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = getExercisesByBodyPartUseCase(bodyPart)
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { list ->
                        currentState.copy(isLoading = false, exercises = list)
                    },
                    onFailure = { error ->
                        currentState.copy(isLoading = false, error = error.message)
                    }
                )
            }
        }
    }

    fun toggleFilter() {
        _uiState.update { it.copy(isFilterExpanded = !it.isFilterExpanded) }
    }

    fun onBodyPartChecked(bodyPart: String, isChecked: Boolean) {
        _uiState.update {
            it.copy(
                selectedBodyPart = if (isChecked) bodyPart else null
            )
        }
    }

    fun applyFilters() {
        val selectedBodyPart = _uiState.value.selectedBodyPart
        _uiState.update { it.copy(isFilterExpanded = false) }

        if (selectedBodyPart != null) {
            loadExercisesByBodyPart(selectedBodyPart)
        } else {
            loadExercises()
        }
    }

    fun clearFilters() {
        _uiState.update { it.copy(selectedBodyPart = null) }
        loadExercises()
    }
}