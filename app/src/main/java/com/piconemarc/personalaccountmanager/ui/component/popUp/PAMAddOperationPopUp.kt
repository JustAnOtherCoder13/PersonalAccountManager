package com.piconemarc.personalaccountmanager.ui.component.popUp

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.*
import com.piconemarc.personalaccountmanager.ui.theme.BigMarge
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.viewmodel.viewModel.AccountDetailViewModel
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpUtilsProvider

@Composable
fun PAMAddOperationPopUp(
    accountDetailViewModel: AccountDetailViewModel
) {
    //Pop up Body --------------------------------------------
    PAMBasePopUp(
        title = AddOperationPopUpUtilsProvider().providedUiState.addPopUpTitle,
        onAcceptButtonClicked = { },//todo add operation here
        onDismiss = { accountDetailViewModel.dispatchAction(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ClosePopUp) },
        isExpanded = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.isPopUpExpanded,
        menuIconPanel = {
            PAMAddOperationPopUpLeftSideMenuIconPanel(
                isTransferOptionExpanded = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.isTransferExpanded,
                isPaymentOptionExpanded = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.isPaymentExpanded,
                onOperationButtonClicked = {
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.CollapseOptions
                    )
                },
                onPaymentButtonClicked = {
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ExpandPaymentOption
                    )
                },
                onTransferButtonClicked = {
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ExpandTransferOption
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //add or minus switch
            AddOrMinusSwitchButton()
            // operation name--------------------------
            PAMBlackBackgroundTextFieldItem(
                title = PresentationDataModel(stringValue = stringResource(R.string.operationName)),
                onTextChange = { operationName ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.FillOperationName(operationName)
                    )
                },
                textValue = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.operationName,
                isAddOperationPopUpExpanded = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.isPopUpExpanded
            )
            // operation Amount--------------------------
            PAMAmountTextFieldItem(
                title = PresentationDataModel(stringValue = stringResource(R.string.operationAmount)),
                onTextChange = { operationAmount ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.FillOperationAmount(operationAmount)
                    )
                },
                amountValue = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.operationAmount,
                isAddOperationPopUpExpanded = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.isPopUpExpanded
            )
            //category drop down -----------------------------------
            PAMBaseDropDownMenuWithBackground(
                selectedItem = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.selectedCategoryName,
                itemList = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.operationCategories,
                onItemSelected = { category ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectCategory(category)
                    )
                }
            )

            //Payment Operation option--------------------------
            PAMPunctualOrRecurrentSwitchButton(
                isRecurrentOptionExpanded = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.isRecurrentOptionExpanded,
                isPaymentOptionExpanded = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.isPaymentExpanded,
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
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectEndDateMonth(endDateMonth)
                    )
                },
                onYearSelected = { endDateYear ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectEndDateYear(endDateYear)
                    )
                },
                endDateSelectedMonth = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.enDateSelectedMonth,
                endDateSelectedYear = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.endDateSelectedYear,
                selectableMonthList = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.selectableMonthsList,
                selectableYearList = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.selectableYearsList
            )
            //Transfer Operation option---------------------------
            PAMTransferOptionPanel(
                isTransferOptionExpanded = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.isTransferExpanded,
                senderAccountSelectedItem = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.senderAccount,
                allAccountsList = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.accountList,
                beneficiaryAccountSelectedItem = AddOperationPopUpUtilsProvider.AddOperationPopUpUiState.beneficiaryAccount,
                onSenderAccountSelected = { senderAccount ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectSenderAccount(senderAccount)
                    )
                },
                onBeneficiaryAccountSelected = { beneficiaryAccount ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectBeneficiaryAccount(beneficiaryAccount)
                    )
                }
            )
        }
    }
}

@Composable
private fun AddOrMinusSwitchButton() {
    var isAddOperation by remember {
        mutableStateOf(true)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = BigMarge, end = BigMarge, top = LittleMarge),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PAMIconButton(
            onIconButtonClicked = { isAddOperation = true },
            iconButton = PAMIconButtons.Add,
            iconColor = if (isAddOperation) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary,
            backgroundColor = if (isAddOperation) MaterialTheme.colors.primary else MaterialTheme.colors.secondaryVariant
        )
        PAMIconButton(
            onIconButtonClicked = { isAddOperation = false },
            iconButton = PAMIconButtons.Minus,
            iconColor = if (!isAddOperation) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary,
            backgroundColor = if (!isAddOperation) MaterialTheme.colors.primary else MaterialTheme.colors.secondaryVariant
        )
    }
}