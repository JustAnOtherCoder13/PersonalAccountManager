package com.piconemarc.personalaccountmanager.newUi.stateManager

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun pAMBasePopUpEnterExitAnimation(isExpended: Boolean): PAMUiDataAnimations.BasePopUpAnimationData {
    val transition = updateTransition(targetState = isExpended, label = "")
    val alpha = transition.animateFloat(
        transitionSpec = { tween(durationMillis = 500) }, label = ""
    ) {
        when (it) {
            false -> 0f
            true -> 0.7f
        }
    }
    val size = transition.animateDp(
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMedium
            )
        }, label = ""
    ) {
        when (it) {
            false -> 0.dp
            true -> 800.dp
        }
    }
    val position = transition.animateDp(

        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            )
        }, label = ""
    ) {
        when (it) {
            false -> (-50).dp
            true -> 100.dp
        }
    }
    return remember(transition) {
        PAMUiDataAnimations.BasePopUpAnimationData(
            alpha,
            size,
            position
        )
    }
}

object PAMUiDataAnimations : PAMUiDataAnimation {

    class BasePopUpAnimationData(
        alpha: State<Float>,
        size: State<Dp>,
        position: State<Dp>,
    ) : PAMUiDataAnimation {
        val alpha by alpha
        val size by size
        val position by position
    }

}