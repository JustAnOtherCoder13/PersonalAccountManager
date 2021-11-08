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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.model.entity.PaymentUiModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.currentLocale
import com.piconemarc.personalaccountmanager.getCurrencySymbolForLocale
import com.piconemarc.personalaccountmanager.toStringWithTwoDec
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BaseDeletePopUp
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.personalaccountmanager.ui.theme.NegativeText
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.personalaccountmanager.ui.theme.ThinBorder
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.deleteOperationPopUpUiState

@Composable
fun DeleteOperationPopUp(viewModel: AppViewModel) {
    BaseDeletePopUp(
        deletePopUpTitle = if (deleteOperationPopUpUiState.value.operationToDelete is OperationUiModel)stringResource(R.string.deleteOperationPopUpTitle)
        else stringResource(R.string.deletePaymentOperationTitle),
        onAcceptButtonClicked = {
            viewModel.dispatchAction(
                AppActions.DeleteOperationPopUpAction.DeleteOperation(
                    deleteOperationPopUpUiState.value.operationToDelete
                )
            )
        },
        onDismiss = {
            viewModel.dispatchAction(
                AppActions.DeleteOperationPopUpAction.ClosePopUp
            )
        },
        isExpanded = deleteOperationPopUpUiState.value.isPopUpExpanded,
        body = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = deleteOperationPopUpUiState.value.operationToDelete.name,
                    modifier = Modifier.padding(vertical = LittleMarge),
                    style = MaterialTheme.typography.h3
                )
                Text(
                    modifier = Modifier.padding(vertical = LittleMarge),
                    text = "${deleteOperationPopUpUiState.value.operationToDelete.amount.toStringWithTwoDec()} ${getCurrencySymbolForLocale(currentLocale)}",
                    style = MaterialTheme.typography.body1
                )
                when (deleteOperationPopUpUiState.value.operationToDelete) {
                    is OperationUiModel -> {
                        DeleteOperationOptionCheckBox(onCheckedChange = {isChecked ->
                            viewModel.dispatchAction(
                                AppActions.DeleteOperationPopUpAction.UpdateIsDeletedPermanently(isChecked)
                            )
                        })
                        if ((deleteOperationPopUpUiState.value.operationToDelete as OperationUiModel).transferId != null) {
                            Text(
                                text = "${stringResource(R.string.deleteOperationPopUpTransferInformationMessage)} ${deleteOperationPopUpUiState.value.transferRelatedAccount.name}",
                                modifier = Modifier
                                    .padding(RegularMarge)
                                    .fillMaxWidth(),
                                style = MaterialTheme.typography.caption,
                                textAlign = TextAlign.Center,
                                color = NegativeText
                            )
                        }
                    }
                    is PaymentUiModel -> { DeleteOperationOptionCheckBox(
                    onCheckedChange = {isChecked ->
                        viewModel.dispatchAction(
                            AppActions.DeleteOperationPopUpAction.UpdateIsDeletedPermanently(isChecked)
                        )
                    }) }
                }
            }

        }
    )
}

@Composable
private fun DeleteOperationOptionCheckBox(
    onCheckedChange: (isChecked: Boolean) -> Unit
) {
    val deleteOperationOptionText =
    if (deleteOperationPopUpUiState.value.operationToDelete is OperationUiModel) stringResource(R.string.deleteOperationPopUpDeleteOperationRelatedPaymentMessage)
    else stringResource(R.string.deleteOperationPopUpDeletePaymentRelatedOperationMessage)

    if (deleteOperationPopUpUiState.value.operationToDelete is OperationUiModel &&
        (deleteOperationPopUpUiState.value.operationToDelete as OperationUiModel).paymentId != null
        || deleteOperationPopUpUiState.value.operationToDelete is PaymentUiModel &&
        (deleteOperationPopUpUiState.value.operationToDelete as PaymentUiModel).operationId != null
    ) {
        OptionCheckBox(
            onCheckedChange = onCheckedChange,
            isChecked = deleteOperationPopUpUiState.value.isRelatedOperationDeleted,
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