package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.getBlackOrNegativeColor
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
import com.piconemarc.viewmodel.viewModel.MyAccountDetailViewModel
import com.piconemarc.viewmodel.viewModel.MyAccountViewModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.baseAppScreenUiState

@Composable
fun PAMMainScreen(
    viewModel: AppViewModel,
    myAccountViewModel: MyAccountViewModel,
    myAccountDetailViewModel: MyAccountDetailViewModel
) {
    //todo pass with navigator
    viewModel.dispatchAction(AppActions.BaseAppScreenAction.InitScreen)
    myAccountViewModel.dispatchAction(AppActions.MyAccountScreenAction.InitScreen)

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
                            viewModel.dispatchAction(AppActions.PaymentScreenAction.InitScreen)
                            PaymentScreen(viewModel = viewModel)
                        }
                        is PAMIconButtons.Chart -> {
                        }
                        else -> {
                            MyAccountsScreen( myAccountViewModel,myAccountDetailViewModel)
                            viewModel.dispatchAction(AppActions.PaymentScreenAction.CloseScreen)
                        }
                    }
                }
            )
        },
        footer = {
            MainScreenFooter(
                footerAccountBalance = Pair(baseAppScreenUiState.footerBalance.toStringWithTwoDec(),
                    getBlackOrNegativeColor(amount = baseAppScreenUiState.footerBalance)),
                mainScreenFooterTitle = stringResource(R.string.mainScreenFooterTitle),
                footerAccountRest = Pair(baseAppScreenUiState.footerRest.toStringWithTwoDec(),
                    getBlackOrNegativeColor(amount = baseAppScreenUiState.footerRest))
            )
        }
    )
    AddOperationPopUp(viewModel = viewModel)
    DeleteAccountPopUp(viewModel = viewModel)
    AddAccountPopUp(viewModel = viewModel)
    DeleteOperationPopUp(viewModel = viewModel)
}