package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.toStringWithTwoDec
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.MainScreenBody
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.MainScreenFooter
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.MainScreenHeader
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BaseScreen
import com.piconemarc.personalaccountmanager.ui.component.popUp.AddAccountPopUp
import com.piconemarc.personalaccountmanager.ui.component.popUp.AddOperationPopUp
import com.piconemarc.personalaccountmanager.ui.component.popUp.DeleteAccountPopUp
import com.piconemarc.personalaccountmanager.ui.component.popUp.DeleteOperationPopUp
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.baseAppScreenUiState

@Composable
fun PAMMainScreen(
    viewModel: AppViewModel,
) {
    //todo pass with navigator
    viewModel.dispatchAction(AppActions.BaseAppScreenAction.InitScreen)
    viewModel.dispatchAction(AppActions.MyAccountScreenAction.InitScreen)

    BaseScreen(
        header = { MainScreenHeader() },
        body = {
            MainScreenBody(
                onInterLayerButtonClick = {selectedIconButton->
                    viewModel.dispatchAction(
                        AppActions.BaseAppScreenAction.SelectInterlayer(selectedIconButton)
                    )
                },
                body = {
                    //todo pass with navigator
                    when (baseAppScreenUiState.selectedInterlayerButton) {
                        is PAMIconButtons.Payment -> {
                            //viewModel.dispatchAction(AppActions.MyAccountDetailScreenAction.CloseScreen)
                            //viewModel.dispatchAction(AppActions.MyAccountScreenAction.CloseScreen)
                            viewModel.dispatchAction(AppActions.PaymentScreenAction.InitScreen)
                            PaymentScreen(viewModel = viewModel)
                        }
                        is PAMIconButtons.Chart -> {
                        }
                        else -> {
                            MyAccountsScreen(viewModel = viewModel)
                            viewModel.dispatchAction(AppActions.PaymentScreenAction.CloseScreen)
                            viewModel.dispatchAction(AppActions.MyAccountScreenAction.InitScreen)
                        }
                    }
                }
            )
        },
        footer = {
            MainScreenFooter(
                footerAccountBalance = baseAppScreenUiState.footerBalance.toStringWithTwoDec(),
                mainScreenFooterTitle = stringResource(R.string.mainScreenFooterTitle),
                footerAccountRest = baseAppScreenUiState.footerRest.toStringWithTwoDec()
            )
        }
    )
    AddOperationPopUp(viewModel = viewModel)
    DeleteAccountPopUp(viewModel = viewModel)
    AddAccountPopUp(viewModel = viewModel)
    DeleteOperationPopUp(viewModel = viewModel)
}