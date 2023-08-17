package com.droi.cp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class BaseCompose {

    @Composable
    fun DemoScreen() {
        var sliderPosition by remember {
            mutableStateOf(20f)
        }
    }

    @Composable
    fun DemoSlider(sliderPositions: Float, onPositionChange: (Float) -> Unit) {
        Slider(
            modifier = Modifier.padding(10.dp),
            valueRange = 10f..40f,
            value = sliderPositions,
            onValueChange = onPositionChange,
        )
    }

    @Composable
    fun myFuntion() {
        Text(text = "hello")
    }

    @Composable
    fun customText(text: String, fontWeight: FontWeight, color: Color) {
        Text(
            text = text,
            fontWeight = fontWeight,
            color = color,
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun defaultPreview() {
        customText(text = "go hell", fontWeight = FontWeight.Bold, color = Color.Unspecified)
    }
}
