package com.piconemarc.personalaccountmanager.ui.component.popUp

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
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.addAccountPopUpUiState


@Composable
fun AddAccountPopUp(
    viewModel: AppViewModel,
) {
    BasePopUp(
        title = stringResource(R.string.addAccountPopUpTitle),
        onAcceptButtonClicked = {
            viewModel.dispatchAction(AppActions.AddAccountPopUpAction.AddNewAccount(
                accountName = addAccountPopUpUiState.accountName,
                accountBalance = addAccountPopUpUiState.accountBalance,
                accountOverdraft = addAccountPopUpUiState.accountOverdraft
            ))
        },
        onDismiss = { viewModel.dispatchAction(AppActions.AddAccountPopUpAction.ClosePopUp) },
        isExpanded = addAccountPopUpUiState.isPopUpExpanded
    ) {
        Column(modifier = Modifier.padding(vertical = RegularMarge)) {
            BrownBackgroundTextFieldItem(
                title = stringResource(R.string.accountNameTitle),
                onTextChange = { accountName ->
                    viewModel.dispatchAction(
                        AppActions.AddAccountPopUpAction.FillAccountName(accountName = accountName)
                    )
                },
                textValue = addAccountPopUpUiState.accountName,
                isPopUpExpanded = addAccountPopUpUiState.isPopUpExpanded,
                isError = addAccountPopUpUiState.isNameError,
                errorMsg = stringResource(R.string.nameErrorMessage)
            )
            BrownBackgroundAmountTextFieldItem(
                title = stringResource(R.string.initialBalanceTitle),
                onTextChange = { accountBalance ->
                    viewModel.dispatchAction(
                        AppActions.AddAccountPopUpAction.FillAccountBalance(accountBalance = accountBalance)
                    )
                },
                amountValue = addAccountPopUpUiState.accountBalance,
                isPopUpExpanded = addAccountPopUpUiState.isPopUpExpanded,
                isError = addAccountPopUpUiState.isBalanceError,
                errorMsg = stringResource(R.string.balanceErrorMessage)
            )
            BrownBackgroundAmountTextFieldItem(
                title = stringResource(R.string.overdraftTitle),
                onTextChange = { accountOverdraft ->
                    viewModel.dispatchAction(
                        AppActions.AddAccountPopUpAction.FillAccountOverdraft(accountOverdraft = accountOverdraft)
                    )
                },
                amountValue = addAccountPopUpUiState.accountOverdraft,
                isPopUpExpanded = addAccountPopUpUiState.isPopUpExpanded,
                isError = addAccountPopUpUiState.isOverdraftError,
                errorMsg = stringResource(R.string.overdraftErrorMessage)
            )
        }
    }
}