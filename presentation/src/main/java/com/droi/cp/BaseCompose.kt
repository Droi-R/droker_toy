package com.droi.cp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droi.view.MainActivity
import com.droi.viewmodel.MainViewModel

class BaseCompose {
    companion object {
        fun getInstance() = this
    }

    val localColor = compositionLocalOf { Color.Red }

    @Preview(showBackground = true)
    @Composable
    fun defaultPreview2() {
        MaterialTheme {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CustomSwitch()
                CustomText(
                    text = "go hell",
                    fontWeight = FontWeight.Bold,
                    color = Color.Unspecified,
                )
                CustomList(items = listOf("100", "2", "3", "4"))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    SlotDemo(
                        topContent = { ButtonDemo() },
                        middleContent = { ButtonDemo() },
                        botContent = { ButtonDemo() },
                    )
                }
            }
        }
    }

    @Composable
    fun SlotDemo(
        topContent: @Composable () -> Unit,
        middleContent: @Composable () -> Unit,
        botContent: @Composable () -> Unit,
    ) {
        Column {
            topContent()
            Text(text = "top text")
            middleContent()
            Text(text = "Bot text")
            middleContent()
        }
    }

    @Composable
    fun ButtonDemo() {
        Button(onClick = { }) {
            Text(text = "Click ME")
        }
    }

    @Composable
    fun DemoScreen(activity: MainActivity, model: MainViewModel) {
        var textstate by remember { mutableStateOf("") }
        var text by remember { mutableStateOf("") }
        var text2 by remember { mutableStateOf("") }

//        val staticLocalColor = staticCompositionLocalOf { Color.Red }
        val color = Color.Blue
        val color2 = Color.Black

        model.customTimerDuration.observe(activity) {
            text = "${model.customTimerDuration.value}"
            text2 = "${model.oldTimeMills}"
        }

        val onTextChanged = { text: String ->
            textstate = text
        }
        CompositionLocalProvider(localColor provides color) {
            Column {
                MyTextFiled(textstate, onTextChanged)
                MyTextView(text = text)
                MyTextView(text = text2)
            }
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
    fun MyFuntion() {
        Text(text = "hello")
    }

    @Composable
    fun CustomText(text: String, fontWeight: FontWeight, color: Color) {
        Text(
            text = text,
            fontWeight = fontWeight,
            color = color,
        )
    }

//    @Preview(showBackground = true)
//    @Composable
//    fun defaultPreview1() {
// //        customText(text = "go hell", fontWeight = FontWeight.Bold, color = Color.Unspecified)
// //        myFuntion()
//        customSwitch()
//    }

    @Composable
    fun CustomSwitch() {
        val checked = remember { mutableStateOf(true) }
        Column {
            Switch(
                checked = checked.value,
                onCheckedChange = { checked.value = it },
            )
            if (checked.value) {
                Text(text = "on")
            } else {
                Text(text = "off")
            }
        }
    }

    @Composable
    fun CustomList(items: List<String>) {
        Column {
            for (item in items) {
                Text(item)
                Divider(color = Color.Black)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyTextFiled(textstate: String, onTextChanged: (String) -> Unit) {
        TextField(
            value = textstate,
            onValueChange = onTextChanged,
        )
    }

    @Composable
    fun MyTextView(text: String) {
        Text(
            text = text,
            modifier = Modifier.background(localColor.current),
        )
    }

    @Composable
    fun FuntionA() {
        var switchState by remember { mutableStateOf(false) }
        val onSwitChChange = { value: Boolean ->
            switchState = value
        }
        FuntionB(switchState = switchState, onSwitChChange = onSwitChChange)
    }

    @Composable
    fun FuntionB(switchState: Boolean, onSwitChChange: (Boolean) -> Unit) {
        Switch(
            checked = switchState,
            onCheckedChange = onSwitChChange,
        )
    }
}
