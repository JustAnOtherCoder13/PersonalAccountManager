package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base

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
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.BaseUiModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.pAMAddPopUpAddOrMinusTransition
import com.piconemarc.personalaccountmanager.ui.theme.*

@Composable
private fun BaseCircleIcon(
    modifier: Modifier = Modifier,
    iconButton: PAMIconButtons,
    iconColor: Color,
    backgroundColor: Color = Color.Transparent,
    yOffset: Dp = 0.dp,
) {
    Surface(
        modifier = modifier
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
private fun BaseButton(
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
fun AcceptOrDismissButtons(
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
        BaseButton(text = stringResource(R.string.ok)) { onAcceptButtonClicked() }
        BaseButton(text = stringResource(R.string.cancel)) { onDismissButtonClicked() }
    }
}

@Composable
fun BaseIconButton(
    modifier: Modifier = Modifier,
    onIconButtonClicked: (iconButton: PAMIconButtons) -> Unit,
    iconButton: PAMIconButtons,
    iconColor: Color = MaterialTheme.colors.onPrimary,
    backgroundColor: Color = MaterialTheme.colors.primaryVariant
) {
    IconButton(onClick = { onIconButtonClicked(iconButton) })
    {
        BaseCircleIcon(
            iconButton = iconButton,
            iconColor = iconColor,
            backgroundColor = backgroundColor,
            modifier = modifier
        )

    }
}

@Composable
fun BrownBackgroundAddButton(onAddButtonClicked: () -> Unit) {
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
        BaseCircleIcon(
            iconButton = PAMIconButtons.Add,
            iconColor = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun HomeButton(
    iconYOffset: Dp,
    onHomeButtonClicked: (iconButton: PAMIconButtons) -> Unit
) = BaseInterlayerIcon(
    backGroundColor = PastelGreen,
    iconButton = PAMIconButtons.Home,
    onInterlayerIconClicked = onHomeButtonClicked,
    iconYOffset = iconYOffset
)

@Composable
fun PaymentButton(
    iconYOffset: Dp,
    onPaymentButtonClicked: (iconButton: PAMIconButtons) -> Unit
) = BaseInterlayerIcon(
    backGroundColor = PastelBlue,
    iconButton = PAMIconButtons.Payment,
    onInterlayerIconClicked = onPaymentButtonClicked,
    yOffset = InterlayerPaymentIconOffsetPosition,
    iconYOffset = iconYOffset
)

@Composable
fun ChartButton(
    onChartButtonClicked: (iconButton: PAMIconButtons) -> Unit
) = BaseInterlayerIcon(
    backGroundColor = PastelPurple,
    iconButton = PAMIconButtons.Chart,
    onInterlayerIconClicked = onChartButtonClicked,
    yOffset = InterlayerChartIconOffsetPosition,
)

@Composable
fun AddOperationPopUpAddOrMinusSwitchButton(
    onAddOrMinusClicked: (isAddClicked: Boolean) -> Unit,
    isAddOperation: Boolean,
    isEnable: Boolean
) {
    val transition =
        pAMAddPopUpAddOrMinusTransition(isAddOperation = isAddOperation, isEnable = isEnable)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = BigMarge, end = BigMarge, top = LittleMarge),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BaseIconButton(
            onIconButtonClicked = {
                if (isEnable)
                    onAddOrMinusClicked(true)
            },
            iconButton = PAMIconButtons.Add,
            iconColor = transition.addIconColor,
            backgroundColor = transition.addBackgroundColor
        )
        BaseIconButton(
            onIconButtonClicked = {
                if (isEnable)
                    onAddOrMinusClicked(false)
            },
            iconButton = PAMIconButtons.Minus,
            iconColor = transition.minusIconColor,
            backgroundColor = transition.minusBackgroundColor
        )
    }
}

@Composable
private fun BaseInterlayerIcon(
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
        BaseCircleIcon(
            iconButton = iconButton,
            iconColor = MaterialTheme.colors.onSecondary,
            yOffset = iconYOffset
        )
    }
}

@Composable
fun <T : BaseUiModel> BaseDeleteIconButton(
    onDeleteItemButtonCLick: (operation: T) -> Unit,
    uiModel: T
) {
    Box(modifier = Modifier.size(35.dp)) {
        BaseIconButton(
            onIconButtonClicked = { onDeleteItemButtonCLick(uiModel) },
            iconButton = PAMIconButtons.Delete,
            iconColor = MaterialTheme.colors.onSecondary,
            backgroundColor = Color.Transparent
        )
    }
}