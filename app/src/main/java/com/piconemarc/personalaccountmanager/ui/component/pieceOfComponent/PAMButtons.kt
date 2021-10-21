package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.PAMUiDataAnimations
import com.piconemarc.personalaccountmanager.ui.animation.pAMAddPopUpAddOrMinusTransition
import com.piconemarc.personalaccountmanager.ui.theme.*
import com.piconemarc.viewmodel.PAMIconButtons

@Composable
fun PAMCircleIcon(
    iconButton: PAMIconButtons,
    iconColor: Color,
    backgroundColor: Color = Color.Transparent,
    yOffset: Dp = 0.dp
) {
    Surface(
        modifier = Modifier
            .background(backgroundColor, CircleShape)
            .padding(LittleMarge)
            .offset(y = yOffset),
        shape = CircleShape,
        color = backgroundColor,
        border = BorderStroke(ThinMarge, iconColor),
        elevation = ThinMarge
    )
    {
        Icon(
            imageVector = ImageVector.vectorResource(iconButton.vectorIcon),
            contentDescription = stringResource(iconButton.iconContentDescription),
            modifier = Modifier
                .background(Color.Transparent, CircleShape)
                .padding(LittleMarge),
            tint = iconColor
        )
    }
}

@Composable
fun PAMBaseButton(
    text: String,
    onButtonClicked: () -> Unit
) {
    Button(
        onClick = onButtonClicked,
        shape = ButtonShape,
        modifier = Modifier
            .width(PopUpBaseButtonWidth),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = BrownLight,
            contentColor = MaterialTheme.colors.onSecondary
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.paddingFromBaseline(bottom = BigMarge)
        )
    }
}

@Composable
fun PAMAcceptOrDismissButtons(
    onAcceptButtonClicked: () -> Unit,
    onDismissButtonClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primaryVariant, MaterialTheme.shapes.large)
            .fillMaxWidth()
            .padding(start = BigMarge, end = BigMarge, top = BigMarge),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PAMBaseButton(text = stringResource(R.string.ok)) { onAcceptButtonClicked() }
        PAMBaseButton(text = stringResource(R.string.cancel)) { onDismissButtonClicked() }
    }
}

@Composable
fun PAMIconButton(
    onIconButtonClicked: (iconButton : PAMIconButtons) -> Unit,
    iconButton: PAMIconButtons,
    iconColor: Color = MaterialTheme.colors.onPrimary,
    backgroundColor: Color = MaterialTheme.colors.primaryVariant
) {
    IconButton(
        onClick = { onIconButtonClicked(iconButton) }
    )
    {
        PAMCircleIcon(
            iconButton = iconButton,
            iconColor = iconColor,
            backgroundColor = backgroundColor
        )

    }
}

@Composable
fun PAMAddButton(onAddButtonClicked: () -> Unit) {
    Button(
        modifier = Modifier
            .border(
                width = ThinMarge,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(XlMarge)
            )
            .width(AddButtonWidth)
            .height(AddButtonHeight),
        shape = RoundedCornerShape(XlMarge),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = BrownDark,
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = AddButtonElevation
        ),
        onClick = onAddButtonClicked
    ) {
        PAMCircleIcon(
            iconButton = PAMIconButtons.Add,
            iconColor = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun PAMHomeButton(
    iconYOffset: Dp,
    onHomeButtonClicked: (iconButton : PAMIconButtons) -> Unit
) = InterlayerIcon(
    backGroundColor = PastelGreen,
    iconButton = PAMIconButtons.Home,
    onInterlayerIconClicked = onHomeButtonClicked,
    iconYOffset = iconYOffset
)

@Composable
fun PAMPaymentButton(
    iconYOffset: Dp,
    onPaymentButtonClicked: (iconButton : PAMIconButtons) -> Unit
) = InterlayerIcon(
    backGroundColor = PastelBlue,
    iconButton = PAMIconButtons.Payment,
    onInterlayerIconClicked = onPaymentButtonClicked,
    yOffset = InterlayerPaymentIconOffsetPosition,
    iconYOffset = iconYOffset
)

@Composable
fun PAMChartButton(
    onChartButtonClicked: (iconButton : PAMIconButtons) -> Unit
) = InterlayerIcon(
    backGroundColor = PastelPurple,
    iconButton = PAMIconButtons.Chart,
    onInterlayerIconClicked = onChartButtonClicked,
    yOffset = InterlayerChartIconOffsetPosition,
)

@Composable
fun AddOrMinusSwitchButton(
    onAddOrMinusClicked : (isAddClicked : Boolean)-> Unit,
    isAddOperation: Boolean
) {
    val transition = pAMAddPopUpAddOrMinusTransition(isAddOperation = isAddOperation)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = BigMarge, end = BigMarge, top = LittleMarge),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PAMIconButton(
            onIconButtonClicked = {
                onAddOrMinusClicked(true)
            },
            iconButton = PAMIconButtons.Add,
            iconColor = transition.addIconColor,
            backgroundColor = transition.addBackgroundColor
        )
        PAMIconButton(
            onIconButtonClicked = {
                onAddOrMinusClicked(false)
            },
            iconButton = PAMIconButtons.Minus,
            iconColor = transition.minusIconColor,
            backgroundColor = transition.minusBackgroundColor
        )
    }
}

@Composable
fun InterLayerIconsSelector(
    topButton: @Composable () -> Unit,
    middleButton: @Composable () -> Unit,
    backgroundButton: @Composable () -> Unit
) {
    backgroundButton()
    middleButton()
    topButton()
}

@Composable
fun InterlayerIcon(
    backGroundColor: Color,
    iconButton: PAMIconButtons,
    onInterlayerIconClicked: (iconButton: PAMIconButtons) -> Unit,
    yOffset: Dp = 0.dp,
    iconYOffset: Dp = InterLayerIconOffsetDown
) {
    Row(
        modifier = Modifier
            .width(InterlayerIconPanelRowWidth)
            .height(InterlayerIconPanelRowHeight)
            .offset(y = yOffset)
            .background(
                color = backGroundColor,
                shape = RoundedCornerShape(
                    topEnd = BigMarge,
                    bottomEnd = BigMarge
                )
            )
            .clickable { onInterlayerIconClicked(iconButton) },
    ) {
        PAMCircleIcon(
            iconButton = iconButton,
            iconColor = MaterialTheme.colors.onSecondary,
            yOffset = iconYOffset
        )
    }
}

@Composable
fun InterlayerIconPanel(
    transition: PAMUiDataAnimations.InterlayerAnimationData,
    onInterlayerIconClicked: (iconButton: PAMIconButtons) -> Unit,
    selectedInterlayerIconButton: PAMIconButtons
) {
    Box(
        modifier = Modifier
            .width(InterlayerIconPanelWidth)
            .fillMaxHeight()
    ) {
        when (selectedInterlayerIconButton) {
            is PAMIconButtons.Payment ->
                PaymentInterlayerIconDisposition(
                    onInterlayerIconClicked = onInterlayerIconClicked,
                    transition = transition
                )
            is PAMIconButtons.Chart ->
                ChartInterlayerIconDisposition(
                    onInterlayerIconClicked = onInterlayerIconClicked,
                    transition = transition
                )
            else -> HomeInterlayerIconDisposition(
                onInterlayerIconClicked = onInterlayerIconClicked,
                transition = transition
            )
        }
    }
}


@Composable
fun HomeInterlayerIconDisposition(
    onInterlayerIconClicked: (iconButton: PAMIconButtons) -> Unit,
    transition: PAMUiDataAnimations.InterlayerAnimationData
) {
    InterLayerIconsSelector(
        topButton = {
            PAMHomeButton(
                onHomeButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.homeIconVerticalPosition
            )
        },
        middleButton = {
            PAMPaymentButton(
                onPaymentButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.paymentIconVerticalPosition
            )
        },
        backgroundButton = { PAMChartButton(onChartButtonClicked = onInterlayerIconClicked) })
}

@Composable
fun PaymentInterlayerIconDisposition(
    onInterlayerIconClicked: (iconButton: PAMIconButtons) -> Unit,
    transition: PAMUiDataAnimations.InterlayerAnimationData
) {
    InterLayerIconsSelector(
        topButton = {
            PAMPaymentButton(
                onPaymentButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.paymentIconVerticalPosition
            )
        },
        middleButton = {
            PAMHomeButton(
                onHomeButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.homeIconVerticalPosition
            )
        },
        backgroundButton = { PAMChartButton(onChartButtonClicked = onInterlayerIconClicked) })
}

@Composable
fun ChartInterlayerIconDisposition(
    onInterlayerIconClicked: (iconButton: PAMIconButtons) -> Unit,
    transition: PAMUiDataAnimations.InterlayerAnimationData
) {
    InterLayerIconsSelector(
        topButton = { PAMChartButton(onChartButtonClicked = onInterlayerIconClicked) },
        middleButton = {
            PAMPaymentButton(
                onPaymentButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.paymentIconVerticalPosition
            )
        },
        backgroundButton = {
            PAMHomeButton(
                onHomeButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.homeIconVerticalPosition
            )
        })
}

