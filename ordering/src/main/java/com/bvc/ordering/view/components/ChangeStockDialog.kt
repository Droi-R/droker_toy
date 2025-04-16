package com.bvc.ordering.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType

@Suppress("ktlint:standard:function-naming")
@Composable
fun ChangeStockDialog(
    currentStock: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
) {
    var stock by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        stock = currentStock.toString()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("보유 수량 변경") },
        text = {
            Column {
                Text("새로운 수량을 입력하세요.")
                OutlinedTextField(
                    value = stock,
                    onValueChange = { stock = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                stock.toIntOrNull()?.let(onConfirm)
                onDismiss()
            }) {
                Text("확인")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        },
    )
}
