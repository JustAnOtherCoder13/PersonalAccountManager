package com.piconemarc.personalaccountmanager.ui.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMIconButtons
import com.piconemarc.personalaccountmanager.ui.theme.*

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
            true -> 20.dp
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


@Composable
fun pAMAddOperationPopUpIconMenuPanelSelectorAnimation(selectedIcon : PAMIconButtons): PAMUiDataAnimations.AddOperationPopUpIconMenuPanelAnimationData {
    val transition = updateTransition(targetState = selectedIcon, label = "")

    val offset = transition.animateOffset(
        transitionSpec = {
            spring(
                stiffness = Spring.StiffnessLow,
                dampingRatio = Spring.DampingRatioLowBouncy
            )
        },
        label = ""
    ) {
        Offset(x=0f, y = when(it){
            is PAMIconButtons.Transfer -> SelectorLowPosition
            is PAMIconButtons.Payment -> SelectorMiddlePosition
            else -> SelectorHighPosition
        })
    }

    return remember(transition) {
        PAMUiDataAnimations.AddOperationPopUpIconMenuPanelAnimationData(
            offset
        )
    }
}

@Composable
fun pAMRecurrentOptionButtonAnimation(isRecurrentOptionExpanded: Boolean): PAMUiDataAnimations.AddOperationPopUpRecurrentButtonTransitionData {
    val transition = updateTransition(targetState = isRecurrentOptionExpanded, label = "")
    val buttonSize = transition.animateDp(
        transitionSpec = {
            tween(delayMillis = if (isRecurrentOptionExpanded) 0 else 120)
        }, label = ""
    ) {
        if (it) BigMarge else RegularMarge
    }
    val leftBottomCornerSize = transition.animateDp(
        transitionSpec = {
            tween(delayMillis = if (isRecurrentOptionExpanded) 120 else 0)
        }, label = ""
    ) {
        if (it) 0.dp else XlMarge
    }

    return remember(transition) {
        PAMUiDataAnimations.AddOperationPopUpRecurrentButtonTransitionData(
            buttonSize = buttonSize,
            leftBottomCornerSize = leftBottomCornerSize
        )
    }
}

@Composable
fun pAMExpandCollapseTransferPanelAnimation(isTransferOptionExpanded: Boolean): State<Dp> =
    animateDpAsState(
        targetValue = if (isTransferOptionExpanded) 130.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

@Composable
fun pAMExpandCollapsePaymentAnimation(
    isPaymentOptionExpanded: Boolean,
    isRecurrentOptionExpanded: Boolean
): State<Dp> = animateDpAsState(
    targetValue = if (isPaymentOptionExpanded) {
        if (isRecurrentOptionExpanded) 190.dp
        else 90.dp
    } else 0.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )
)

@Composable
fun pAMExpandCollapseEndDatePanel(isRecurrentOptionSelected: Boolean): State<Dp> = animateDpAsState(
    targetValue = if (isRecurrentOptionSelected) 100.dp else 0.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )
)


@Composable
fun pAMAddOperationPopUpBackgroundColor(isAddOperation : Boolean) : State<Color> = animateColorAsState(
    targetValue = if (isAddOperation) Positive else Negative,
    animationSpec = tween(durationMillis = 300)
)

@Composable
fun pAMAddPopUpAddOrMinusTransition (isAddOperation : Boolean): PAMUiDataAnimations.AddOperationPopUpAddOrMinusTransitionData{
    val transition = updateTransition(targetState = isAddOperation, label = "")

    val addIconColor = transition.animateColor(label = "") {
        if (it) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary
    }

    val addBackgroundColor = transition.animateColor(label = "") {
        if (it) MaterialTheme.colors.primary else MaterialTheme.colors.secondaryVariant
    }

    val minusIconColor = transition.animateColor(label = "") {
        if (!it) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary
    }

    val minusBackgroundColor = transition.animateColor(label = "") {
        if (!it) MaterialTheme.colors.primary else MaterialTheme.colors.secondaryVariant
    }

   return PAMUiDataAnimations.AddOperationPopUpAddOrMinusTransitionData(
       addIconColor = addIconColor ,
       addBackgroundColor = addBackgroundColor,
       minusIconColor = minusIconColor,
       minusBackgroundColor = minusBackgroundColor
   )
}

@Composable
fun pAMBaseSwitchButtonTransition(isSelected : Boolean) : PAMUiDataAnimations.BaseSwitchButtonData{
    val transition = updateTransition(targetState = isSelected, label = "")

    val buttonColor = transition.animateColor(
        label = "",
        transitionSpec = { tween(delayMillis = if (!isSelected) 120 else 0) }
        ) {
        if (it) MaterialTheme.colors.primary else MaterialTheme.colors.secondaryVariant
    }

    val textColor = transition.animateColor(
        label = "",
        transitionSpec = { tween(delayMillis = if (!isSelected) 120 else 0) }
    ) {
        if (it) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary
    }

    return PAMUiDataAnimations.BaseSwitchButtonData(
        buttonColor = buttonColor ,
        textColor = textColor,
    )
}

