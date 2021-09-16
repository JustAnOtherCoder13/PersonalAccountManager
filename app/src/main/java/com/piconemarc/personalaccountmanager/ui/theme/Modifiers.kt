package com.piconemarc.personalaccountmanager.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import java.lang.NumberFormatException

fun Modifier.deleteOperationTextModifier() = this.padding(vertical = RegularMarge)

fun Modifier.popUpClickableItemModifier() = composed {
    this
        .fillMaxWidth()
        .padding(top = RegularMarge, bottom = RegularMarge, end = RegularMarge)
        .background(
            color = MaterialTheme.colors.primary,
            shape = PopUpFieldBackgroundShape
        )
}

fun Modifier.popUpAmountItemModifier(amount: String) = this
    .fillMaxWidth()
    .padding(top = RegularMarge, bottom = RegularMarge, end = RegularMarge)
    .background(
        color = try {
            if (amount.toDouble() <= 0) Color.Red else Color.Green
        } catch (exception: NumberFormatException) {
            println(exception)
            Color.Red
        },
        shape = PopUpFieldBackgroundShape
    )