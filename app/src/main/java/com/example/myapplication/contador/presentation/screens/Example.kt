package com.example.myapplication.contador.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Example(viewModelExample : ExampleViewModel = viewModel()) {
    val textVal = viewModelExample.count.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "${textVal.value}",
            fontSize = 24.sp
        )
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {viewModelExample.increment()}, Modifier.weight(1f).padding(10.dp)) {
                Text("+", fontSize = 24.sp)
            }
            Button(onClick = {viewModelExample.decrement()}, Modifier.weight(1f).padding(10.dp)) {
                Text("-", fontSize = 24.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExamplePreview(){
    Example()
}