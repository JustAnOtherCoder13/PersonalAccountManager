package com.piconemarc.personalaccountmanager.ui.baseComponent.addOperationPopUp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.personalaccountmanager.ui.theme.ThinBorder

//Work well if pop up is not init on app start, add if condition on AddOperationPopUp
@Composable
fun PAMIconButton(
    onIconButtonClicked: (iconName: Int) -> Unit,
    iconButton: IconButtons
) {
    IconButton(onClick = { onIconButtonClicked(iconButton.getIconName()) })
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
                imageVector = ImageVector.vectorResource(iconButton.getVectorIcon()),
                contentDescription = stringResource(iconButton.getIconContentDescription()),
                modifier = Modifier
                    .background(Color.Transparent, CircleShape)
                    .padding(LittleMarge),
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

enum class IconButtons {
    HOME {
        override fun getIconContentDescription(): Int {
            return R.string.homeIconContentDescription
        }

        override fun getVectorIcon(): Int {
            return R.drawable.ic_outline_home_24
        }

        override fun getIconName(): Int {
            return R.string.homeIconContentDescription
        }
    },
    OPERATION {
        override fun getIconContentDescription(): Int {
            return R.string.operationIconContentDescription
        }

        override fun getVectorIcon(): Int {
            return R.drawable.ic_outline_payments_24
        }

        override fun getIconName(): Int {
            return R.string.operation
        }
    },
    PAYMENT {
        override fun getIconContentDescription(): Int {
            return R.string.paymentIconContentDescription
        }

        override fun getVectorIcon(): Int {
            return R.drawable.ic_outline_ios_share_24
        }

        override fun getIconName(): Int {
            return R.string.payment
        }
    },
    TRANSFER {
        override fun getIconContentDescription(): Int {
            return R.string.transferIconContentDescription
        }

        override fun getVectorIcon(): Int {
            return R.drawable.ic_baseline_swap_horiz_24
        }

        override fun getIconName(): Int {
            return R.string.transfer
        }
    },
    CHART {
        override fun getIconContentDescription(): Int {
            return R.string.chartIconContentDescription
        }

        override fun getVectorIcon(): Int {
            return R.drawable.ic_outline_bar_chart_24
        }

        override fun getIconName(): Int {
            return 0
        }
    },
    ADD {
        override fun getIconContentDescription(): Int {
            return R.string.addIconContentDescription
        }

        override fun getVectorIcon(): Int {
            return R.drawable.ic_baseline_add_24
        }

        override fun getIconName(): Int {
            return 0
        }
    };

    abstract fun getIconContentDescription(): Int
    abstract fun getVectorIcon(): Int
    abstract fun getIconName(): Int
}