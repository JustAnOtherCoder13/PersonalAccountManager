package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.*
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.personalaccountmanager.ui.theme.ThinBorder
import com.piconemarc.viewmodel.viewModel.AppActionDispatcher
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.myAccountDetailScreenUiState
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.myAccountScreenUiState

@Composable
fun MyAccountsSheet(
    actionDispatcher: AppActionDispatcher,
) {
    if (myAccountScreenUiState.isVisible) MyAccountBody(actionDispatcher)
    if (myAccountDetailScreenUiState.isVisible) MyAccountDetailBody(actionDispatcher)
}



@Composable
private fun MyAccountBody(actionDispatcher: AppActionDispatcher) {
    VerticalDispositionSheet(
        body = { MyAccountBodyRecyclerView(actionDispatcher) },
        footer = {
            PAMAddButton(onAddButtonClicked = {
                actionDispatcher.dispatchAction(
                    AppActions.AddAccountPopUpAction.InitPopUp
                )
            })
        },
        bodyModifier = Modifier.padding(bottom = LittleMarge)
    )
}

@Composable
fun MyAccountDetailBody(
    actionDispatcher: AppActionDispatcher
) {
    VerticalDispositionSheet(
        header = {
            AccountDetailTitle(
                onBackIconClick = {
                    actionDispatcher.dispatchAction(AppActions.MyAccountDetailScreenAction.CloseScreen)
                    actionDispatcher.dispatchAction(AppActions.MyAccountScreenAction.InitScreen)
                },
                accountName = myAccountDetailScreenUiState.accountName
            )
        },
        body = {
            AccountDetailSheetRecyclerView(
                allOperations = myAccountDetailScreenUiState.accountMonthlyOperations,
                onDeleteItemButtonCLick = {
                    actionDispatcher.dispatchAction(
                        AppActions.DeleteOperationPopUpAction.InitPopUp(it)
                    )
                },
                accountBalance = myAccountDetailScreenUiState.accountBalance.stringValue,
                accountRest = myAccountDetailScreenUiState.accountRest.stringValue,
            )

        },
        footer = {
            PAMAddButton(onAddButtonClicked = {
                actionDispatcher.dispatchAction(
                    AppActions.AddOperationPopUpAction.InitPopUp
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