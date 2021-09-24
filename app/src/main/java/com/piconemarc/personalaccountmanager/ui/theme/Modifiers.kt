package com.piconemarc.personalaccountmanager.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color

fun Modifier.deleteOperationTextModifier() = this.padding(vertical = RegularMarge)

@SuppressLint("UnnecessaryComposedModifier")
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