package com.piconemarc.personalaccountmanager.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

fun Modifier.deleteOperationTextModifier() = this.padding(vertical = RegularMarge)

fun Modifier.popUpClickableItemModifier(color: Color) = this
    .fillMaxWidth()
    .padding(top = RegularMarge, bottom = RegularMarge, end = RegularMarge)
    .background(
        color = color,
        shape = PopUpFieldBackgroundShape
    )

