package com.piconemarc.personalaccountmanager.newUi.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.newUi.stateManager.pAMBasePopUpEnterExitAnimation
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.AcceptOrDismissButtons
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.PopUpTitle
import com.piconemarc.personalaccountmanager.ui.theme.BigMarge
import com.piconemarc.personalaccountmanager.ui.theme.Black
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge

@Composable
fun PAMBasePopUp(
    title: String,
    onAcceptButtonClicked: () -> Unit,
    onDismiss: () -> Unit,
    body: @Composable () -> Unit,
    isExpanded: Boolean
) {
    val transition = pAMBasePopUpEnterExitAnimation(isExpended = isExpanded)
    if (transition.alpha > 0f)
    Column(
        modifier = Modifier
            .background(Black.copy(alpha = transition.alpha))
            .fillMaxSize()
            .clickable { onDismiss() }

    ) {
        Row(
            modifier = Modifier
                .height(transition.size)
                .offset(y = transition.position)
                .padding(horizontal = RegularMarge, vertical = RegularMarge)
        ) {
            Card(
                elevation = BigMarge,
                backgroundColor = MaterialTheme.colors.secondary,
                shape = MaterialTheme.shapes.large.copy(topStart = CornerSize(0.dp)),
                border = BorderStroke(LittleMarge, MaterialTheme.colors.primaryVariant),
            ) {
                Column {
                    PopUpTitle(title)
                    body()
                    AcceptOrDismissButtons(
                        onAcceptButtonClicked = onAcceptButtonClicked,
                        onDismissButtonClicked = onDismiss
                    )
                }
            }
        }
    }
}


@Composable
fun PAMBaseDeletePopUp(
    elementToDelete: String,
    onAcceptButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    isExpanded: Boolean,
    body: @Composable () -> Unit
) {
    PAMBasePopUp(
        title = stringResource(R.string.deleteBaseMsg) + elementToDelete,
        onAcceptButtonClicked = onAcceptButtonClicked,
        onDismiss = onCancelButtonClicked,
        body = body,
        isExpanded = isExpanded
    )
}