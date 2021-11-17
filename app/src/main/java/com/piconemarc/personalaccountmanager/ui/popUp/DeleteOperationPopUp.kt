package com.piconemarc.personalaccountmanager.ui.popUp


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
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates

@Composable
fun DeleteOperationPopUp(
    deleteOperationPopUpState: ViewModelInnerStates.DeleteOperationPopUpVMState,
    onDeleteOperationPopUpEvent: (AppActions.DeleteOperationPopUpAction) -> Unit
) {
    BaseDeletePopUp(
        deletePopUpTitle = if (deleteOperationPopUpState.operationToDelete is OperationUiModel) stringResource(
            R.string.deleteOperationPopUpTitle
        )
        else stringResource(R.string.deletePaymentOperationTitle),
        onAcceptButtonClicked = {
            onDeleteOperationPopUpEvent(
                AppActions.DeleteOperationPopUpAction.DeleteOperation(
                    deleteOperationPopUpState.operationToDelete
                )
            )
        },
        onDismiss = {
            onDeleteOperationPopUpEvent(
                AppActions.DeleteOperationPopUpAction.ClosePopUp
            )
        },
        isExpanded = deleteOperationPopUpState.isPopUpExpanded,
        body = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = deleteOperationPopUpState.operationToDelete.name,
                    modifier = Modifier.padding(vertical = LittleMarge),
                    style = MaterialTheme.typography.h3
                )
                Text(
                    modifier = Modifier.padding(vertical = LittleMarge),
                    text = "${deleteOperationPopUpState.operationToDelete.amount.toStringWithTwoDec()} ${
                        getCurrencySymbolForLocale(
                            currentLocale
                        )
                    }",
                    style = MaterialTheme.typography.body1
                )
                when (deleteOperationPopUpState.operationToDelete) {
                    is OperationUiModel -> {
                        DeleteOperationOptionCheckBox(
                            onCheckedChange = { isChecked ->
                                onDeleteOperationPopUpEvent(
                                    AppActions.DeleteOperationPopUpAction.UpdateIsDeletedPermanently(
                                        isChecked
                                    )
                                )
                            },
                            deleteOperationPopUpState = deleteOperationPopUpState
                        )
                        if ((deleteOperationPopUpState.operationToDelete as OperationUiModel).transferId != null) {
                            Text(
                                text = "${stringResource(R.string.deleteOperationPopUpTransferInformationMessage)} ${deleteOperationPopUpState.transferRelatedAccount.name}",
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
                        DeleteOperationOptionCheckBox(
                            onCheckedChange = { isChecked ->
                                onDeleteOperationPopUpEvent(
                                    AppActions.DeleteOperationPopUpAction.UpdateIsDeletedPermanently(
                                        isChecked
                                    )
                                )
                            },
                            deleteOperationPopUpState = deleteOperationPopUpState
                        )
                    }
                }
            }

        }
    )
}

@Composable
private fun DeleteOperationOptionCheckBox(
    onCheckedChange: (isChecked: Boolean) -> Unit,
    deleteOperationPopUpState: ViewModelInnerStates.DeleteOperationPopUpVMState
) {
    val deleteOperationOptionText =
        if (deleteOperationPopUpState.operationToDelete is OperationUiModel) stringResource(R.string.deleteOperationPopUpDeleteOperationRelatedPaymentMessage)
        else stringResource(R.string.deleteOperationPopUpDeletePaymentRelatedOperationMessage)

    if (deleteOperationPopUpState.operationToDelete is OperationUiModel &&
        (deleteOperationPopUpState.operationToDelete as OperationUiModel).paymentId != null
        || deleteOperationPopUpState.operationToDelete is PaymentUiModel &&
        (deleteOperationPopUpState.operationToDelete as PaymentUiModel).operationId != null
    ) {
        OptionCheckBox(
            onCheckedChange = onCheckedChange,
            isChecked = deleteOperationPopUpState.isRelatedOperationDeleted,
            optionText = deleteOperationOptionText
        )
    }
}

@Composable
fun OptionCheckBox(
    onCheckedChange: (isChecked: Boolean) -> Unit,
    isChecked: Boolean,
    optionText: String
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