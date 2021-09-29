package com.piconemarc.personalaccountmanager.newUi.popUp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.newUi.component.PAMBaseDeletePopUp
import com.piconemarc.personalaccountmanager.newUi.stateManager.ConfirmDeleteOperationPopUpScreenModel
import com.piconemarc.personalaccountmanager.ui.theme.Positive
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge

@Composable
fun PAMDeleteOperationPopUp() {
        PAMBaseDeletePopUp(
            elementToDelete = stringResource(R.string.operation),
            onAcceptButtonClicked = { ConfirmDeleteOperationPopUpScreenModel().deleteOperation() },
            onCancelButtonClicked = { ConfirmDeleteOperationPopUpScreenModel().collapse()},
            isExpanded = ConfirmDeleteOperationPopUpScreenModel().getState().isExpanded,
            body = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = ConfirmDeleteOperationPopUpScreenModel().getState().operation.operationName,
                        modifier = Modifier.padding(vertical = RegularMarge)
                    )
                    Text(
                        modifier = Modifier.padding(vertical = RegularMarge),
                        text = ConfirmDeleteOperationPopUpScreenModel().getState().operation.operationAmount.toString(),
                        color = if (ConfirmDeleteOperationPopUpScreenModel().getState().operation.operationAmount < 0) MaterialTheme.colors.error else Positive
                    )
                }
            }
        )
}