package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.MyAccountBodyRecyclerView
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.MyAccountDetailSheet
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.MyAccountDetailTitle
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BrownBackgroundAddButton
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.VerticalDispositionSheet
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.personalaccountmanager.ui.theme.ThinBorder
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.myAccountDetailScreenUiState
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.myAccountScreenUiState

@Composable
fun MyAccountsScreen(
    viewModel: AppViewModel,
) {
    if (myAccountScreenUiState.isVisible) MyAccountBody(viewModel)
    if (myAccountDetailScreenUiState.isVisible) MyAccountDetailBody(viewModel)
}

@Composable
private fun MyAccountBody(viewModel: AppViewModel) {
    VerticalDispositionSheet(
        body = {
            MyAccountBodyRecyclerView(
                onAccountClicked = { selectedAccount ->
                    //todo pass with navigator
                    viewModel.dispatchAction(
                        AppActions.MyAccountScreenAction.CloseScreen
                    )
                    viewModel.dispatchAction(
                        AppActions.MyAccountDetailScreenAction.InitScreen(selectedAccount = selectedAccount)
                    )
                },
                onDeleteAccountButtonClicked = { accountToDelete ->
                    viewModel.dispatchAction(
                        AppActions.DeleteAccountAction.InitPopUp(accountUiToDelete = accountToDelete)
                    )
                },
                allAccounts = myAccountScreenUiState.allAccounts
            )
        },
        footer = {
            BrownBackgroundAddButton(onAddButtonClicked = {
                viewModel.dispatchAction(
                    AppActions.AddAccountPopUpAction.InitPopUp
                )
            })
        },
        bodyModifier = Modifier.padding(bottom = LittleMarge)
    )
}

@Composable
fun MyAccountDetailBody(
    viewModel: AppViewModel
) {
    Column {
        VerticalDispositionSheet(
            header = {
                MyAccountDetailTitle(
                    onBackIconClick = {
                        //todo pass with navigator
                        viewModel.dispatchAction(AppActions.MyAccountDetailScreenAction.CloseScreen)
                        viewModel.dispatchAction(AppActions.MyAccountScreenAction.InitScreen)
                    },
                    accountName = myAccountDetailScreenUiState.selectedAccount.name
                )
            },
            body = {
                MyAccountDetailSheet(
                    allOperations = myAccountDetailScreenUiState.accountMonthlyOperations,
                    onDeleteItemButtonCLick = { operationToDelete ->
                        viewModel.dispatchAction(
                            AppActions.DeleteOperationPopUpAction.InitPopUp(operationToDelete)
                        )
                    },
                    accountBalance = myAccountDetailScreenUiState.selectedAccount.accountBalance,
                    accountRest = myAccountDetailScreenUiState.selectedAccount.rest,
                    onOperationNameClick = {
                        viewModel.dispatchAction(
                            AppActions.MyAccountDetailScreenAction.GetSelectedOperation(it)
                        )
                    }
                )
            },
            footer = {
                BrownBackgroundAddButton(onAddButtonClicked = {
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.InitPopUp(
                            selectedAccountId = myAccountDetailScreenUiState.selectedAccount.id
                        )
                    )
                })
            },
            headerAndBodyColumnModifier = Modifier
                .padding(bottom = RegularMarge)
                .border(
                    width = ThinBorder,
                    color = MaterialTheme.colors.onSecondary,
                    shape = RoundedCornerShape(
                        LittleMarge
                    )
                )
                .background(
                    color = MaterialTheme.colors.onPrimary, shape = RoundedCornerShape(
                        LittleMarge
                    )
                )
        )
    }
}