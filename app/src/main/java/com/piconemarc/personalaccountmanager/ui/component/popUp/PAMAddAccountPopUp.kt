package com.piconemarc.personalaccountmanager.ui.component.popUp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMBrownBackgroundAmountTextFieldItem
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMBasePopUp
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMBrownBackgroundTextFieldItem
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.viewmodel.viewModel.AppActionDispatcher
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.addAccountPopUpVMState

@Composable
fun PAMAddAccountPopUp(actionDispatcher: AppActionDispatcher) {

    PAMBasePopUp(
        title = PresentationDataModel("Add new Account"),
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
                title = PresentationDataModel("Account Name"),
                onTextChange = { accountName ->
                    actionDispatcher.dispatchAction(
                        AppActions.AddAccountPopUpAction.FillAccountName(accountName = accountName)
                    )
                },
                textValue = addAccountPopUpVMState.accountName,
                isPopUpExpanded = addAccountPopUpVMState.isPopUpExpanded,
                isError = addAccountPopUpVMState.isNameError,
                errorMsg = PresentationDataModel("You have to fill the name before validate")
            )
            PAMBrownBackgroundAmountTextFieldItem(
                title = PresentationDataModel("Initial Balance"),
                onTextChange = { accountBalance ->
                    actionDispatcher.dispatchAction(
                        AppActions.AddAccountPopUpAction.FillAccountBalance(accountBalance = accountBalance)
                    )
                },
                amountValue = addAccountPopUpVMState.accountBalance,
                isPopUpExpanded = addAccountPopUpVMState.isPopUpExpanded,
                isError = addAccountPopUpVMState.isBalanceError,
                errorMsg = PresentationDataModel("If you don't fill balance, it will be set to 0.0")
            )
            PAMBrownBackgroundAmountTextFieldItem(
                title = PresentationDataModel("Overdraft"),
                onTextChange = { accountOverdraft ->
                    actionDispatcher.dispatchAction(
                        AppActions.AddAccountPopUpAction.FillAccountOverdraft(accountOverdraft = accountOverdraft)
                    )
                },
                amountValue = addAccountPopUpVMState.accountOverdraft,
                isPopUpExpanded = addAccountPopUpVMState.isPopUpExpanded,
                isError = addAccountPopUpVMState.isOverdraftError,
                errorMsg = PresentationDataModel("If you don't fill overdraft, it will be set to 0.0")
            )
        }


    }
}