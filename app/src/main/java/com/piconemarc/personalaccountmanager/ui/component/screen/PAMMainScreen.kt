package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.runtime.*
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.BaseScreen
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMAppBody
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMAppFooter
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMAppHeader
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMAddAccountPopUp
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMAddOperationPopUp
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMDeleteAccountPopUp
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMDeleteOperationPopUp
import com.piconemarc.viewmodel.PAMIconButtons
import com.piconemarc.viewmodel.viewModel.AppActionDispatcher
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.baseAppScreenUiState

@Composable
fun PAMMainScreen(
    actionDispatcher: AppActionDispatcher,
) {
    actionDispatcher.dispatchAction(AppActions.BaseAppScreenAction.InitScreen)
    actionDispatcher.dispatchAction(AppActions.MyAccountScreenAction.InitScreen)
    var isPop by remember {
        mutableStateOf(false)
    }
    var isAccountClicked by remember {
        mutableStateOf(false)
    }
    var selectedAccountName by remember {
        mutableStateOf(PresentationDataModel())
    }
    var isDeleteOperationPopUpExpanded by remember {
        mutableStateOf(false)
    }
    var operationName by remember {
        mutableStateOf("")
    }
    var operationAmount by remember {
        mutableStateOf("")
    }
    BaseScreen(
        header = { PAMAppHeader() },
        body = {
            PAMAppBody(
                onInterlayerIconClicked = {
                    actionDispatcher.dispatchAction(
                        AppActions.BaseAppScreenAction.SelectInterlayer(it)
                    )
                },
                selectedInterlayerIconButton = baseAppScreenUiState.selectedInterlayerButton,
                body = {
                    when (baseAppScreenUiState.selectedInterlayerButton) {
                        is PAMIconButtons.Payment -> {
                        }
                        is PAMIconButtons.Chart -> {
                        }
                        else -> { MyAccountsBody(
                            actionDispatcher = actionDispatcher,
                            onDeleteOperationButtonClick = {
                                isDeleteOperationPopUpExpanded = true
                                operationName = it.name
                                operationAmount = it.amount.toString()
                            }
                        ) }
                    }
                },
                interLayerTitle = ""
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
    PAMDeleteOperationPopUp(
        isDeleteOperationExpanded = isDeleteOperationPopUpExpanded,
        operationName = PresentationDataModel(operationName),
        operationAmount = PresentationDataModel(operationAmount),
        onAcceptButtonClick ={

        },
        onDismiss ={
            isDeleteOperationPopUpExpanded = false
        },
        actionDispatcher = actionDispatcher
    )
}