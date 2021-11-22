package com.piconemarc.personalaccountmanager.ui.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.personalaccountmanager.NavDestinations
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.MainScreenBody
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.MainScreenFooter
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.MainScreenHeader
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BaseScreen
import com.piconemarc.personalaccountmanager.ui.popUp.*
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
    val navController = rememberNavController()
    BaseScreen(
        header = { MainScreenHeader() },
        body = {
            MainScreenBody(
                onInterLayerButtonClick = { selectedIconButton ->
                    when (selectedIconButton) {
                        is PAMIconButtons.Payment -> { NavDestinations.myPayment.doNavigation(navController = navController) }
                        is PAMIconButtons.Chart -> { NavDestinations.chart.doNavigation(navController = navController) }
                        is PAMIconButtons.Home -> { NavDestinations.Home.doNavigation(navController = navController) }
                        else -> { }
                    }
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
                },
                selectedInterLayerButton = appUiState.selectedInterlayerButton,
                interlayerTitle = appUiState.interLayerTitle
            )
        },
        footer = {
            MainScreenFooter(
                mainScreenFooterTitle = stringResource(R.string.mainScreenFooterTitle),
                footerAccountRest = appUiState.footerRest
            )
        }
    )

    AddOperationPopUp(
        addOperationPopUpState = appViewModel.addOperationPopUpState,
        onAddOperationPopUpEvent = { addOperationPopUpAction ->
            appViewModel.dispatchAction(
                addOperationPopUpAction
            )
        }
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
        deleteAccountPopUpState = appViewModel.deleteAccountPopUpState,
        onDeleteAccountPopUpEvent = { deleteAccountPopUpAction ->
            appViewModel.dispatchAction(
                deleteAccountPopUpAction
            )
        }
    )

    AddAccountPopUp(
        addAccountPopUpState = appViewModel.addAccountPopUpState,
        onAddAccountPopUpEvent = { addAccountPopUpAction ->
            appViewModel.dispatchAction(
                addAccountPopUpAction
            )
        }
    )

    DeleteObsoletePaymentPopUp(
        deleteObsoletePaymentPopUpState = appViewModel.deleteObsoletePaymentPopUpState,
        onDeleteObsoletePaymentEvent = { deleteObsoletePaymentAction ->
            appViewModel.dispatchAction(
                deleteObsoletePaymentAction
            )
        }
    )
}