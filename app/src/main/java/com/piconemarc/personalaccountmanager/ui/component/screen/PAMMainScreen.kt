package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.runtime.Composable
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.personalaccountmanager.toStringWithTwoDec
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.BaseScreen
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMAppBody
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMAppFooter
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMAppHeader
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMAddAccountPopUp
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMAddOperationPopUp
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMDeleteAccountPopUp
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMDeleteOperationPopUp
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.baseAppScreenUiState

@Composable
fun PAMMainScreen(
    viewModel: AppViewModel,
) {
    viewModel.dispatchAction(AppActions.BaseAppScreenAction.InitScreen)
    viewModel.dispatchAction(AppActions.MyAccountScreenAction.InitScreen)

    BaseScreen(
        header = { PAMAppHeader() },
        body = {
            PAMAppBody(
                viewModel = viewModel,
                body = {
                    when (baseAppScreenUiState.selectedInterlayerButton) {
                        is PAMIconButtons.Payment -> {
                            PaymentScreen()
                        }
                        is PAMIconButtons.Chart -> {
                        }
                        else -> { MyAccountsScreen(viewModel = viewModel) }
                    }
                }
            )
        },
        footer = {
            PAMAppFooter(
                footerAccountBalance = baseAppScreenUiState.footerBalance.toStringWithTwoDec(),
                footerAccountName = baseAppScreenUiState.footerTitle,
                footerAccountRest = baseAppScreenUiState.footerRest.toStringWithTwoDec()
            )
        }
    )
    PAMAddOperationPopUp(viewModel = viewModel)
    PAMDeleteAccountPopUp(viewModel = viewModel)
    PAMAddAccountPopUp(viewModel = viewModel)
    PAMDeleteOperationPopUp(viewModel = viewModel)
}