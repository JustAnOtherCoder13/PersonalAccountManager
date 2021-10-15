package com.piconemarc.personalaccountmanager.ui.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.ui.theme.PastelBlue
import com.piconemarc.personalaccountmanager.ui.theme.PastelGreen
import com.piconemarc.personalaccountmanager.ui.theme.PastelPurple

@Composable
fun pAMInterlayerAnimation(selectedInterlayer:Pair< Boolean,Boolean>) : PAMUiDataAnimations.InterlayerAnimationData{
    val transition = updateTransition(targetState = selectedInterlayer, label = "" )
    val color = transition.animateColor(
        label = "",
        transitionSpec = { tween(durationMillis = 800) }) {
        if (!it.first && !it.second) {
            PastelGreen
        } else if (it.first) {
            PastelBlue
        } else {
            PastelPurple
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
        if (!it.first && !it.second){
            28.dp
        }else{
            0.dp
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
        if (it.second){
            0.dp
        }else{
            28.dp
        }
    }

    return PAMUiDataAnimations.InterlayerAnimationData(
        interlayerColor = color,
        homeIconVerticalPosition = homePosition,
        paymentIconVerticalPosition = paymentPosition
    )
}