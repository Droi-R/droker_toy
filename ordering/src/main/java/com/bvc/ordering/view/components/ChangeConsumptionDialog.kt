package com.bvc.ordering.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bvc.domain.model.ProductEntity
import com.bvc.ordering.R

@Suppress("ktlint:standard:function-naming")
@Composable
@Preview
fun ChangeConsumptionDialogPreview() {
    ChangeConsumptionDialog(
        productEntity = ProductEntity.EMPTY,
        onDismiss = {},
        onConfirm = { stock, unit -> },
    )
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun ChangeConsumptionDialog(
    productEntity: ProductEntity,
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit,
) {
    var stockText by remember { mutableStateOf("${productEntity.stock}") }
    var unit by remember { mutableStateOf("${productEntity.stock}") }
    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier =
                    Modifier
                        .width(dimensionResource(R.dimen.d_33300))
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(dimensionResource(R.dimen.d_2400)),
                        ).padding(dimensionResource(R.dimen.d_2100)),
                // 내부 클릭 시 배경 dismiss 방지
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.materials_detail_consumption),
                        color = colorResource(R.color.bvc_666E89),
                        fontSize = dimensionResource(R.dimen.text_size_16).value.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_close_small),
                        contentDescription = "샘플 이미지",
                        modifier =
                            Modifier
                                .wrapContentSize()
                                .clickable {
                                    onDismiss()
                                },
                        // 크기 조정
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.d_3400)))

                Text(
                    text = "'${productEntity.stock}'를\n'${productEntity.stock}${productEntity.stock}'보유하고 계신것으로\n계산됩니다. 아니실까요",
                    color = colorResource(R.color.bvc_666E89),
                    fontSize = dimensionResource(R.dimen.text_size_16).value.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.d_1500)))
                Text(
                    text = stringResource(R.string.materials_detail_consumption),
                    color = colorResource(R.color.bvc_666E89),
                    fontSize = dimensionResource(R.dimen.text_size_12).value.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start, // 왼쪽 정렬
                    modifier = Modifier.fillMaxWidth(), // 텍스트가 전체 너비를 차지하도록 설정
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.d_1300)))
                TextField(
                    value = stockText,
                    onValueChange = { newText ->
                        if (newText.all { it.isDigit() }) {
                            stockText = newText
                        }
                    },
                    singleLine = true,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(dimensionResource(R.dimen.d_3200))) // 라운드 모양 적용
                            .background(colorResource(id = R.color.bvc_f6f6f6)) // 배경색 설정
                            .padding(
                                horizontal = 4.dp,
                                vertical = 0.dp,
                            ),
                    colors =
                        TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent, // 내부 배경 투명 처리
                            unfocusedContainerColor = Color.Transparent, // 내부 배경 투명 처리
                            focusedIndicatorColor = Color.Transparent, // 하단 강조선 제거
                            unfocusedIndicatorColor = Color.Transparent, // 하단 강조선 제거
                        ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    textStyle =
                        androidx.compose.ui.text.TextStyle(
                            fontSize = dimensionResource(R.dimen.text_size_14).value.sp, // 텍스트 크기 설정
                        ),
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.d_2900)))
                Text(
                    text = stringResource(R.string.materials_detail_unit),
                    color = colorResource(R.color.bvc_666E89),
                    fontSize = dimensionResource(R.dimen.text_size_12).value.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start, // 왼쪽 정렬
                    modifier = Modifier.fillMaxWidth(), // 텍스트가 전체 너비를 차지하도록 설정
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.d_1300)))
                TextField(
                    value = unit,
                    onValueChange = { newText ->
                        if (newText.all { it.isDigit() }) {
                            unit = newText
                        }
                    },
                    singleLine = true,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(dimensionResource(R.dimen.d_3200))) // 라운드 모양 적용
                            .background(colorResource(id = R.color.bvc_f6f6f6)) // 배경색 설정
                            .padding(
                                horizontal = 4.dp,
                                vertical = 0.dp,
                            ),
                    colors =
                        TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent, // 내부 배경 투명 처리
                            unfocusedContainerColor = Color.Transparent, // 내부 배경 투명 처리
                            focusedIndicatorColor = Color.Transparent, // 하단 강조선 제거
                            unfocusedIndicatorColor = Color.Transparent, // 하단 강조선 제거
                        ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    textStyle =
                        androidx.compose.ui.text.TextStyle(
                            fontSize = dimensionResource(R.dimen.text_size_14).value.sp, // 텍스트 크기 설정
                        ),
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.d_2900)))

                Button(
                    onClick = {
                        val stock = stockText.toIntOrNull() ?: productEntity.stock
                        val unit = unit.toIntOrNull() ?: productEntity.stock
                        onConfirm(stock, unit)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.bvc_main_color), // 원하는 배경 색상 설정
                        ),
                ) {
                    Text(
                        text = "저장",
                        fontSize = dimensionResource(R.dimen.text_size_16).value.sp,
                    )
                }
            }
        }
    }
}
