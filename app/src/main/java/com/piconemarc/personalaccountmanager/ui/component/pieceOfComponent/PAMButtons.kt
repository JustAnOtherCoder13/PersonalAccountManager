package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.piconemarc.personalaccountmanager.ui.component.screen.InterlayerIcon
import com.piconemarc.personalaccountmanager.ui.theme.*

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
            .width(120.dp),
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
    onIconButtonClicked: () -> Unit,
    iconButton: PAMIconButtons,
    iconColor: Color = MaterialTheme.colors.onPrimary,
    backgroundColor: Color = MaterialTheme.colors.primaryVariant
) {
    IconButton(
        onClick = { onIconButtonClicked() }
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
            .width(100.dp)
            .height(50.dp),
        shape = RoundedCornerShape(XlMarge),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = BrownDark,
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 10.dp
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
    onHomeButtonClicked: () -> Unit
) = InterlayerIcon(
    backGroundColor = PastelGreen,
    iconButton = PAMIconButtons.Home,
    onIconFolderClicked = onHomeButtonClicked,
    yOffset = 0.dp,
    iconYOffset = iconYOffset // todo pass with animation
)

@Composable
fun PAMPaymentButton(
    iconYOffset: Dp,
    onPaymentButtonClicked: () -> Unit
) = InterlayerIcon(
    backGroundColor = PastelBlue,
    iconButton = PAMIconButtons.Payment,
    onIconFolderClicked = onPaymentButtonClicked,
    yOffset = 45.dp,
    iconYOffset = iconYOffset
)

@Composable
fun PAMChartButton(
    onChartButtonClicked: () -> Unit
) = InterlayerIcon(
    backGroundColor = PastelPurple,
    iconButton = PAMIconButtons.Chart,
    onIconFolderClicked = onChartButtonClicked,
    yOffset = 90.dp,
)


sealed class PAMIconButtons {

    open val iconContentDescription: Int = 0
    open val vectorIcon: Int = 0
    open val iconName: Int = 0

    object Operation : PAMIconButtons() {
        override val iconContentDescription: Int = R.string.operationIconContentDescription
        override val vectorIcon: Int = R.drawable.ic_outline_payments_24
        override val iconName: Int = R.string.operation
    }

    object Payment : PAMIconButtons() {
        override val iconContentDescription: Int = R.string.paymentIconContentDescription
        override val vectorIcon: Int = R.drawable.ic_outline_ios_share_24
        override val iconName: Int = R.string.payment
    }

    object Transfer : PAMIconButtons() {
        override val iconContentDescription: Int = R.string.transferIconContentDescription
        override val vectorIcon: Int = R.drawable.ic_baseline_swap_horiz_24
        override val iconName: Int = R.string.transfer
    }

    object Home : PAMIconButtons() {
        override val iconContentDescription: Int = R.string.homeIconContentDescription
        override val vectorIcon: Int = R.drawable.ic_outline_home_24
        override val iconName: Int = R.string.homeIconContentDescription
    }

    object Chart : PAMIconButtons() {
        override val iconContentDescription: Int = R.string.chartIconContentDescription
        override val vectorIcon: Int = R.drawable.ic_outline_bar_chart_24
    }

    object Add : PAMIconButtons() {
        override val iconContentDescription: Int = R.string.addIconContentDescription
        override val vectorIcon: Int = R.drawable.ic_baseline_add_24
    }

    object Minus : PAMIconButtons() {
        override val iconContentDescription: Int = R.string.addIconContentDescription
        override val vectorIcon: Int = R.drawable.ic_outline_minus_24
    }
}