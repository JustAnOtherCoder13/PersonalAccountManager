package com.piconemarc.personalaccountmanager.ui.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.ui.theme.BigMarge
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.personalaccountmanager.ui.theme.XlMarge
import com.piconemarc.viewmodel.viewModel.PAMUiDataAnimation
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpScreenModel
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpState

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


@Composable
fun pAMAddOperationPopUpIconMenuPanelSelectorAnimation(expandedOptions: Pair<Boolean, Boolean>): PAMUiDataAnimations.AddOperationPopUpIconMenuPanelAnimationData {
    val transition = updateTransition(targetState = expandedOptions, label = "")

    val offset = transition.animateOffset(
        transitionSpec = {
            spring(
                stiffness = Spring.StiffnessLow,
                dampingRatio = Spring.DampingRatioLowBouncy
            )
        },
        label = ""
    ) {

        Offset(
            0f, if (it.second && it.first) {
                116f
            } else if (it.first) {
                58f
            }  else
                0f
        )
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
fun pAMExpandCollapseEndDatePanel(isSelected: Boolean): State<Dp> = animateDpAsState(
    targetValue = if (isSelected) 100.dp else 0.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )
)

@Composable
fun pAMAmountTextFieldAnimation(amount: String): PAMUiDataAnimations.AmountTextFieldTransitionData {
    val transition = updateTransition(targetState = getAmountState(amount = amount), label = "")

    val backgroundColor = transition.animateColor(
        label = "", transitionSpec = { tween(durationMillis = 400) }) {
        when (it) {
            AmountTextFieldState.NAN -> Color.Gray
            AmountTextFieldState.POSITIVE -> Color.Green
            AmountTextFieldState.NEGATIVE -> Color.Red
        }
    }
    val textColor = transition.animateColor(
        label = "", transitionSpec = { tween(durationMillis = 400) }) {
        when (it) {
            AmountTextFieldState.NAN -> MaterialTheme.colors.onSecondary
            AmountTextFieldState.POSITIVE -> MaterialTheme.colors.onSecondary
            AmountTextFieldState.NEGATIVE -> MaterialTheme.colors.onPrimary
        }
    }

    return remember(transition) {
        PAMUiDataAnimations.AmountTextFieldTransitionData(
            backgroundColor = backgroundColor,
            textColor = textColor
        )
    }
}

@Composable
fun getAmountState(amount: String): AmountTextFieldState {
    return try {
        if (amount.toDouble() < 0) AmountTextFieldState.NEGATIVE
        else AmountTextFieldState.POSITIVE
    } catch (exception: NumberFormatException) {
        println(exception)
        AmountTextFieldState.NAN
    }
}

enum class AmountTextFieldState {
    NEGATIVE,
    POSITIVE,
    NAN
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

    class AddOperationPopUpIconMenuPanelAnimationData(
        offset: State<Offset>
    ) : PAMUiDataAnimation {
        val offset by offset
    }

    class AddOperationPopUpRecurrentButtonTransitionData(
        buttonSize: State<Dp>, leftBottomCornerSize: State<Dp>
    ) : PAMUiDataAnimation {
        val buttonSize by buttonSize
        val leftBottomCornerSize by leftBottomCornerSize
    }

    class AmountTextFieldTransitionData(
        backgroundColor: State<Color>,
        textColor: State<Color>,
    ) : PAMUiDataAnimation {
        val backgroundColor by backgroundColor
        val textColor by textColor
    }
}
