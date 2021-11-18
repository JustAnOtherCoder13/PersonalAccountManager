package com.piconemarc.personalaccountmanager.ui.screen

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.personalaccountmanager.*
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.MainScreenBody
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.MainScreenFooter
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.MainScreenHeader
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BaseDeletePopUp
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BaseScreen
import com.piconemarc.personalaccountmanager.ui.popUp.AddAccountPopUp
import com.piconemarc.personalaccountmanager.ui.popUp.AddOperationPopUp
import com.piconemarc.personalaccountmanager.ui.popUp.DeleteAccountPopUp
import com.piconemarc.personalaccountmanager.ui.popUp.DeleteOperationPopUp
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.personalaccountmanager.ui.theme.ThinBorder
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.MyAccountDetailViewModel
import com.piconemarc.viewmodel.viewModel.MyAccountViewModel
import com.piconemarc.viewmodel.viewModel.MyPaymentViewModel
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates

//todo when open app check for payment arrive to end date and if exist pop up to delete
//todo after check for payment to pass in operation and show toast "don't forget to pass your payment for this month
@Composable
fun PAMMainScreen(
    appViewModel: AppViewModel,
    appUiState: ViewModelInnerStates.BaseAppScreenVmState
) {
    val navController = rememberNavController()
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
                    NavHost(
                        navController = navController,
                        startDestination = NavDestinations.Home.destination
                    ) {


                        composable(route = NavDestinations.Home.getRoute()) {
                            val myAccountVM = hiltViewModel<MyAccountViewModel>()

                            LaunchedEffect(key1 = myAccountVM){
                                myAccountVM.onStart()
                            }

                            MyAccountBody(
                                myAccountState = myAccountVM.myAccountState,
                                navController = navController,
                                onAddAccountButtonClick = { initAddAccountPopUp ->
                                    appViewModel.dispatchAction(
                                        initAddAccountPopUp
                                    )
                                },
                                onDeleteAccountButtonClick = { initDeleteAccountPopUp ->
                                    appViewModel.dispatchAction(
                                        initDeleteAccountPopUp
                                    )
                                }
                            )
                        }
                        composable(
                            route = NavDestinations.myAccountDetail.getRoute(),
                        ) {
                            val myAccountDetailVM = hiltViewModel<MyAccountDetailViewModel>()

                            LaunchedEffect(key1 = myAccountDetailVM){
                                myAccountDetailVM.onStart(it.arguments?.getString(NavDestinations.myAccountDetail.key)?:"")
                            }

                            MyAccountDetailBody(
                                myAccountDetailState = myAccountDetailVM.myAccountDetailState,
                                navController = navController,
                                onMyAccountDetailEvent = { myAccountDetailAction ->
                                    myAccountDetailVM.dispatchAction(
                                        myAccountDetailAction
                                    )
                                },
                                onAddOperationButtonClick = { initAddOperationPopUp ->
                                    appViewModel.dispatchAction(
                                        initAddOperationPopUp
                                    )
                                },
                                onDeleteOperationButtonClick = { initDeleteOperationPopUp ->
                                    appViewModel.dispatchAction(
                                        initDeleteOperationPopUp
                                    )
                                }
                            )
                        }
                        composable(NavDestinations.myPayment.getRoute()) {
                            //todo don't find why but when open add or delete popup on this screen only, it restart the composable lifecycle
                            val myPaymentVM = hiltViewModel<MyPaymentViewModel>()

                            LaunchedEffect(key1 = myPaymentVM){
                                myPaymentVM.onStart()
                            }

                            MyPaymentScreen(
                                paymentState = myPaymentVM.paymentState,
                                onAddPaymentButtonClick = { initAddPaymentPopUp ->
                                    appViewModel.dispatchAction(
                                        initAddPaymentPopUp
                                    )
                                },
                                onDeletePaymentButtonClick = { initDeletePaymentPopUp ->
                                    appViewModel.dispatchAction(
                                        initDeletePaymentPopUp
                                    )
                                },
                                onPaymentEvent = { paymentAction ->
                                    myPaymentVM.dispatchAction(paymentAction)
                                }
                            )
                        }

                        composable(NavDestinations.chart.getRoute()){
                            Text(text = "on chart")
                        }
                    }

                    when (appUiState.selectedInterlayerButton) {
                        is PAMIconButtons.Payment -> {
                            NavDestinations.myPayment.doNavigation(navController = navController)
                        }
                        is PAMIconButtons.Chart -> {
                            NavDestinations.chart.doNavigation(navController = navController)
                        }
                        is PAMIconButtons.Home -> {
                            if (appUiState.interLayerTitle != (com.piconemarc.model.R.string.detail)
                                && appUiState.interLayerTitle != (com.piconemarc.model.R.string.myAccountsInterLayerTitle)
                            ) {
                                NavDestinations.Home.doNavigation(navController = navController)
                            }
                        }
                        else -> {
                        }
                    }
                },
                selectedInterLayerButton = appUiState.selectedInterlayerButton,
                interlayerTitle = appUiState.interLayerTitle
            )
        },
        footer = {
            //todo footer better only show rest, global balance is useless, instead pass rest/month, rest/week, rest/day
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
        onAddOperationPopUpEvent = { addOperationPopUpAction ->
            appViewModel.dispatchAction(
                addOperationPopUpAction
            )
        },
        addOperationPopUpState = appViewModel.addOperationPopUpState
    )
    DeleteOperationPopUp(
        deleteOperationPopUpState = appViewModel.deleteOperationPopUpState,
        onDeleteOperationPopUpEvent = { deleteOperationPoUpAction ->
            appViewModel.dispatchAction(
                deleteOperationPoUpAction
            )
        }
    )
    DeleteAccountPopUp(
        onDeleteAccountPopUpEvent = { deleteAccountPopUpAction ->
            appViewModel.dispatchAction(
                deleteAccountPopUpAction
            )
        },
        deleteAccountPopUpState = appViewModel.deleteAccountPopUpState
    )
    AddAccountPopUp(
        addAccountPopUpState = appViewModel.addAccountPopUpState,
        onAddAccountPopUpEvent = { addAccountPopUpAction ->
            appViewModel.dispatchAction(
                addAccountPopUpAction
            )
        }
    )

    BaseDeletePopUp(
        deletePopUpTitle = "Delete obsolete payments",
        onAcceptButtonClicked = {
            appViewModel.dispatchAction(
                AppActions.DeleteObsoletePaymentPopUpAction.DeleteObsoletePayment(appUiState.obsoletePaymentToDelete)
            )
        },
        onDismiss = {
            appViewModel.dispatchAction(
                AppActions.DeleteObsoletePaymentPopUpAction.ClosePopUp
            )
        },
        isExpanded = appViewModel.deleteObsoletePaymentPopUpState.isVisible
    ) {

        Column(modifier = Modifier.padding(vertical = RegularMarge)) {
            Text(
                text = "Those Payments finished this month, would you like to delete them ?",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(RegularMarge),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(RegularMarge)
                    .border(width = ThinBorder, color = MaterialTheme.colors.onSecondary, shape = RoundedCornerShape(
                        RegularMarge))
            ) {
                appUiState.obsoletePaymentToDelete.forEach {
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = RegularMarge),
                        style = MaterialTheme.typography.body1
                    )
                }
            }

        }
    }
}