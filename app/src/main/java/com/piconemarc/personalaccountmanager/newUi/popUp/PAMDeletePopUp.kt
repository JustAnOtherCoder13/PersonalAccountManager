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
import com.piconemarc.personalaccountmanager.newUi.stateManager.deletePopUp.DeleteOperationPopUpEvents
import com.piconemarc.personalaccountmanager.newUi.stateManager.deletePopUp.DeleteOperationPopUpState
import com.piconemarc.personalaccountmanager.newUi.stateManager.deletePopUp.DeleteOperationPopUpStates
import com.piconemarc.personalaccountmanager.newUi.stateManager.deletePopUp.deleteOperationEventHandler
import com.piconemarc.personalaccountmanager.ui.theme.Positive
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.personalaccountmanager.ui.theme.deleteOperationTextModifier

@Composable
fun PAMDeleteOperationPopUp() {
        PAMBaseDeletePopUp(
            elementToDelete = stringResource(R.string.operation),
            onAcceptButtonClicked = { deleteOperationEventHandler(DeleteOperationPopUpEvents.OnDeleteOperation) },
            onCancelButtonClicked = { deleteOperationEventHandler(DeleteOperationPopUpEvents.OnDismiss)},
            isExpanded = DeleteOperationPopUpStates.deleteOperationPopUpState == DeleteOperationPopUpState.EXPAND,
            body = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = DeleteOperationPopUpStates.operationToDeleteName,
                        modifier = Modifier.padding(vertical = RegularMarge)
                    )
                    Text(
                        modifier = Modifier.padding(vertical = RegularMarge),
                        text = DeleteOperationPopUpStates.operationToDeleteAmount.toString(),
                        color = if (DeleteOperationPopUpStates.operationToDeleteAmount < 0) MaterialTheme.colors.error else Positive
                    )
                }
            }
        )
}