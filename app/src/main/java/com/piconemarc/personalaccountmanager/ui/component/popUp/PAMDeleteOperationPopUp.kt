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
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.model.entity.PaymentUiModel
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
                when (deleteOperationPopUpUiState.operationToDelete) {
                    is OperationUiModel -> {
                        DeleteOperationOptionCheckBox(viewModel)

                        if ((deleteOperationPopUpUiState.operationToDelete as OperationUiModel).transferId != null) {
                            Text(
                                text = "This operation is a transfer, and will be deleted from ${deleteOperationPopUpUiState.transferRelatedAccount.name}",
                                modifier = Modifier
                                    .padding(RegularMarge)
                                    .fillMaxWidth(),
                                style = MaterialTheme.typography.caption,
                                textAlign = TextAlign.Center,
                                color = NegativeText
                            )
                        }
                    }
                    is PaymentUiModel -> {
                        DeleteOperationOptionCheckBox(viewModel)
                    }
                }
            }

        }
    )
}

@Composable
private fun DeleteOperationOptionCheckBox(viewModel: AppViewModel) {
    val deleteOperationOptionText =
    if (deleteOperationPopUpUiState.operationToDelete is OperationUiModel)"Delete permanently?"
    else "Delete associated Operation?"


    if (deleteOperationPopUpUiState.operationToDelete is OperationUiModel &&
        (deleteOperationPopUpUiState.operationToDelete as OperationUiModel).paymentId != null
        || deleteOperationPopUpUiState.operationToDelete is PaymentUiModel &&
        (deleteOperationPopUpUiState.operationToDelete as PaymentUiModel).operationId != null

    ) {
        OptionCheckBox(
            onCheckedChange = {
                viewModel.dispatchAction(
                    AppActions.DeleteOperationPopUpAction.UpdateIsDeletedPermanently(it)
                )
            },
            isChecked = deleteOperationPopUpUiState.isRelatedOperationDeleted,
            optionText = deleteOperationOptionText
        )
    }
}

@Composable
fun OptionCheckBox(
    onCheckedChange : (isChecked : Boolean)-> Unit,
    isChecked : Boolean,
    optionText : String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            modifier = Modifier
                .padding(end = RegularMarge)
                .border(
                    width = ThinBorder,
                    shape = RoundedCornerShape(LittleMarge),
                    color = MaterialTheme.colors.onSecondary
                ),
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = optionText,
            modifier = Modifier.padding(vertical = RegularMarge),
            style = MaterialTheme.typography.body2
        )
    }
}
