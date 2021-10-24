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
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.addAccountPopUpUiState

@Composable
fun PAMAddAccountPopUp(viewModel: AppViewModel) {
    PAMBasePopUp(
        title = PresentationDataModel(stringResource(R.string.addAccountPopUpTitle)),
        onAcceptButtonClicked = {
            viewModel.dispatchAction(AppActions.AddAccountPopUpAction.AddNewAccount(
                accountName = addAccountPopUpUiState.accountName,
                accountBalance = addAccountPopUpUiState.accountBalance,
                accountOverdraft = addAccountPopUpUiState.accountOverdraft
            ))
        },
        onDismiss = {
                    viewModel.dispatchAction(AppActions.AddAccountPopUpAction.ClosePopUp)
                    },
        isExpanded = addAccountPopUpUiState.isPopUpExpanded
    ) {
        Column(modifier = Modifier.padding(vertical = RegularMarge)) {
            PAMBrownBackgroundTextFieldItem(
                title = PresentationDataModel(stringResource(R.string.accountNameTitle)),
                onTextChange = { accountName ->
                    viewModel.dispatchAction(
                        AppActions.AddAccountPopUpAction.FillAccountName(accountName = accountName)
                    )
                },
                textValue = addAccountPopUpUiState.accountName,
                isPopUpExpanded = addAccountPopUpUiState.isPopUpExpanded,
                isError = addAccountPopUpUiState.isNameError,
                errorMsg = PresentationDataModel(stringResource(R.string.nameErrorMessage))
            )
            PAMBrownBackgroundAmountTextFieldItem(
                title = PresentationDataModel(stringResource(R.string.initialBalanceTitle)),
                onTextChange = { accountBalance ->
                    viewModel.dispatchAction(
                        AppActions.AddAccountPopUpAction.FillAccountBalance(accountBalance = accountBalance)
                    )
                },
                amountValue = addAccountPopUpUiState.accountBalance,
                isPopUpExpanded = addAccountPopUpUiState.isPopUpExpanded,
                isError = addAccountPopUpUiState.isBalanceError,
                errorMsg = PresentationDataModel(stringResource(R.string.balanceErrorMessage))
            )
            PAMBrownBackgroundAmountTextFieldItem(
                title = PresentationDataModel(stringResource(R.string.overdraftTitle)),
                onTextChange = { accountOverdraft ->
                    viewModel.dispatchAction(
                        AppActions.AddAccountPopUpAction.FillAccountOverdraft(accountOverdraft = accountOverdraft)
                    )
                },
                amountValue = addAccountPopUpUiState.accountOverdraft,
                isPopUpExpanded = addAccountPopUpUiState.isPopUpExpanded,
                isError = addAccountPopUpUiState.isOverdraftError,
                errorMsg = PresentationDataModel(stringResource(R.string.overdraftErrorMessage))
            )
        }
    }
}