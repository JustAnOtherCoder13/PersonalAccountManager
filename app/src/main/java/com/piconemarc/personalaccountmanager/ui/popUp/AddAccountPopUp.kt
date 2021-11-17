package com.piconemarc.personalaccountmanager.ui.popUp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BasePopUp
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BrownBackgroundAmountTextFieldItem
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BrownBackgroundTextFieldItem
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates


@Composable
fun AddAccountPopUp(
    onAddAccountPopUpEvent: (AppActions.AddAccountPopUpAction) -> Unit,
    addAccountPopUpState: ViewModelInnerStates.AddAccountPopUpVMState
) {
    BasePopUp(
        title = stringResource(R.string.addAccountPopUpTitle),
        onAcceptButtonClicked = {
            onAddAccountPopUpEvent(
                AppActions.AddAccountPopUpAction.AddNewAccount(
                    accountName = addAccountPopUpState.accountName,
                    accountBalance = addAccountPopUpState.accountBalance,
                    accountOverdraft = addAccountPopUpState.accountOverdraft,
                )
            )
        },
        onDismiss = { onAddAccountPopUpEvent(AppActions.AddAccountPopUpAction.ClosePopUp) },
        isExpanded = addAccountPopUpState.isVisible
    ) {
        Column(modifier = Modifier.padding(vertical = RegularMarge)) {
            BrownBackgroundTextFieldItem(
                title = stringResource(R.string.accountNameTitle),
                onTextChange = { accountName ->
                    onAddAccountPopUpEvent(
                        AppActions.AddAccountPopUpAction.FillAccountName(accountName = accountName)
                    )
                },
                textValue = addAccountPopUpState.accountName,
                isPopUpExpanded = addAccountPopUpState.isVisible,
                isError = addAccountPopUpState.isNameError,
                errorMsg = stringResource(R.string.nameErrorMessage)
            )
            BrownBackgroundAmountTextFieldItem(
                title = stringResource(R.string.initialBalanceTitle),
                onTextChange = { accountBalance ->
                    onAddAccountPopUpEvent(
                        AppActions.AddAccountPopUpAction.FillAccountBalance(accountBalance = accountBalance)
                    )
                },
                amountValue = addAccountPopUpState.accountBalance,
                isPopUpExpanded = addAccountPopUpState.isVisible,
                isError = addAccountPopUpState.isBalanceError,
                errorMsg = stringResource(R.string.balanceErrorMessage)
            )
            BrownBackgroundAmountTextFieldItem(
                title = stringResource(R.string.overdraftTitle),
                onTextChange = { accountOverdraft ->
                    onAddAccountPopUpEvent(
                        AppActions.AddAccountPopUpAction.FillAccountOverdraft(accountOverdraft = accountOverdraft)
                    )
                },
                amountValue = addAccountPopUpState.accountOverdraft,
                isPopUpExpanded = addAccountPopUpState.isVisible,
                isError = addAccountPopUpState.isOverdraftError,
                errorMsg = stringResource(R.string.overdraftErrorMessage)
            )
        }
    }
}