package com.piconemarc.personalaccountmanager.ui.component.popUp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.pAMAddOperationPopUpBackgroundColor
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.*
import com.piconemarc.viewmodel.PAMIconButtons
import com.piconemarc.viewmodel.viewModel.AppActionDispatcher
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.addOperationPopUpUiState

@Composable
fun PAMAddOperationPopUp(
    accountDetailViewModel: AppActionDispatcher
) {
    //Pop up Body --------------------------------------------
    PAMBasePopUp(
        title = addOperationPopUpUiState.addPopUpTitle,
        onAcceptButtonClicked = {
            //todo check if filled before add here

        },//todo add operation here
        onDismiss = { accountDetailViewModel.dispatchAction(AppActions.AddOperationPopUpAction.ClosePopUp) },
        isExpanded = addOperationPopUpUiState.isPopUpExpanded,
        menuIconPanel = {
            PAMAddOperationPopUpLeftSideMenuIconPanel(
                onIconButtonClicked = {
                    accountDetailViewModel.dispatchAction(AppActions.AddOperationPopUpAction.SelectOptionIcon(it))
                    when (it) {
                        is PAMIconButtons.Payment -> accountDetailViewModel.dispatchAction(
                            AppActions.AddOperationPopUpAction.ExpandPaymentOption
                        )
                        is PAMIconButtons.Transfer -> accountDetailViewModel.dispatchAction(
                            AppActions.AddOperationPopUpAction.ExpandTransferOption
                        )
                        else -> accountDetailViewModel.dispatchAction(
                            AppActions.AddOperationPopUpAction.CollapseOptions
                        )
                    }
                },
                selectedIcon = addOperationPopUpUiState.addPopUpOptionSelectedIcon
            )
        },
        popUpBackgroundColor = pAMAddOperationPopUpBackgroundColor(isAddOperation = addOperationPopUpUiState.isAddOperation).value
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //add or minus switch
            AddOrMinusSwitchButton(
                onAddOrMinusClicked = { isAddClicked ->
                    accountDetailViewModel.dispatchAction(AppActions.AddOperationPopUpAction.SelectAddOrMinus(
                    isAddClicked
                )) },
                isAddOperation = addOperationPopUpUiState.isAddOperation
            )
            // operation name--------------------------
            PAMBlackBackgroundTextFieldItem(
                title = PresentationDataModel(stringValue = stringResource(R.string.operationName)),
                onTextChange = { operationName ->
                    accountDetailViewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.FillOperationName(
                            operationName
                        )
                    )
                },
                textValue = addOperationPopUpUiState.operationName,
                isAddOperationPopUpExpanded = addOperationPopUpUiState.isPopUpExpanded
            )
            // operation Amount--------------------------
            PAMAmountTextFieldItem(
                title = PresentationDataModel(stringValue = stringResource(R.string.operationAmount)),
                onTextChange = { operationAmount ->
                    accountDetailViewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.FillOperationAmount(
                            operationAmount
                        )
                    )
                },
                amountValue = addOperationPopUpUiState.operationAmount,
                isAddOperationPopUpExpanded = addOperationPopUpUiState.isPopUpExpanded
            )
            //category drop down -----------------------------------
            PAMBaseDropDownMenuWithBackground(
                selectedItem = addOperationPopUpUiState.selectedCategory,
                itemList = addOperationPopUpUiState.allCategories,
                onItemSelected = { category ->
                    accountDetailViewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectCategory(
                            category
                        )
                    )
                }
            )

            //Payment Operation option--------------------------
            PAMPunctualOrRecurrentSwitchButton(
                isRecurrentOptionExpanded = addOperationPopUpUiState.isRecurrentOptionExpanded,
                isPaymentOptionExpanded = addOperationPopUpUiState.isPaymentExpanded,
                onPunctualButtonSelected = {
                    accountDetailViewModel
                        .dispatchAction(AppActions.AddOperationPopUpAction.CloseRecurrentOption)
                },
                onRecurrentButtonSelected = {
                    accountDetailViewModel
                        .dispatchAction(AppActions.AddOperationPopUpAction.ExpandRecurrentOption)
                },
                onMonthSelected = { endDateMonth ->
                    accountDetailViewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectEndDateMonth(
                            endDateMonth
                        )
                    )
                },
                onYearSelected = { endDateYear ->
                    accountDetailViewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectEndDateYear(
                            endDateYear
                        )
                    )
                },
                endDateSelectedMonth = addOperationPopUpUiState.enDateSelectedMonth,
                endDateSelectedYear = addOperationPopUpUiState.endDateSelectedYear,
                selectableMonthList = addOperationPopUpUiState.selectableEndDateMonths,
                selectableYearList = addOperationPopUpUiState.selectableEndDateYears
            )
            //Transfer Operation option---------------------------
            PAMTransferOptionPanel(
                isTransferOptionExpanded = addOperationPopUpUiState.isTransferExpanded,
                senderAccountSelectedItem = addOperationPopUpUiState.senderAccount,
                allAccountsList = addOperationPopUpUiState.allAccounts,
                beneficiaryAccountSelectedItem = addOperationPopUpUiState.beneficiaryAccount,
                onSenderAccountSelected = { senderAccount ->
                    accountDetailViewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectSenderAccount(
                            senderAccount
                        )
                    )
                },
                onBeneficiaryAccountSelected = { beneficiaryAccount ->
                    accountDetailViewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectBeneficiaryAccount(
                            beneficiaryAccount
                        )
                    )
                }
            )
        }
    }
}

