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
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.myAccountDetailScreenUiState
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.myAccountScreenUiState

@Composable
fun MyAccountsSheet(
    viewModel: AppViewModel,
) {
    if (myAccountScreenUiState.isVisible) MyAccountBody(viewModel)
    if (myAccountDetailScreenUiState.isVisible) MyAccountDetailBody(viewModel)
}



@Composable
private fun MyAccountBody(viewModel: AppViewModel) {
    VerticalDispositionSheet(
        body = { MyAccountBodyRecyclerView(viewModel) },
        footer = {
            PAMAddButton(onAddButtonClicked = {
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
    VerticalDispositionSheet(
        header = {
            AccountDetailTitle(
                onBackIconClick = {
                    viewModel.dispatchAction(AppActions.MyAccountDetailScreenAction.CloseScreen)
                    viewModel.dispatchAction(AppActions.MyAccountScreenAction.InitScreen)
                },
                accountName = myAccountDetailScreenUiState.selectedAccount.name
            )
        },
        body = {
            AccountDetailSheetRecyclerView(
                allOperations = myAccountDetailScreenUiState.accountMonthlyOperations,
                onDeleteItemButtonCLick = {
                    viewModel.dispatchAction(
                        AppActions.DeleteOperationPopUpAction.InitPopUp(it)
                    )
                },
                accountBalance = myAccountDetailScreenUiState.accountBalance,
                accountRest = myAccountDetailScreenUiState.accountRest,
            )

        },
        footer = {
            PAMAddButton(onAddButtonClicked = {
                viewModel.dispatchAction(
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