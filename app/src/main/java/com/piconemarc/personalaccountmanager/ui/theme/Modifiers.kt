package com.piconemarc.personalaccountmanager.ui.theme

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

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


