package com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.animation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.UiEvent
import com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.states.UiStates

//TODO pass with classic function
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.selectorOffsetAnimation(selectedOperationType: UiStates.AddOperationPopUpLeftSideIconState): Modifier =
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
                UiStates.AddOperationPopUpLeftSideIconState.PAYMENT -> Offset(0f, 58f)
                UiStates.AddOperationPopUpLeftSideIconState.TRANSFER -> Offset(0f, 116f)
                else -> Offset(0f, 0f)
            }
        }
        this.offset(y = selectorOffset.y.dp)
    }

@Composable
fun expandCollapsePaymentAnimation(
    popUpTitle: String,
    isRecurrent: UiStates.SwitchButtonState
): State<Dp> = animateDpAsState(
    targetValue = if (popUpTitle != stringResource(id = R.string.operation)) {
        if (isRecurrent == UiStates.SwitchButtonState.PUNCTUAL) 90.dp
        else 190.dp
    } else 0.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )
)

@Composable
fun expandCollapseTransferAnimation(popUpTitle: String): State<Dp> = animateDpAsState(
    targetValue = if (popUpTitle == stringResource(id = R.string.transfer)) 130.dp else 0.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )
)

@Composable
fun addOperationPopUpAnimation(addOperationPopUpState: UiStates.AddOperationPopUpState): TransitionsData.AddOperationPopUpTransitionData {
    val transition = updateTransition(targetState = addOperationPopUpState, label = "")
    val alpha = transition.animateFloat(
        transitionSpec = { tween(durationMillis = 500) }, label = ""
    ) {
        when (it) {
            UiStates.AddOperationPopUpState.COLLAPSED -> 0f
            UiStates.AddOperationPopUpState.EXPANDED -> 0.7f
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
            UiStates.AddOperationPopUpState.COLLAPSED -> 0.dp
            UiStates.AddOperationPopUpState.EXPANDED -> 800.dp
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
            UiStates.AddOperationPopUpState.COLLAPSED -> (-50).dp
            UiStates.AddOperationPopUpState.EXPANDED -> 100.dp
        }
    }
    return remember(transition) {
        TransitionsData.AddOperationPopUpTransitionData(
            alpha,
            size,
            position
        )
    }
}


fun popUpAnimationHandler(event: UiEvent) {

}


@Composable
fun amountTextFieldAnimation(amount: String): TransitionsData.AmountTextFieldTransitionData {
    Log.i("TAG", "amountTextFieldAnimation: " + amount + " " + getAmountState(amount = amount))
    val transition = updateTransition(targetState = getAmountState(amount = amount), label = "")

    val backgroundColor = transition.animateColor(
        label = "", transitionSpec = { tween(durationMillis = 400) }) {
        when (it) {
            UiStates.AmountTextFieldState.NAN -> Color.Gray
            UiStates.AmountTextFieldState.POSITIVE -> Color.Green
            UiStates.AmountTextFieldState.NEGATIVE -> Color.Red
        }
    }
    val textColor = transition.animateColor(
        label = "", transitionSpec = { tween(durationMillis = 400) }) {
        when (it) {
            UiStates.AmountTextFieldState.NAN -> MaterialTheme.colors.onSecondary
            UiStates.AmountTextFieldState.POSITIVE -> MaterialTheme.colors.onSecondary
            UiStates.AmountTextFieldState.NEGATIVE -> MaterialTheme.colors.onPrimary
        }
    }

    return remember(transition) {
        TransitionsData.AmountTextFieldTransitionData(
            backgroundColor = backgroundColor,
            textColor = textColor
        )
    }
}


@Composable
fun getAmountState(amount: String): UiStates.AmountTextFieldState {
    return try {
        if (amount.toDouble() < 0) UiStates.AmountTextFieldState.NEGATIVE
        else UiStates.AmountTextFieldState.POSITIVE
    } catch (exception: NumberFormatException) {
        println(exception)
        UiStates.AmountTextFieldState.NAN
    }
}

@Composable
fun expandCollapseEndDatePanel(isSelected: Boolean): State<Dp> = animateDpAsState(
    targetValue = if (isSelected) 100.dp else 0.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )
)