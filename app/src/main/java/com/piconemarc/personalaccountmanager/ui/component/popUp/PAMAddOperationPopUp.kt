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
import com.piconemarc.viewmodel.viewModel.AccountDetailViewModel
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpUtilsProvider
import com.piconemarc.viewmodel.viewModel.globalState.GlobalUtilProvider.GlobalUiState.addOperationPopUpUiState

@Composable
fun PAMAddOperationPopUp(
    accountDetailViewModel: AccountDetailViewModel
) {
    //todo pass with reducer and state
    var isAddOperation by remember {
        mutableStateOf(true)
    }
    var selectedIcon :PAMIconButtons by remember {
        mutableStateOf(PAMIconButtons.Operation)
    }
    //Pop up Body --------------------------------------------
    PAMBasePopUp(
        title = addOperationPopUpUiState.addPopUpTitle,
        onAcceptButtonClicked = {
            //todo check if filled before add here

        },//todo add operation here
        onDismiss = { accountDetailViewModel.dispatchAction(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ClosePopUp) },
        isExpanded = addOperationPopUpUiState.isPopUpExpanded,
        menuIconPanel = {
            PAMAddOperationPopUpLeftSideMenuIconPanel(
                onIconButtonClicked = {
                    selectedIcon = it
                    when (it) {
                        is PAMIconButtons.Payment -> accountDetailViewModel.dispatchAction(
                            AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ExpandPaymentOption
                        )
                        is PAMIconButtons.Transfer -> accountDetailViewModel.dispatchAction(
                            AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ExpandTransferOption
                        )
                        else -> accountDetailViewModel.dispatchAction(
                            AddOperationPopUpUtilsProvider.AddOperationPopUpAction.CollapseOptions
                        )
                    }
                },
                selectedIcon = selectedIcon
            )
        },
        popUpBackgroundColor = pAMAddOperationPopUpBackgroundColor(isAddOperation = isAddOperation).value
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //add or minus switch
            AddOrMinusSwitchButton(
                onAddOrMinusClicked = { isAddClicked -> isAddOperation = isAddClicked },
                isAddOperation = isAddOperation
            )
            // operation name--------------------------
            PAMBlackBackgroundTextFieldItem(
                title = PresentationDataModel(stringValue = stringResource(R.string.operationName)),
                onTextChange = { operationName ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.FillOperationName(
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
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.FillOperationAmount(
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
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectCategory(
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
                        .dispatchAction(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.CloseRecurrentOption)
                },
                onRecurrentButtonSelected = {
                    accountDetailViewModel
                        .dispatchAction(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ExpandRecurrentOption)
                },
                onMonthSelected = { endDateMonth ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectEndDateMonth(
                            endDateMonth
                        )
                    )
                },
                onYearSelected = { endDateYear ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectEndDateYear(
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
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectSenderAccount(
                            senderAccount
                        )
                    )
                },
                onBeneficiaryAccountSelected = { beneficiaryAccount ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectBeneficiaryAccount(
                            beneficiaryAccount
                        )
                    )
                }
            )
        }
    }
}

