package com.piconemarc.personalaccountmanager.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.theme.BigMarge
import com.piconemarc.personalaccountmanager.ui.theme.ButtonShape
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.personalaccountmanager.ui.theme.ThinBorder

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
            backgroundColor = MaterialTheme.colors.secondaryVariant,
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
            .background(MaterialTheme.colors.primary, MaterialTheme.shapes.large)
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
    iconButton: PAMIconButtons
) {
    IconButton(onClick = { onIconButtonClicked() })
    {
        Surface(
            modifier = Modifier
                .background(MaterialTheme.colors.primary, CircleShape)
                .padding(LittleMarge),
            shape = CircleShape,
            color = MaterialTheme.colors.primary,
            border = BorderStroke(ThinBorder, MaterialTheme.colors.onPrimary),
        )
        {
            Icon(
                imageVector = ImageVector.vectorResource(iconButton.vectorIcon),
                contentDescription = stringResource(iconButton.iconContentDescription),
                modifier = Modifier
                    .background(Color.Transparent, CircleShape)
                    .padding(LittleMarge),
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

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
}