package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.runtime.Composable
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.BaseScreen
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMAppBody
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMAppFooter
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMAppHeader
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMAddAccountPopUp
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMAddOperationPopUp
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMDeleteAccountPopUp
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMDeleteOperationPopUp
import com.piconemarc.viewmodel.viewModel.AppActionDispatcher
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.baseAppScreenUiState

@Composable
fun PAMMainScreen(
    actionDispatcher: AppActionDispatcher,
) {
    actionDispatcher.dispatchAction(AppActions.BaseAppScreenAction.InitScreen)
    actionDispatcher.dispatchAction(AppActions.MyAccountScreenAction.InitScreen)

    BaseScreen(
        header = { PAMAppHeader() },
        body = {
            PAMAppBody(
                actionDispatcher = actionDispatcher,
                body = {
                    when (baseAppScreenUiState.selectedInterlayerButton) {
                        is PAMIconButtons.Payment -> {
                        }
                        is PAMIconButtons.Chart -> {
                        }
                        else -> { MyAccountsSheet(actionDispatcher = actionDispatcher) }
                    }
                }
            )
        },
        footer = {
            PAMAppFooter(
                footerAccountBalance = baseAppScreenUiState.footerBalance,
                footerAccountName = baseAppScreenUiState.footerTitle,
                footerAccountRest = baseAppScreenUiState.footerRest
            )
        }
    )
    PAMAddOperationPopUp(actionDispatcher = actionDispatcher)
    PAMDeleteAccountPopUp(actionDispatcher = actionDispatcher)
    PAMAddAccountPopUp(actionDispatcher = actionDispatcher)
    PAMDeleteOperationPopUp(actionDispatcher = actionDispatcher)
}