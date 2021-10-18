package com.piconemarc.personalaccountmanager.ui.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMIconButtons
import com.piconemarc.personalaccountmanager.ui.theme.*

@Composable
fun pAMInterlayerAnimation(selectedInterlayerButton: PAMIconButtons) : PAMUiDataAnimations.InterlayerAnimationData{
    val transition = updateTransition(targetState = selectedInterlayerButton, label = "" )
    val color = transition.animateColor(
        label = "",
        transitionSpec = { tween(durationMillis = 800) }) {
        when(it){
            is PAMIconButtons.Payment -> PastelBlue
            is PAMIconButtons.Chart -> PastelPurple
            else -> PastelGreen
        }
    }

    val homePosition = transition.animateDp(
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        }, label = ""
    ) {
        when(it){
            is PAMIconButtons.Home -> InterLayerIconOffsetDown
            else -> InterLayerIconOffsetUp
        }
    }
    val paymentPosition = transition.animateDp(
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        }, label = ""
    ) {
        when(it){
            is PAMIconButtons.Chart -> InterLayerIconOffsetUp
            else ->InterLayerIconOffsetDown
        }
    }

    return PAMUiDataAnimations.InterlayerAnimationData(
        interlayerColor = color,
        homeIconVerticalPosition = homePosition,
        paymentIconVerticalPosition = paymentPosition
    )
}