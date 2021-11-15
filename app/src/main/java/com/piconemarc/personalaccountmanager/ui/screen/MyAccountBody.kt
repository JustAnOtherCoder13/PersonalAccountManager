package com.piconemarc.personalaccountmanager.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.piconemarc.personalaccountmanager.NavDestinations
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.MyAccountBodyRecyclerView
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.MyAccountDetailSheet
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.MyAccountDetailTitle
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BrownBackgroundAddButton
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.VerticalDispositionSheet
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.personalaccountmanager.ui.theme.ThinBorder
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates


@Composable
fun MyAccountBody(
    myAccountState: ViewModelInnerStates.MyAccountScreenVMState,
    navController: NavController,
    onAddAccountButtonClick : (AppActions.AddAccountPopUpAction)-> Unit,
    onDeleteAccountButtonClick : ( AppActions.DeleteAccountAction)-> Unit,
) {
    VerticalDispositionSheet(
        body = {
            MyAccountBodyRecyclerView(
                onAccountClicked = { selectedAccount ->
                    NavDestinations.myAccountDetail.doNavigation(navController = navController, argument = selectedAccount.id.toString())
                },
                onDeleteAccountButtonClicked = { accountToDelete ->
                    onDeleteAccountButtonClick(
                        AppActions.DeleteAccountAction.InitPopUp(accountUiToDelete = accountToDelete)
                    )
                },
                allAccounts = myAccountState.allAccounts
            )
        },
        footer = {
            BrownBackgroundAddButton(onAddButtonClicked = {
                onAddAccountButtonClick(
                    AppActions.AddAccountPopUpAction.InitPopUp
                )
            })
        },
        bodyModifier = Modifier.padding(bottom = LittleMarge)
    )
}

@Composable
fun MyAccountDetailBody(
    navController: NavController,
    selectedAccountId: String?,
    myAccountDetailState: ViewModelInnerStates.MyAccountDetailScreenVMState,
    onMyAccountDetailEvent: (AppActions.MyAccountDetailScreenAction) -> Unit,
    onAddOperationButtonClick: (AppActions.AddOperationPopupAction) -> Unit,
    onDeleteOperationButtonClick: (AppActions.DeleteOperationPopUpAction) -> Unit
) {

    onMyAccountDetailEvent(
        AppActions.MyAccountDetailScreenAction.InitScreen(
            selectedAccountId ?: ""
        )
    )
    Column {
        VerticalDispositionSheet(
            header = {
                MyAccountDetailTitle(
                    onBackIconClick = { NavDestinations.Home.doNavigation(navController)},
                    accountName = myAccountDetailState.selectedAccount.name
                )
            },
            body = {
                MyAccountDetailSheet(
                    allOperations = myAccountDetailState.accountMonthlyOperations,
                    onDeleteItemButtonCLick = { operationToDelete ->
                        onDeleteOperationButtonClick(
                            AppActions.DeleteOperationPopUpAction.InitPopUp(operationToDelete)
                        )
                    },
                    accountBalance = myAccountDetailState.selectedAccount.accountBalance,
                    accountRest = myAccountDetailState.selectedAccount.rest,
                    onOperationNameClick = {
                        onMyAccountDetailEvent(
                            AppActions.MyAccountDetailScreenAction.GetSelectedOperation(it)
                        )
                    },
                    operationDetailMessage = myAccountDetailState.operationDetailMessage
                )
            },
            footer = {
                BrownBackgroundAddButton(onAddButtonClicked = {
                    onAddOperationButtonClick(
                        AppActions.AddOperationPopupAction.InitPopUp(
                            selectedAccountId = myAccountDetailState.selectedAccount.id
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