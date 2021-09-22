package com.piconemarc.personalaccountmanager.ui.baseComponent

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.baseComponent.addOperationPopUp.AddOperationPopUpState
import com.piconemarc.personalaccountmanager.ui.baseComponent.addOperationPopUp.TransitionData
import com.piconemarc.personalaccountmanager.ui.baseComponent.addOperationPopUp.isRecurrent

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.selectorOffsetAnimation(selectedOperationType: Int): Modifier =
    this.composed {

        val popUpLeftSidePanelIconSelectorTransition =
            updateTransition(targetState = selectedOperationType, label = "")

        val selectorOffset by popUpLeftSidePanelIconSelectorTransition.animateOffset(
            label = "",
            transitionSpec = {
                spring(
                    stiffness = Spring.StiffnessLow,
                    dampingRatio = Spring.DampingRatioLowBouncy
                )
            }
        ) { selectedOperationOption_ ->
            when (selectedOperationOption_) {
                R.string.payment -> Offset(0f, 58f)
                R.string.transfer -> Offset(0f, 116f)
                else -> Offset(0f, 0f)
            }
        }
        this.offset(y = selectorOffset.y.dp)
    }

@Composable
fun expandCollapsePaymentAnimation(popUpTitle: Int): State<Dp> = animateDpAsState(
    targetValue = if (popUpTitle != R.string.operation) {
        if (!isRecurrent.value) 90.dp
        else 190.dp
    } else 0.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )
)

@Composable
fun expandCollapseTransferAnimation(popUpTitle: Int): State<Dp> = animateDpAsState(
    targetValue = if (popUpTitle == R.string.transfer) 130.dp else 0.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )
)

@Composable
fun addOperationPopUpAnimation(addOperationPopUpState: AddOperationPopUpState): TransitionData {
    val transition = updateTransition(targetState = addOperationPopUpState, label = "")
    val alpha = transition.animateFloat(
        transitionSpec = { tween(durationMillis = 500) }, label = ""
    ) {
        when (it) {
            AddOperationPopUpState.COLLAPSED -> 0f
            AddOperationPopUpState.EXPANDED -> 0.7f
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
            AddOperationPopUpState.COLLAPSED -> 0.dp
            AddOperationPopUpState.EXPANDED -> 800.dp
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
            AddOperationPopUpState.COLLAPSED -> (-50).dp
            AddOperationPopUpState.EXPANDED -> 100.dp
        }
    }
    return remember(transition) { TransitionData(alpha, size, position) }
}


