package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.myapplication.core.di.AppContainer
import com.example.myapplication.features.contador.presentation.screens.Example
import com.example.myapplication.features.exercise.di.ExercisesModule
import com.example.myapplication.features.exercise.presentation.screens.ExercisesScreen
import com.example.myapplication.features.magicnumber.presentation.screens.MagicNumberScreen
import com.example.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
     lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppContainer(this)
//        val exerciseModule = ExercisesModule(appContainer)
        enableEdgeToEdge()
        setContent {
            Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MagicNumberScreen()
                }
        }
    }
}
