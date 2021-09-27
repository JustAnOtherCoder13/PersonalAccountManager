package com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.addOperationPopUp

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.RecurrentOptionPanel
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.animation.TransitionsData
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.animation.expandCollapseEndDatePanel
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.animation.expandCollapsePaymentAnimation
import com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.events.popUpOperationType
import com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.states.UiStates
import com.piconemarc.personalaccountmanager.ui.theme.*

@Composable
fun SwitchButton(
    onButtonSelected: () -> Unit,
    isSelected: Boolean,
    title: String,
    switchShape: Shape,
    bottomPadding: Dp = RegularMarge
) {
    //TODO pass with transition
    val buttonColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
        animationSpec = if (!isSelected) tween(delayMillis = 120) else tween(delayMillis = 0)
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary,
        animationSpec = if (!isSelected) tween(delayMillis = 120) else tween(delayMillis = 0)
    )
    Button(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = buttonColor,
                shape = switchShape
            )
            .padding(end = RegularMarge),
        onClick = onButtonSelected,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        elevation = ButtonDefaults.elevation(0.dp)

    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(
                top = RegularMarge,
                bottom = bottomPadding,
                end = RegularMarge
            ),
            color = textColor
        )
    }
}


@Composable
fun PunctualOrRecurrentSwitchButton(
    updateSwitchButtonState: (UiStates.SwitchButtonState) -> Unit,
    onMonthSelected: (String) -> Unit,
    onYearSelected: (String) -> Unit,
    selectedMonth: String,
    selectedYear: String,
    switchButtonState: UiStates.SwitchButtonState
) {
    val transition = recurrentOptionPanelAnimation(recurrentSwitchButtonState = switchButtonState)

    Column(
        modifier =
        Modifier
            .height(
                expandCollapsePaymentAnimation(
                    stringResource(popUpOperationType.value),
                    switchButtonState
                ).value
            )

            .padding(bottom = RegularMarge)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = RegularMarge)
        ) {
            SwitchButton(
                onButtonSelected = { updateSwitchButtonState(UiStates.SwitchButtonState.PUNCTUAL) },
                isSelected = switchButtonState == UiStates.SwitchButtonState.PUNCTUAL,
                title = stringResource(R.string.punctualSwitchButton),
                switchShape = LeftSwitchShape
            )
            SwitchButton(
                onButtonSelected = { updateSwitchButtonState(UiStates.SwitchButtonState.RECURRENT) },
                isSelected = switchButtonState == UiStates.SwitchButtonState.RECURRENT,
                title = stringResource(R.string.recurrentSwitchButton),
                switchShape = RightSwitchShape.copy(bottomStart = CornerSize(transition.leftBottomCornerSize)),
                bottomPadding = transition.buttonSize
            )
        }
        //Recurrent options
        RecurrentOptionPanel(
            modifier = Modifier.height(expandCollapseEndDatePanel(isSelected = switchButtonState == UiStates.SwitchButtonState.RECURRENT).value),
            onMonthSelected = onMonthSelected,
            onYearSelected = onYearSelected,
            selectedMonth = selectedMonth,
            selectedYear = selectedYear,
        )
    }
}

@Composable
fun recurrentOptionPanelAnimation(recurrentSwitchButtonState: UiStates.SwitchButtonState): TransitionsData.RecurrentButtonTransitionData {
    val transition = updateTransition(targetState = recurrentSwitchButtonState, label = "")
    val buttonSize = transition.animateDp(
        transitionSpec = {
            tween(delayMillis = if (recurrentSwitchButtonState != UiStates.SwitchButtonState.PUNCTUAL) 120 else 0)
        }, label = ""
    ) {
        when (it) {
            UiStates.SwitchButtonState.PUNCTUAL -> RegularMarge
            UiStates.SwitchButtonState.RECURRENT -> BigMarge
        }
    }
    val leftBottomCornerSize = transition.animateDp(
        transitionSpec = {
            tween(delayMillis = if (recurrentSwitchButtonState != UiStates.SwitchButtonState.RECURRENT) 120 else 0)
        }, label = ""
    ) {
        when (it) {
            UiStates.SwitchButtonState.PUNCTUAL -> XlMarge
            UiStates.SwitchButtonState.RECURRENT -> 0.dp
        }
    }

    return remember(transition) {
        TransitionsData.RecurrentButtonTransitionData(
            buttonSize = buttonSize,
            leftBottomCornerSize = leftBottomCornerSize
        )
    }
}