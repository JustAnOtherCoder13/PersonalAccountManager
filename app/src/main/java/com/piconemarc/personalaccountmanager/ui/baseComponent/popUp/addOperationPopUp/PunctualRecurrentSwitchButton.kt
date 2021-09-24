package com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.addOperationPopUp

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.baseComponent.expandCollapseEndDatePanel
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.RecurrentOptionPanel
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
    modifier: Modifier,
    isRecurrent: (Boolean) -> Unit,
    onMonthSelected: (String) -> Unit,
    onYearSelected: (String) -> Unit,
    selectedMonth: String,
    selectedYear: String
) {
    var selectedButton: Int by remember {
        mutableStateOf(0)
    }
    val recurrent = 1
    val punctual = 0
    isRecurrent(selectedButton == 1)

    Column(modifier = modifier.padding(bottom = RegularMarge)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = RegularMarge,
                )
        ) {//TODO pass with Transition
            val shapeSize by animateDpAsState(
                targetValue = if (selectedButton == recurrent) BigMarge else RegularMarge,
                animationSpec = if (selectedButton != recurrent) tween(delayMillis = 120) else tween(
                    delayMillis = 0
                )
            )
            val shapeCorner by animateDpAsState(
                targetValue = if (selectedButton == recurrent) 0.dp else XlMarge,
                animationSpec = if (selectedButton != recurrent) tween(delayMillis = 120) else tween(
                    delayMillis = 0
                )
            )
            SwitchButton(
                onButtonSelected = { selectedButton = punctual },
                isSelected = selectedButton == punctual,
                title = stringResource(R.string.punctualSwitchButton),
                switchShape = LeftSwitchShape
            )
            SwitchButton(
                onButtonSelected = { selectedButton = recurrent },
                isSelected = selectedButton == recurrent,
                title = stringResource(R.string.recurrentSwitchButton),
                switchShape = RightSwitchShape.copy(bottomStart = CornerSize(shapeCorner)),
                bottomPadding = shapeSize
            )
        }
        //Recurrent options
        RecurrentOptionPanel(
            modifier = Modifier.height(expandCollapseEndDatePanel(isSelected = selectedButton == recurrent).value),
            onMonthSelected = onMonthSelected,
            onYearSelected = onYearSelected,
            selectedMonth = selectedMonth,
            selectedYear = selectedYear,
        )
    }
}