package com.piconemarc.personalaccountmanager.ui.component.popUp


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMBaseDeletePopUp
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.viewmodel.viewModel.AppActionDispatcher
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.deleteOperationPopUpUiState

@Composable
fun PAMDeleteOperationPopUp(actionDispatcher: AppActionDispatcher) {
    PAMBaseDeletePopUp(
        deletePopUpTitle = PresentationDataModel("Delete Operation"),
        onAcceptButtonClicked = {
                                actionDispatcher.dispatchAction(
                                    AppActions.DeleteOperationPopUpAction.DeleteOperation(
                                       deleteOperationPopUpUiState.operationToDelete
                                    )
                                )
        },
        onDismiss = {
                    actionDispatcher.dispatchAction(
                        AppActions.DeleteOperationPopUpAction.ClosePopUp
                    )
        },
        isExpanded = deleteOperationPopUpUiState.isPopUpExpanded,
        body = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = deleteOperationPopUpUiState.operationToDelete.name,
                    modifier = Modifier.padding(vertical = RegularMarge)
                )
                Text(
                    modifier = Modifier.padding(vertical = RegularMarge),
                    text = deleteOperationPopUpUiState.operationToDelete.amount.toString()+" €",
                )
            }
        }
    )
}
