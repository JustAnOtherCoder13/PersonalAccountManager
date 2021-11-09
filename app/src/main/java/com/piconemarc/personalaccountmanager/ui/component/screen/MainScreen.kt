package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.piconemarc.viewmodel.viewModel.*
import com.piconemarc.viewmodel.viewModel.utils.AppActions

@Composable
fun PAMMainScreen(appViewModel: AppViewModel) {

    val appState by appViewModel.uiState

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
                                myAccountVM,
                                appViewModel,
                                myAccountUiStateValue = myAccountVM.uiState.value,
                                navController = navController
                            )
                        }
                        composable("myAccountDetail/{selectedAccountId}") {
                            val myAccountDetailVM = hiltViewModel<MyAccountDetailViewModel>()
                            MyAccountDetailBody(
                                myAccountDetailVM,
                                appViewModel,
                                navController,
                                it.arguments?.getString("selectedAccountId")
                            )
                        }
                        composable("myPayments") {
                            val myPaymentVM = hiltViewModel<MyPaymentViewModel>()
                            MyPaymentScreen(
                                myPaymentViewModel = myPaymentVM,
                                appViewModel = appViewModel
                            )
                        }
                    }


                    when (appState.selectedInterlayerButton) {
                        is PAMIconButtons.Payment -> {
                            navController.navigate("myPayments")
                        }
                        is PAMIconButtons.Chart -> {
                        }
                        else -> {
                            if (appState.interLayerTitle != com.piconemarc.model.R.string.detail)
                                navController.navigate("myAccount")
                        }
                    }
                },
                selectedInterLayerButton = appState.selectedInterlayerButton,
                interlayerTitle = appState.interLayerTitle
            )
        },
        footer = {
            MainScreenFooter(
                footerAccountBalance = Pair(
                    appState.footerBalance.toStringWithTwoDec(),
                    getBlackOrNegativeColor(amount = appState.footerBalance)
                ),
                mainScreenFooterTitle = stringResource(R.string.mainScreenFooterTitle),
                footerAccountRest = Pair(
                    appState.footerRest.toStringWithTwoDec(),
                    getBlackOrNegativeColor(amount = appState.footerRest)
                )
            )
        }
    )
    AddOperationPopUp(viewModel = appViewModel)
    DeleteAccountPopUp(viewModel = appViewModel)
    AddAccountPopUp(viewModel = appViewModel)
    DeleteOperationPopUp(viewModel = appViewModel)
}