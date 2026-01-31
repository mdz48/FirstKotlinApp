package com.example.myapplication.features.exercise.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.features.exercise.presentation.viewmodels.ExerciseViewModel
import com.example.myapplication.features.exercise.presentation.viewmodels.ExerciseViewModelFactory
import com.example.myapplication.features.exercise.presentation.components.ExerciseCard

val bodyParts = listOf(
    "back",
    "cardio",
    "chest",
    "lower arms",
    "lower legs",
    "neck",
    "shoulders",
    "upper arms",
    "upper legs",
    "waist"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesScreen(
    factory: ExerciseViewModelFactory
) {

    val viewModel: ExerciseViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Exercises", fontWeight = FontWeight.ExtraBold) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                uiState.error != null -> {
                    Text(
                        text = uiState.error ?: "Error",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Red
                    )
                }
                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = { viewModel.toggleFilter() },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Filter body parts")
                                }

                                if (uiState.selectedBodyPart != null) {
                                    Spacer(Modifier.width(8.dp))
                                    Button(
                                        onClick = { viewModel.clearFilters() }
                                    ) {
                                        Text("Clear")
                                    }
                                }
                            }

                            DropdownMenu(
                                expanded = uiState.isFilterExpanded,
                                onDismissRequest = { viewModel.toggleFilter() },
                                modifier = Modifier.fillMaxWidth(0.8f)
                            ) {
                                bodyParts.forEach { bodyPart ->
                                    DropdownMenuItem(
                                        text = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Checkbox(
                                                    checked = uiState.selectedBodyPart == bodyPart,
                                                    onCheckedChange = {
                                                        viewModel.onBodyPartChecked(bodyPart, it)
                                                    }
                                                )
                                                Spacer(Modifier.width(8.dp))
                                                Text(bodyPart.replaceFirstChar { it.uppercase() })
                                            }
                                        },
                                        onClick = {
                                            val isCurrentlySelected = uiState.selectedBodyPart == bodyPart
                                            viewModel.onBodyPartChecked(bodyPart, !isCurrentlySelected)
                                        }
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = { viewModel.applyFilters() },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Text("Apply filters")
                                }
                            }
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(uiState.exercises) { exercise ->
                                ExerciseCard(
                                    name = exercise.name,
                                    imageUrl = exercise.gifUrl,
                                    instructions = exercise.instructions
                                )
                            }
                        }
                    }
                }
            }
    }
}
}

@Preview
@Composable
fun ExercisesScreenPreview() {
    Text(text = "Exercises Screen Preview")
}
