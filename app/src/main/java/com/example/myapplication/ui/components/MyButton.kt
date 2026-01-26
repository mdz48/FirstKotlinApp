package com.example.myapplication.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import org.w3c.dom.Text

@Composable
fun MyButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = text, fontSize = 24.sp)
    }
}

@Preview
@Composable
fun MyButtonPreview() {
    MyButton(text = "Hola", onClick = {})
}