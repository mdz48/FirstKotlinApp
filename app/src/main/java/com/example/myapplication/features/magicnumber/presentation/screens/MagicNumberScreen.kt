package com.example.myapplication.features.magicnumber.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.features.magicnumber.presentation.viewmodels.MagicNumberViewModel
import com.example.myapplication.features.magicnumber.presentation.viewmodels.MagicNumberViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MagicNumberScreen(
    viewModel: MagicNumberViewModel = viewModel(factory = MagicNumberViewModelFactory())
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adivina el Número") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            FeedbackDisplay(
                feedback = uiState.feedback,
                isGameWon = uiState.isGameWon
            )

            if (!uiState.isGameWon) {
                OutlinedTextField(
                    value = uiState.userGuess,
                    onValueChange = {
                        if (it.length <= 3) {
                            viewModel.onEvent(MagicNumberEvent(
                                type = MagicNumberEventType.UPDATE_GUESS,
                                guessValue = it
                            ))
                        }
                    },
                    label = { Text("Tu número") },
                    placeholder = { Text("1-100") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.onEvent(MagicNumberEvent(type = MagicNumberEventType.SUBMIT_GUESS))
                        }
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        viewModel.onEvent(MagicNumberEvent(type = MagicNumberEventType.SUBMIT_GUESS))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Adivinar", fontSize = 18.sp)
                }
            } else {
                Button(
                    onClick = {
                        viewModel.onEvent(MagicNumberEvent(type = MagicNumberEventType.START_NEW_GAME))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Jugar de Nuevo")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun FeedbackDisplay(
    feedback: String,
    isGameWon: Boolean
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        colors = CardDefaults.cardColors(
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = feedback,
                fontSize = if (isGameWon) 24.sp else 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
