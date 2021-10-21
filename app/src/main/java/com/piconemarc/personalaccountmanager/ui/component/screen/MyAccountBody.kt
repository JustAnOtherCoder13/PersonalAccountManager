package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.AccountPostIt
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMAddButton
import com.piconemarc.viewmodel.viewModel.AppActionDispatcher
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppSubscriber

@Composable
fun MyAccountsBody(actionDispatcher: AppActionDispatcher) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn() {
            items(AppSubscriber.GlobalUiState.baseAppScreenUiState.allAccounts) { account ->
                AccountPostIt(
                    accountName =  PresentationDataModel(
                        stringValue = account.name,
                        objectIdReference = account.id
                    ),
                    onDeleteAccountButtonClicked = { accountName ->
                        actionDispatcher.dispatchAction(
                            AppActions.DeleteAccountAction.InitPopUp(accountName = accountName)
                        ) },
                    accountBalanceValue = PresentationDataModel(
                        stringValue = account.accountBalance.toString(),
                        objectIdReference = account.id
                    ),
                    accountRestValue = PresentationDataModel(
                        stringValue = (account.accountOverdraft + (account.accountBalance)).toString(),
                        objectIdReference = account.id
                    ),
                    onAccountClicked = {accountName ->
                        //todo go to detail
                    }
                )
            }
        }
        PAMAddButton(
            onAddButtonClicked = {
                actionDispatcher.dispatchAction(
                    AppActions.AddAccountPopUpAction.InitPopUp
                )
            }
        )
    }
}