package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.core.di.AppContainer
import com.example.myapplication.features.contador.presentation.screens.Example
import com.example.myapplication.features.exercise.di.ExercisesModule
import com.example.myapplication.features.exercise.presentation.screens.ExercisesScreen
import com.example.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
     lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppContainer(this)
        val exerciseModule = ExercisesModule(appContainer)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                ExercisesScreen(exerciseModule.provideExercisesViewModelFactory())
            }
        }
    }
}
