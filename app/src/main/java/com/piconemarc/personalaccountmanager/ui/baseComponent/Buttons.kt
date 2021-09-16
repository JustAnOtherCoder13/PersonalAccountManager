package com.piconemarc.personalaccountmanager.ui.baseComponent

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.theme.BigMarge
import com.piconemarc.personalaccountmanager.ui.theme.ButtonShape
import com.piconemarc.personalaccountmanager.ui.theme.PersonalAccountManagerTheme
import com.piconemarc.personalaccountmanager.ui.theme.XlMarge

@Composable
fun PamIconButton(
    iconButton: IconButtons,
    onIconButtonClicked: () -> Unit
) {
    IconButton(onClick = onIconButtonClicked)
    {
        Surface(
            modifier = Modifier
                .background(MaterialTheme.colors.primary, CircleShape)
                .padding(5.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.primary,
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
        )
        {
            Icon(
                imageVector = ImageVector.vectorResource(iconButton.vectorIcon()),
                contentDescription = stringResource(iconButton.contentDescription()),
                modifier = Modifier
                    .background(Color.Transparent, CircleShape)
                    .padding(4.dp),
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

enum class IconButtons {
    HOME {
        override fun contentDescription(): Int {
            return R.string.homeIconContentDescription
        }

        override fun vectorIcon(): Int {
            return R.drawable.ic_outline_home_24
        }
    },
    OPERATION {
        override fun contentDescription(): Int {
            return R.string.operationIconContentDescription
        }

        override fun vectorIcon(): Int {
            return R.drawable.ic_outline_payments_24
        }
    },
    PAYMENT {
        override fun contentDescription(): Int {
            return R.string.paymentIconContentDescription
        }

        override fun vectorIcon(): Int {
            return R.drawable.ic_outline_ios_share_24
        }
    },
    TRANSFER {
        override fun contentDescription(): Int {
            return R.string.transferIconContentDescription
        }

        override fun vectorIcon(): Int {
            return R.drawable.ic_baseline_swap_horiz_24
        }
    },
    CHART {
        override fun contentDescription(): Int {
            return R.string.chartIconContentDescription
        }

        override fun vectorIcon(): Int {
            return R.drawable.ic_outline_bar_chart_24
        }
    },
    ADD {
        override fun contentDescription(): Int {
            return R.string.addIconContentDescription
        }

        override fun vectorIcon(): Int {
            return R.drawable.ic_baseline_add_24
        }
    };

    abstract fun contentDescription(): Int
    abstract fun vectorIcon(): Int
}

@Preview
@Composable
fun BaseIconPreview() {
    PamIconButton(
        onIconButtonClicked = {},
        iconButton = IconButtons.CHART
    )
}

@Composable
fun BaseButton(
    text: String,
    onButtonClicked: () -> Unit
) {
    Button(
        onClick = onButtonClicked,
        shape = ButtonShape,
        modifier = Modifier
            .width(130.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = MaterialTheme.colors.secondary,
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
            .background(MaterialTheme.colors.primary, MaterialTheme.shapes.large)
            .fillMaxWidth()
            .padding(start = XlMarge, end = XlMarge, top = BigMarge),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BaseButton(text = stringResource(android.R.string.ok)) { onAcceptButtonClicked() }
        BaseButton(text = stringResource(android.R.string.cancel).uppercase()) { onDismissButtonClicked() }
    }
}

@Preview
@Composable
fun AcceptOrDismissPreview() {
    PersonalAccountManagerTheme {
        AcceptOrDismissButtons(
            onAcceptButtonClicked = { },
            onDismissButtonClicked = { }
        )
    }
}