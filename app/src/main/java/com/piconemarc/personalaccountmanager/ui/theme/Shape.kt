package com.piconemarc.personalaccountmanager.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(15.dp),
    large = RoundedCornerShape(20.dp),
)

val ButtonShape = RoundedCornerShape(topStart = RegularMarge, topEnd = RegularMarge)
val PopUpFieldBackgroundShape = RoundedCornerShape(
    topEnd = BigMarge,
    bottomEnd = BigMarge
)
val LeftSwitchShape = RoundedCornerShape(topEnd = XlMarge, bottomEnd = XlMarge)
val RightSwitchShape = RoundedCornerShape(topStart = XlMarge, bottomStart = XlMarge)
val RecurrentOptionPanelShape = RoundedCornerShape(bottomStart = BigMarge, bottomEnd = BigMarge)