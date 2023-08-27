package com.droi.cp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droi.R
import com.droi.view.MainActivity
import com.droi.viewmodel.MainViewModel

class BaseCompose {
    companion object {
        fun getInstance() = this
    }

    val localColor = compositionLocalOf { Color.Red }

    @Preview(showSystemUi = true)
    @Composable
    fun DefaultPreview2() {
        BoxLayOut()
    }

    @Composable
    fun BoxLayOut() {
        Box(
            contentAlignment = Alignment.CenterEnd,
//            modifier = Modifier.size(width = 400.dp, height = 400.dp).clip(CircleShape).background(Color.Blue),
//            modifier = Modifier.size(width = 400.dp, height = 400.dp).clip(RoundedCornerShape(30.dp)).background(Color.Blue),
            modifier = Modifier.size(width = 400.dp, height = 400.dp).clip(CutCornerShape(30.dp)).background(Color.Blue),
        ) {
            val height = 200.dp
            val width = 200.dp
            Text(text = "top s", modifier = Modifier.align(Alignment.TopStart))
            Text(text = "center", modifier = Modifier.align(Alignment.Center))
            Text(text = "bot e", modifier = Modifier.align(Alignment.BottomEnd))
            BoxTextCell(
                text = "1",
                Modifier.size(width = width, height = height),
            )
            BoxTextCell(
                text = "2",
                Modifier.size(width = width, height = height),
            )
            BoxTextCell(
                text = "3",
                Modifier.size(width = width, height = height),
            )
        }
    }

    @Composable
    fun BoxTextCell(text: String, modifier: Modifier = Modifier, fontSize: Int = 150) {
        val cellModifier = Modifier
            .padding(4.dp)
            .border(width = 5.dp, color = Color.Black)
        Surface {
            Text(
                text = text,
                cellModifier.then(modifier),
                fontSize = fontSize.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
    }

    @Composable
    fun runRowCol() {
        Column {
            Row(
//            horizontalArrangement = Arrangement.SpaceEvenly
//            horizontalArrangement = Arrangement.SpaceBetween
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                TextCell(
                    text = "1",
                    modifier = Modifier.align(Alignment.Top),
                )
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.align(Alignment.CenterVertically),
                ) {
                    TextCell(text = "2")
                    TextCell(text = "3")
                }
                TextCell(
                    text = "3",
                    modifier = Modifier.align(Alignment.Bottom),
                )
            }
            Row {
                Text(
                    text = "large",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.alignByBaseline(),
                )
                Text(
                    text = "small",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.alignByBaseline(),
                )
            }
            Row {
                Text(
                    text = "large",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.alignBy(FirstBaseline),
                )
                Text(
                    text = "small",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.alignBy(FirstBaseline),
                )
            }
            Row {
                Text(
                    text = "large",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.alignBy(FirstBaseline).weight(weight = 0.2f, fill = true),
                )
                Text(
                    text = "small",
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.paddingFrom(
                        alignmentLine = FirstBaseline,
                        before = 0.dp,
                        after = 80.dp,
                    ).weight(weight = 0.4f, fill = true),
                )

                Text(
                    text = "small",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                        .weight(weight = 0.3f, fill = true),
                )
            }
        }
    }

    @Composable
    fun TextCell(text: String, modifier: Modifier = Modifier) {
        val cellModifier = Modifier
            .padding(4.dp)
            .size(100.dp, 100.dp)
            .border(width = 4.dp, color = Color.Black)

        Text(
            text = text,
            cellModifier.then(modifier),
            fontSize = 70.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }

    @Composable
    fun runModifier() {
        val modifier = Modifier
            .padding(all = 10.dp)
            .border(width = 2.dp, color = Color.Black)
            .clip(shape = RoundedCornerShape(30.dp))

        val modifier2 = Modifier
            .padding(all = 10.dp)
            .border(width = 2.dp, color = Color.Black)
            .clip(shape = RoundedCornerShape(30.dp))

        val combinedModifier = modifier.then(modifier2)

        MaterialTheme {
            Column(
                modifier = combinedModifier,
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.jp_jobplanet),
                    contentDescription = "title Image",
                    modifier = modifier,
                )
                CustomSwitch()
                CustomText(
                    text = "go hell",
                    fontWeight = FontWeight.Bold,
                    color = Color.Unspecified,
                    modifier = modifier,
                )
                CustomList(items = listOf("100", "2", "3", "4"))
                Row(
                    modifier = modifier,
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
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
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
    fun CustomText(
        text: String,
        fontWeight: FontWeight,
        color: Color,
        modifier: Modifier = Modifier,
    ) {
        Text(
            modifier = modifier,
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
