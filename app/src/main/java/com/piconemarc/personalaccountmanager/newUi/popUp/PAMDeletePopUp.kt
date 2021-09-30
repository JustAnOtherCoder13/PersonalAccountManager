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
import com.piconemarc.personalaccountmanager.ui.theme.Positive
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.viewmodel.viewModel.ConfirmDeleteOperationPopUpScreenModel
import com.piconemarc.viewmodel.viewModel.DeletePopUpEvent
import com.piconemarc.viewmodel.viewModel.DeletePopUpState

@Composable
fun PAMDeleteOperationPopUp() {
    val screenModel = ConfirmDeleteOperationPopUpScreenModel()
    //if (screenModel.getState().isExpanded)
        PAMBaseDeletePopUp(
            elementToDelete = stringResource(R.string.operation),
            onAcceptButtonClicked = { screenModel.deleteOperation()},
            onCancelButtonClicked = { screenModel.collapse()},
            isExpanded = screenModel.getState().isExpanded,
            body = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = screenModel.getState().operation.operationName,
                        modifier = Modifier.padding(vertical = RegularMarge)
                    )
                    Text(
                        modifier = Modifier.padding(vertical = RegularMarge),
                        text = screenModel.getState().operation.operationAmount.toString(),
                        color = if (screenModel.getState().operation.operationAmount < 0) MaterialTheme.colors.error else Positive
                    )
                }
            }
        )
}