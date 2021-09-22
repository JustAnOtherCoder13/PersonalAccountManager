package com.piconemarc.personalaccountmanager.ui.baseComponent.addOperationPopUp

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
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
import com.piconemarc.personalaccountmanager.ui.baseComponent.RecurrentOptionPanel
import com.piconemarc.personalaccountmanager.ui.theme.*

@Composable
fun SwitchButton(
    onButtonSelected: () -> Unit,
    isSelected: Boolean,
    title: String,
    switchShape: Shape,
    bottomPadding: Dp = RegularMarge
) {
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
    onEndDateSelected: (date: Pair<String, String>) -> Unit,
    modifier: Modifier,
    isRecurrent: (Boolean) -> Unit
) {
    var selectedButton: Int by remember {
        mutableStateOf(0)
    }
    var selectedDate: Pair<String, String> by remember {
        mutableStateOf(Pair("", ""))
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
        ) {
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
                switchShape = RightSwitchShape.copy(
                    bottomStart = CornerSize(
                        shapeCorner
                    )
                ),
                bottomPadding = shapeSize
            )
        }
        //Recurrent options

        RecurrentOptionPanel(
            modifier = Modifier.height(expandCollapseEndDatePanel(isSelected = selectedButton == recurrent).value),
            onMonthSelected = { month ->
                selectedDate = selectedDate.copy(first = month)
                onEndDateSelected(selectedDate)
            },
            onYearSelected = { year ->
                selectedDate = selectedDate.copy(second = year)
                onEndDateSelected(selectedDate)
            }
        )
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