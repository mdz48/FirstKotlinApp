package com.example.myapplication.features.exercise.data.datasources.remote.mapper

import com.example.myapplication.features.exercise.data.datasources.remote.model.ExercisesDto
import com.example.myapplication.features.exercise.domain.entities.Exercise
import kotlin.String

fun ExercisesDto.toDomain(): Exercise {
    return Exercise(
        exerciseId = this.exerciseId,
        name = this.name,
        gifUrl = this.gifUrl,
        targetMuscles  = this.targetMuscles,
        instructions = this.instructions
    )
}