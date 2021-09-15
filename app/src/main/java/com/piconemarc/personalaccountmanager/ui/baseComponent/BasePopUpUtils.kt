package com.piconemarc.personalaccountmanager.ui.baseComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

fun Modifier.popUpClickableItemModifier(
    isAmount: Boolean = false,
    amount: Double = 0.00
) = this
    .fillMaxWidth()
    .padding(top = 10.dp, bottom = 10.dp, end = 10.dp)
    .background(
        color = when (isAmount) {
            false -> Color.Black
            true -> if (amount <= 0) {
                Color.Red
            } else Color.Green
        },
        shape = RoundedCornerShape(
            topStart = 0.dp,
            bottomStart = 0.dp,
            topEnd = 20.dp,
            bottomEnd = 20.dp
        )
    )

@Composable
fun PopUpTitle(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black, shape = RoundedCornerShape(20.dp))
    ) {
        Text(
            text = title,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            textAlign = TextAlign.Center
        )
    }
}

val deleteOperationTextModifier = Modifier.padding(horizontal = 0.dp, vertical = 10.dp)
val testList = mutableListOf("test 1", "test 2", "test 3")