package com.piconemarc.personalaccountmanager.ui.baseComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R

@Composable
fun PamIconButton(
    iconButtons: IconButtons,
    onIconButtonClicked: () -> Unit
) {
    val imageVectorIcon: ImageVector = when (iconButtons) {
        IconButtons.HOME -> Icons.Outlined.Home
        IconButtons.OPERATION -> ImageVector.vectorResource(id = R.drawable.ic_outline_payments_24)
        IconButtons.PAYMENT -> ImageVector.vectorResource(id = R.drawable.ic_outline_ios_share_24)
        IconButtons.TRANSFER -> ImageVector.vectorResource(id = R.drawable.ic_baseline_swap_horiz_24)
        IconButtons.CHART -> ImageVector.vectorResource(id = R.drawable.ic_outline_bar_chart_24)
        IconButtons.ADD -> Icons.Outlined.Add
    }
    IconButton(onClick = onIconButtonClicked)
    {
        Surface(
            modifier = Modifier
                .background(Color.Black, CircleShape)
                .padding(5.dp),
            shape = CircleShape,
            color = Color.Black,
            border = BorderStroke(1.dp, Color.White),
        )
        {
            Icon(
                imageVector = imageVectorIcon,
                contentDescription = stringResource(iconButtons.contentDescription()),
                modifier = Modifier
                    .background(Color.Transparent, CircleShape)
                    .padding(4.dp),
                tint = Color.White
            )
        }
    }
}

enum class IconButtons {
    HOME {
        override fun contentDescription(): Int {
            return R.string.homeIconContentDescription
        }
    },
    OPERATION {
        override fun contentDescription(): Int {
            return R.string.operationIconContentDescription
        }
    },
    PAYMENT {
        override fun contentDescription(): Int {
            return R.string.paymentIconContentDescription
        }
    },
    TRANSFER {
        override fun contentDescription(): Int {
            return R.string.transferIconContentDescription
        }
    },
    CHART {
        override fun contentDescription(): Int {
            return R.string.chartIconContentDescription
        }
    },
    ADD {
        override fun contentDescription(): Int {
            return R.string.addIconContentDescription
        }
    };

    abstract fun contentDescription(): Int
}

@Preview
@Composable
fun BaseIconPreview() {
    PamIconButton(
        onIconButtonClicked = {},
        iconButtons = IconButtons.CHART
    )
}

@Composable
fun BaseButton(
    text: String,
    onButtonClicked: () -> Unit
) {
    Button(
        onClick = onButtonClicked,
        shape = RoundedCornerShape(topStart = 10.dp, topEnd =  10.dp, bottomStart = 0.dp, bottomEnd =  0.dp),
        modifier = Modifier
            .width(100.dp)
        ,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.paddingFromBaseline(top = 0.dp,bottom =  20.dp)
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
            .background(Color.Black, RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, top = 20.dp ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BaseButton(text = stringResource(android.R.string.ok)) { onAcceptButtonClicked() }
        BaseButton(text = stringResource(android.R.string.cancel).uppercase()) { onDismissButtonClicked() }
    }
}

@Preview
@Composable
fun AcceptOrDismissPreview() {
    AcceptOrDismissButtons(
        onAcceptButtonClicked = { },
        onDismissButtonClicked = { }
    )
}