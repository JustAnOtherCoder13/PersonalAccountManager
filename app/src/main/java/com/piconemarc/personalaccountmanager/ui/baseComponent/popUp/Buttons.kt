package com.piconemarc.personalaccountmanager.ui.baseComponent.popUp

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
import com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.states.UiStates
import com.piconemarc.personalaccountmanager.ui.theme.BigMarge
import com.piconemarc.personalaccountmanager.ui.theme.ButtonShape
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.personalaccountmanager.ui.theme.ThinBorder

@Composable
fun BaseButton(
    text: String,
    onButtonClicked: () -> Unit
) {
    Button(
        onClick = onButtonClicked,
        shape = ButtonShape,
        modifier = Modifier
            .width(120.dp),
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
            .padding(start = BigMarge, end = BigMarge, top = BigMarge),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BaseButton(text = stringResource(R.string.ok)) { onAcceptButtonClicked() }
        BaseButton(text = stringResource(R.string.cancel)) { onDismissButtonClicked() }
    }
}

@Composable
fun PAMIconButton(
    onIconButtonClicked: (iconName: UiStates.AddOperationPopUpLeftSideIconState) -> Unit,
    iconButtonType: UiStates.AddOperationPopUpLeftSideIconState
) {
    IconButton(onClick = { onIconButtonClicked(iconButtonType) })
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
                imageVector = ImageVector.vectorResource(iconButtonType.getIcon().getVectorIcon()),
                contentDescription = stringResource(iconButtonType.getIcon().getIconContentDescription()),
                modifier = Modifier
                    .background(Color.Transparent, CircleShape)
                    .padding(LittleMarge),
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}