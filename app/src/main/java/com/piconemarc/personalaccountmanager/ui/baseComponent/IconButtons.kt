package com.piconemarc.personalaccountmanager.ui.baseComponent

import android.content.res.Resources
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
                contentDescription = Resources.getSystem().getString(iconButtons.contentDescription()),
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