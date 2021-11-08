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
import com.piconemarc.viewmodel.UiState
import com.piconemarc.viewmodel.viewModel.*

@Composable
fun MyAccountsScreen(
    myAccountViewModel: MyAccountViewModel,
    myAccountDetailViewModel: MyAccountDetailViewModel,
    appViewModel: AppViewModel
) {
    if (myAccountViewModel.uiState.value.isVisible) MyAccountBody(
        myAccountViewModel,
        myAccountDetailViewModel,
        appViewModel,
        myAccountUiStateValue = myAccountViewModel.uiState.value
    )

    if (myAccountDetailViewModel.uiState.value.isVisible) MyAccountDetailBody(myAccountDetailViewModel, myAccountViewModel, appViewModel)
}

@Composable
private fun MyAccountBody(
    myAccountViewModel: MyAccountViewModel,
    myAccountDetailViewModel: MyAccountDetailViewModel,
    appViewModel: AppViewModel,
    myAccountUiStateValue : ViewModelInnerStates.MyAccountScreenVMState

) {
    VerticalDispositionSheet(
        body = {
            MyAccountBodyRecyclerView(
                onAccountClicked = { selectedAccount ->
                    //todo pass with navigator
                    myAccountViewModel.dispatchAction(AppActions.MyAccountScreenAction.CloseScreen)
                    myAccountDetailViewModel.dispatchAction(AppActions.MyAccountDetailScreenAction.InitScreen(selectedAccount = selectedAccount))
                },
                onDeleteAccountButtonClicked = { accountToDelete ->
                    appViewModel.dispatchAction(
                        AppActions.DeleteAccountAction.InitPopUp(accountUiToDelete = accountToDelete)
                    )
                },
                allAccounts = myAccountViewModel.uiState.value.allAccounts
            )
        },
        footer = {
            BrownBackgroundAddButton(onAddButtonClicked = {
                appViewModel.dispatchAction(
                    AppActions.AddAccountPopUpAction.InitPopUp
                )
            })
        },
        bodyModifier = Modifier.padding(bottom = LittleMarge)
    )
}

@Composable
fun MyAccountDetailBody(
    myAccountDetailViewModel: MyAccountDetailViewModel,
    myAccountViewModel: MyAccountViewModel,
    appViewModel: AppViewModel
) {
    Column {

        VerticalDispositionSheet(
            header = {
                MyAccountDetailTitle(
                    onBackIconClick = {
                        //todo pass with navigator
                        myAccountDetailViewModel.dispatchAction(AppActions.MyAccountDetailScreenAction.CloseScreen)
                        myAccountViewModel.dispatchAction(AppActions.MyAccountScreenAction.InitScreen)
                    },
                    accountName = myAccountDetailViewModel.uiState.value.selectedAccount.name
                )
            },
            body = {
                MyAccountDetailSheet(
                    allOperations = myAccountDetailViewModel.uiState.value.accountMonthlyOperations,
                    onDeleteItemButtonCLick = { operationToDelete ->
                        appViewModel.dispatchAction(
                            AppActions.DeleteOperationPopUpAction.InitPopUp(operationToDelete)
                        )
                    },
                    accountBalance = myAccountDetailViewModel.uiState.value.selectedAccount.accountBalance,
                    accountRest = myAccountDetailViewModel.uiState.value.selectedAccount.rest,
                    onOperationNameClick = {
                        myAccountDetailViewModel.dispatchAction(
                            AppActions.MyAccountDetailScreenAction.GetSelectedOperation(it)
                        )
                    }
                )
            },
            footer = {
                BrownBackgroundAddButton(onAddButtonClicked = {
                    appViewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.InitPopUp(
                            selectedAccountId = myAccountDetailViewModel.uiState.value.selectedAccount.id
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