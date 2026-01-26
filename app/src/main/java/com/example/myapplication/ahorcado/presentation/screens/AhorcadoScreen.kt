package com.example.myapplication.ahorcado.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AhorcadoScreen(viewModel: AhorcadoScreenViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "Juego del Ahorcado",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Dibujo del ahorcado
        Dibujo(errors = viewModel.errors)

        Spacer(modifier = Modifier.height(20.dp))

        // Palabra oculta
        Text(
            text = viewModel.getDisplayWord(),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 4.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Errores
        Text(
            text = "Errores: ${viewModel.errors}/6",
            fontSize = 18.sp,
            color = if (viewModel.errors >= 4) Color.Red else Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Mensaje de estado del juego
        when (viewModel.gameState) {
            AhorcadoScreenViewModel.GameState.WIN -> {
                Text(
                    text = "¡GANASTE!",
                    fontSize = 24.sp,
                    color = Color.Green,
                    fontWeight = FontWeight.Bold
                )
            }
            AhorcadoScreenViewModel.GameState.LOST -> {
                Text(
                    text = "¡PERDISTE! \nLa palabra era: ${viewModel.secretWord}",
                    fontSize = 20.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            else -> {}
        }

        Spacer(modifier = Modifier.weight(1f))

        // Teclado de letras (organizado en filas)
        if (viewModel.gameState == AhorcadoScreenViewModel.GameState.IN_GAME) {
            Teclado(
                usedLetters = viewModel.usedLetters,
                onLetterClick = { viewModel.guessLetter(it) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón reiniciar
        if (viewModel.gameState != AhorcadoScreenViewModel.GameState.IN_GAME) {
            Button(
                onClick = { viewModel.initGame() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Jugar de nuevo", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun Teclado(usedLetters: Set<Char>, onLetterClick: (Char) -> Unit) {
    val fila1 = "QWERTYUIOP".toList()
    val fila2 = "ASDFGHJKL".toList()
    val fila3 = "ZXCVBNM".toList()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        FilaTeclado(letras = fila1, usedLetters = usedLetters, onLetterClick = onLetterClick)
        FilaTeclado(letras = fila2, usedLetters = usedLetters, onLetterClick = onLetterClick)
        FilaTeclado(letras = fila3, usedLetters = usedLetters, onLetterClick = onLetterClick)
    }
}

@Composable
fun FilaTeclado(letras: List<Char>, usedLetters: Set<Char>, onLetterClick: (Char) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        letras.forEach { letter ->
            val isUsed = letter in usedLetters
            Button(
                onClick = { onLetterClick(letter) },
                enabled = !isUsed,
                modifier = Modifier
                    .padding(2.dp)
                    .size(35.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isUsed) Color.Gray else MaterialTheme.colorScheme.primary,
                    disabledContainerColor = Color.LightGray
                ),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
            ) {
                Text(
                    text = letter.toString(),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun Dibujo(errors: Int) {
    val cuerpoCompleto = listOf(
        "  _____ ",
        " |     |",
        " |     O",    // cabeza (error 1)
        " |    /|\\",  // cuerpo y brazos (error 2, 3, 4)
        " |    / \\",  // piernas (error 5, 6)
        "_|_      "
    )

    Box(
        modifier = Modifier
            .background(Color.LightGray.copy(alpha = 0.3f))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Base siempre visible
            Text(text = cuerpoCompleto[0], fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            Text(text = cuerpoCompleto[1], fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)

            // Cabeza (error >= 1)
            if (errors >= 1) {
                Text(text = cuerpoCompleto[2], fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            } else {
                Text(text = " |      ", fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
            }

            // Cuerpo (error >= 2), brazos (error >= 3, 4)
            val cuerpo = when {
                errors >= 4 -> " |    /|\\"
                errors >= 3 -> " |    /| "
                errors >= 2 -> " |     | "
                else -> " |      "
            }
            Text(text = cuerpo, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)

            // Piernas (error >= 5, 6)
            val piernas = when {
                errors >= 6 -> " |    / \\"
                errors >= 5 -> " |    /  "
                else -> " |      "
            }
            Text(text = piernas, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)

            Text(text = cuerpoCompleto[5], fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AhorcadoScreenPreview() {
    AhorcadoScreen()
}