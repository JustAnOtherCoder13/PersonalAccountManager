package com.piconemarc.personalaccountmanager.ui.component.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.piconemarc.model.entity.OperationModel
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.AccountPostIt
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMAddButton
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMDeleteOperationPopUp
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.viewmodel.viewModel.AppActionDispatcher
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppSubscriber
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.baseAppScreenUiState
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.myAccountDetailScreenUiState
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.myAccountScreenUiState

@Composable
fun MyAccountsBody(
    actionDispatcher: AppActionDispatcher,
    onDeleteOperationButtonClick : (operation : OperationModel)-> Unit,
) {
    if (myAccountScreenUiState.isVisible)
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(
                modifier = Modifier
                .weight(1f)
                .padding(bottom = LittleMarge)
            ) {
                items(myAccountScreenUiState.allAccounts) { account ->
                    AccountPostIt(
                        accountName = PresentationDataModel(
                            stringValue = account.name,
                            objectIdReference = account.id
                        ),
                        onDeleteAccountButtonClicked = { accountName ->
                            actionDispatcher.dispatchAction(
                                AppActions.DeleteAccountAction.InitPopUp(accountName = accountName)
                            )
                        },
                        accountBalanceValue = PresentationDataModel(
                            stringValue = account.accountBalance.toString(),
                            objectIdReference = account.id
                        ),
                        accountRestValue = PresentationDataModel(
                            stringValue = (account.accountOverdraft + (account.accountBalance)).toString(),
                            objectIdReference = account.id
                        ),
                        onAccountClicked = { selectedAccount ->
                            actionDispatcher.dispatchAction(
                                AppActions.MyAccountScreenAction.CloseScreen
                            )
                            actionDispatcher.dispatchAction(
                                AppActions.MyAccountDetailScreenAction.InitScreen(selectedAccount = selectedAccount)
                            )
                        },
                        selectedAccount = account
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
    if (myAccountDetailScreenUiState.isVisible)
        MyAccountDetailBody(
            onDeleteItemButtonCLick = {
                onDeleteOperationButtonClick(it)
            },
            actionDispatcher = actionDispatcher
        )
}
