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
import com.piconemarc.viewmodel.viewModel.*
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.baseAppScreenUiState

@Composable
fun PAMMainScreen(
    appViewModel: AppViewModel,
    myAccountViewModel: MyAccountViewModel,
    myAccountDetailViewModel: MyAccountDetailViewModel,
    myPaymentViewModel: MyPaymentViewModel,
) {
    //todo pass with navigator
    appViewModel.dispatchAction(AppActions.BaseAppScreenAction.InitScreen)
    myAccountViewModel.dispatchAction(AppActions.MyAccountScreenAction.InitScreen)

    BaseScreen(
        header = { MainScreenHeader() },
        body = {
            MainScreenBody(
                onInterLayerButtonClick = {selectedIconButton->
                    appViewModel.dispatchAction(
                        AppActions.BaseAppScreenAction.SelectInterlayer(selectedIconButton)
                    )
                },
                body = {
                    //todo pass with navigator
                    when (baseAppScreenUiState.value.selectedInterlayerButton) {
                        is PAMIconButtons.Payment -> {
                            myPaymentViewModel.dispatchAction(AppActions.PaymentScreenAction.InitScreen)
                            PaymentScreen(myPaymentViewModel, appViewModel)
                        }
                        is PAMIconButtons.Chart -> {
                        }
                        else -> {
                            MyAccountsScreen( myAccountViewModel,myAccountDetailViewModel, appViewModel)
                            appViewModel.dispatchAction(AppActions.PaymentScreenAction.CloseScreen)
                        }
                    }
                }
            )
        },
        footer = {
            MainScreenFooter(
                footerAccountBalance = Pair(baseAppScreenUiState.value.footerBalance.toStringWithTwoDec(),
                    getBlackOrNegativeColor(amount = baseAppScreenUiState.value.footerBalance)),
                mainScreenFooterTitle = stringResource(R.string.mainScreenFooterTitle),
                footerAccountRest = Pair(baseAppScreenUiState.value.footerRest.toStringWithTwoDec(),
                    getBlackOrNegativeColor(amount = baseAppScreenUiState.value.footerRest))
            )
        }
    )
    AddOperationPopUp(viewModel = appViewModel)
    DeleteAccountPopUp(viewModel = appViewModel)
    AddAccountPopUp(viewModel = appViewModel)
    DeleteOperationPopUp(viewModel = appViewModel)
}