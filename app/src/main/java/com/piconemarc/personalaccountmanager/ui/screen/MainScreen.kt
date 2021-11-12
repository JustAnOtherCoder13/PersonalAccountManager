package com.piconemarc.personalaccountmanager.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.MyAccountDetailViewModel
import com.piconemarc.viewmodel.viewModel.MyAccountViewModel
import com.piconemarc.viewmodel.viewModel.MyPaymentViewModel
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates

@Composable
fun PAMMainScreen(
    appViewModel: AppViewModel,
    appUiState: ViewModelInnerStates.BaseAppScreenVmState
) {
    BaseScreen(
        header = { MainScreenHeader() },
        body = {
            MainScreenBody(
                onInterLayerButtonClick = { selectedIconButton ->
                    appViewModel.dispatchAction(
                        AppActions.BaseAppScreenAction.SelectInterlayer(selectedIconButton)
                    )
                },
                body = {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "myAccount") {
                        composable("myAccount") {
                            val myAccountVM = hiltViewModel<MyAccountViewModel>()
                            MyAccountBody(
                                myAccountState = myAccountVM.myAccountState,
                                navController = navController,
                                onAddAccountButtonClick = {initAddAccountPopUp -> appViewModel.dispatchAction(initAddAccountPopUp)},
                                onDeleteAccountButtonClick = {initDeleteAccountPopUp -> appViewModel.dispatchAction(initDeleteAccountPopUp)}
                            )
                        }
                        composable("myAccountDetail/{selectedAccountId}") {
                            val myAccountDetailVM = hiltViewModel<MyAccountDetailViewModel>()
                            MyAccountDetailBody(
                                myAccountDetailState = myAccountDetailVM.myAccountDetailState,
                                navController = navController,
                                selectedAccountId = it.arguments?.getString("selectedAccountId"),
                                onMyAccountDetailEvent = { myAccountDetailAction -> myAccountDetailVM.dispatchAction(myAccountDetailAction) },
                                onAddOperationButtonClick = {initAddOperationPopUp-> appViewModel.dispatchAction(initAddOperationPopUp)},
                                onDeleteOperationButtonClick = {initDeleteOperationPopUp -> appViewModel.dispatchAction(initDeleteOperationPopUp)}
                                )
                        }
                        composable("myPayments") {
                            val myPaymentVM = hiltViewModel<MyPaymentViewModel>()
                            MyPaymentScreen(
                                paymentState = myPaymentVM.paymentState,
                                onAddPaymentButtonClick = {initAddPaymentPopUp -> appViewModel.dispatchAction(initAddPaymentPopUp)},
                                onDeletePaymentButtonClick = {initDeletePaymentPopUp -> appViewModel.dispatchAction(initDeletePaymentPopUp)}
                            )
                        }
                    }

                    when (appUiState.selectedInterlayerButton) {
                        is PAMIconButtons.Payment -> {
                            navController.navigate("myPayments")
                        }
                        is PAMIconButtons.Chart -> {
                        }
                        else -> {
                            if (appUiState.interLayerTitle != com.piconemarc.model.R.string.detail)
                                navController.navigate("myAccount")
                        }
                    }
                },
                selectedInterLayerButton = appUiState.selectedInterlayerButton,
                interlayerTitle = appUiState.interLayerTitle
            )
        },
        footer = {
            MainScreenFooter(
                footerAccountBalance = Pair(
                    appUiState.footerBalance.toStringWithTwoDec(),
                    getBlackOrNegativeColor(amount = appUiState.footerBalance)
                ),
                mainScreenFooterTitle = stringResource(R.string.mainScreenFooterTitle),
                footerAccountRest = Pair(
                    appUiState.footerRest.toStringWithTwoDec(),
                    getBlackOrNegativeColor(amount = appUiState.footerRest)
                )
            )
        }
    )
    AddOperationPopUp(
        onAddOperationPopUpEvent = {addOperationPopUpAction -> appViewModel.dispatchAction(addOperationPopUpAction) },
        addOperationPopUpState = appViewModel.addOperationPopUpState
    )
    DeleteOperationPopUp(
        deleteOperationPopUpState = appViewModel.deleteOperationPopUpState,
        onDeleteOperationPopUpEvent = {deleteOperationPoUpAction-> appViewModel.dispatchAction(deleteOperationPoUpAction)}
    )
    DeleteAccountPopUp(
        onDeleteAccountPopUpEvent = {deleteAccountPopUpAction -> appViewModel.dispatchAction(deleteAccountPopUpAction) },
        deleteAccountPopUpState = appViewModel.deleteAccountPopUpState
    )
    AddAccountPopUp(
        addAccountPopUpState = appViewModel.addAccountPopUpState,
        onAddAccountPopUpEvent = {addAccountPopUpAction -> appViewModel.dispatchAction(addAccountPopUpAction)}
    )
}