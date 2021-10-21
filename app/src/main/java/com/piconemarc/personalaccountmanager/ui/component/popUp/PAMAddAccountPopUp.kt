package com.piconemarc.personalaccountmanager.ui.component.popUp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMBasePopUp
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMBrownBackgroundAmountTextFieldItem
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMBrownBackgroundTextFieldItem
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.viewmodel.viewModel.AppActionDispatcher
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.addAccountPopUpVMState

@Composable
fun PAMAddAccountPopUp(actionDispatcher: AppActionDispatcher) {

    PAMBasePopUp(
        title = PresentationDataModel(stringResource(R.string.addAccountPopUpTitle)),
        onAcceptButtonClicked = {
            actionDispatcher.dispatchAction(AppActions.AddAccountPopUpAction.AddNewAccount(
                accountName = addAccountPopUpVMState.accountName,
                accountBalance = addAccountPopUpVMState.accountBalance,
                accountOverdraft = addAccountPopUpVMState.accountOverdraft
            ))
        },
        onDismiss = {
                    actionDispatcher.dispatchAction(AppActions.AddAccountPopUpAction.ClosePopUp)
                    },
        isExpanded = addAccountPopUpVMState.isPopUpExpanded
    ) {
        Column(modifier = Modifier.padding(vertical = RegularMarge)) {
            PAMBrownBackgroundTextFieldItem(
                title = PresentationDataModel(stringResource(R.string.accountNameTitle)),
                onTextChange = { accountName ->
                    actionDispatcher.dispatchAction(
                        AppActions.AddAccountPopUpAction.FillAccountName(accountName = accountName)
                    )
                },
                textValue = addAccountPopUpVMState.accountName,
                isPopUpExpanded = addAccountPopUpVMState.isPopUpExpanded,
                isError = addAccountPopUpVMState.isNameError,
                errorMsg = PresentationDataModel(stringResource(R.string.nameErrorMessage))
            )
            PAMBrownBackgroundAmountTextFieldItem(
                title = PresentationDataModel(stringResource(R.string.initialBalanceTitle)),
                onTextChange = { accountBalance ->
                    actionDispatcher.dispatchAction(
                        AppActions.AddAccountPopUpAction.FillAccountBalance(accountBalance = accountBalance)
                    )
                },
                amountValue = addAccountPopUpVMState.accountBalance,
                isPopUpExpanded = addAccountPopUpVMState.isPopUpExpanded,
                isError = addAccountPopUpVMState.isBalanceError,
                errorMsg = PresentationDataModel(stringResource(R.string.balanceErrorMessage))
            )
            PAMBrownBackgroundAmountTextFieldItem(
                title = PresentationDataModel(stringResource(R.string.overdraftTitle)),
                onTextChange = { accountOverdraft ->
                    actionDispatcher.dispatchAction(
                        AppActions.AddAccountPopUpAction.FillAccountOverdraft(accountOverdraft = accountOverdraft)
                    )
                },
                amountValue = addAccountPopUpVMState.accountOverdraft,
                isPopUpExpanded = addAccountPopUpVMState.isPopUpExpanded,
                isError = addAccountPopUpVMState.isOverdraftError,
                errorMsg = PresentationDataModel(stringResource(R.string.overdraftErrorMessage))
            )
        }
    }
}