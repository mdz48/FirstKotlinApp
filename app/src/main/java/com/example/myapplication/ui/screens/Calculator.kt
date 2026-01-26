package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.MyButton

@Composable
fun Calculator() {
    var textVal by remember { mutableStateOf("0") }
    var firstNumber by remember { mutableStateOf(0.0) }
    var pendingOperation by remember { mutableStateOf("") }
    var isNewEntry by remember { mutableStateOf(true) }

    val rows = listOf(
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf("0", ".", "=", "+"),
        listOf("C")
    )

    fun formatResult(value: Double): String {
        return if (value % 1 == 0.0) value.toInt().toString() else value.toString()
    }

    // Lógica matemática pura separada
    fun performCalculation(n1: Double, n2: Double, op: String): Double {
        return when (op) {
            "+" -> n1 + n2
            "-" -> n1 - n2
            "*" -> n1 * n2
            "/" -> if (n2 != 0.0) n1 / n2 else 0.0
            else -> n2
        }
    }

    fun onButtonClick(value: String) {
        when (value) {
            "C" -> {
                textVal = "0"
                firstNumber = 0.0
                pendingOperation = ""
                isNewEntry = true
            }


            // OPERADORES (+, -, *, /)
            "+", "-", "*", "/" -> {
                val currentNumber = textVal.toDoubleOrNull() ?: return

                if (!isNewEntry && pendingOperation.isNotEmpty()) {
                    val result = performCalculation(firstNumber, currentNumber, pendingOperation)
                    firstNumber = result
                    textVal = formatResult(result)
                } else {
                    firstNumber = currentNumber
                }

                pendingOperation = value
                isNewEntry = true
            }


            // IGUAL (=)
            "=" -> {
                val secondNumber = textVal.toDoubleOrNull() ?: return

                if (pendingOperation.isNotEmpty()) {
                    val result = performCalculation(firstNumber, secondNumber, pendingOperation)
                    textVal = formatResult(result)

                    pendingOperation = ""
                    isNewEntry = true
                }
            }


            "." -> {
                if (isNewEntry) {
                    textVal = "0."
                    isNewEntry = false
                } else if (!textVal.contains(".")) {
                    textVal += "."
                }
            }

            // NÚMEROS
            else -> {
                if (isNewEntry) {
                    textVal = value
                    isNewEntry = false
                } else {
                    textVal += value
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.height(20.dp))
        Text(
            text = textVal,
            fontSize = 48.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
        Spacer(Modifier.height(10.dp))
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { buttonText ->
                    Column(modifier = Modifier.weight(1f)) {
                        MyButton(text = buttonText, onClick = { onButtonClick(buttonText) })
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    Calculator()
}