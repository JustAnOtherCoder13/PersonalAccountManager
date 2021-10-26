package com.piconemarc.personalaccountmanager.ui.component.popUp


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMBaseDeletePopUp
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.personalaccountmanager.ui.theme.NegativeText
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.personalaccountmanager.ui.theme.ThinBorder
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.deleteOperationPopUpUiState

@Composable
fun PAMDeleteOperationPopUp(viewModel: AppViewModel) {
    PAMBaseDeletePopUp(
        deletePopUpTitle = "Delete Operation",
        onAcceptButtonClicked = {
            viewModel.dispatchAction(
                AppActions.DeleteOperationPopUpAction.DeleteOperation(
                    deleteOperationPopUpUiState.operationToDelete
                )
            )
        },
        onDismiss = {
            viewModel.dispatchAction(
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
                    modifier = Modifier.padding(vertical = LittleMarge),
                    style = MaterialTheme.typography.h3
                )
                Text(
                    modifier = Modifier.padding(vertical = LittleMarge),
                    text = deleteOperationPopUpUiState.operationToDelete.amount.toString() + " €",
                    style = MaterialTheme.typography.body1
                )

                if (deleteOperationPopUpUiState.operationToDelete.paymentId != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            modifier = Modifier
                                .padding(end = RegularMarge)
                                .border(
                                width = ThinBorder,
                                shape = RoundedCornerShape(LittleMarge),
                                color = MaterialTheme.colors.onSecondary
                            ),
                            checked = deleteOperationPopUpUiState.isDeletedPermanently,
                            onCheckedChange = {
                                viewModel.dispatchAction(
                                    AppActions.DeleteOperationPopUpAction.UpdateIsDeletedPermanently(it)
                                )
                            }
                        )
                        Text(
                            text = "Delete permanently?",
                            modifier = Modifier.padding(vertical = RegularMarge),
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
            if (deleteOperationPopUpUiState.operationToDelete.transferId != null) {
                Text(
                    text = "This operation is transfer, and will be deleted from ${deleteOperationPopUpUiState.transferRelatedAccount.name}",
                    modifier = Modifier.padding(RegularMarge).fillMaxWidth(),
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Center,
                    color = NegativeText
                )
            }
        }
    )
}
