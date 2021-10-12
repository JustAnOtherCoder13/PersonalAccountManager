package com.piconemarc.personalaccountmanager.ui.component.popUp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.component.*
import com.piconemarc.viewmodel.viewModel.AccountDetailViewModel
import com.piconemarc.viewmodel.viewModel.AddOperationPopUpState
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpAction

@SuppressLint("ModifierParameter")
@Composable
fun PAMAddOperationPopUp(
    accountDetailViewModel: AccountDetailViewModel
) {
    //Pop up Body --------------------------------------------
    PAMBasePopUp(
        title = AddOperationPopUpState.addPopUpTitle,
        onAcceptButtonClicked = { },//todo add operation here
        onDismiss = { accountDetailViewModel.dispatchAction(AddOperationPopUpAction.ClosePopUp) },
        isExpanded = AddOperationPopUpState.isPopUpExpanded,
        menuIconPanel = {
            PAMAddOperationPopUpLeftSideMenuIconPanel(
                isTransferOptionExpanded = AddOperationPopUpState.isTransferExpanded,
                isPaymentOptionExpanded = AddOperationPopUpState.isPaymentExpanded,
                onOperationButtonClicked = {
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpAction.CollapseOptions
                    )
                },
                onPaymentButtonClicked = {
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpAction.ExpandPaymentOption
                    )
                },
                onTransferButtonClicked = {
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpAction.ExpandTransferOption
                    )
                }
            )
        }
    ) {
        //category drop down -----------------------------------
        PAMBaseDropDownMenuWithBackground(
            selectedItem = AddOperationPopUpState.selectedCategoryName,
            itemList = AddOperationPopUpState.operationCategories,
            onItemSelected = { category ->
                accountDetailViewModel.dispatchAction(
                    AddOperationPopUpAction.SelectCategory(category)
                )
            }
        )
        // operation and amount text field--------------------------
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PAMBlackBackgroundTextFieldItem(
                title = PresentationDataModel(stringValue = stringResource(R.string.operationName)),
                onTextChange = { operationName ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpAction.FillOperationName(operationName)
                    )
                },
                textValue = AddOperationPopUpState.operationName,
            )
            PAMAmountTextFieldItem(
                title = PresentationDataModel(stringValue = stringResource(R.string.operationAmount)),
                onTextChange = { operationAmount ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpAction.FillOperationAmount(operationAmount)
                    )
                },
                amountValue = AddOperationPopUpState.operationAmount,
            )
            //Payment Operation option--------------------------
            PAMPunctualOrRecurrentSwitchButton(
                isRecurrentOptionExpanded = AddOperationPopUpState.isRecurrentOptionExpanded,
                isPaymentOptionExpanded = AddOperationPopUpState.isPaymentExpanded,
                onPunctualButtonSelected = {
                    accountDetailViewModel
                        .dispatchAction(AddOperationPopUpAction.CloseRecurrentOption)
                },
                onRecurrentButtonSelected = {
                    accountDetailViewModel
                        .dispatchAction(AddOperationPopUpAction.ExpandRecurrentOption)
                },
                onMonthSelected = { endDateMonth ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpAction.SelectEndDateMonth(endDateMonth)
                    )
                },
                onYearSelected = { endDateYear ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpAction.SelectEndDateYear(endDateYear)
                    )
                },
                endDateSelectedMonth = AddOperationPopUpState.enDateSelectedMonth,
                endDateSelectedYear = AddOperationPopUpState.endDateSelectedYear,
                selectableMonthList = AddOperationPopUpState.selectableMonthsList,
                selectableYearList = AddOperationPopUpState.selectableYearsList
            )
            //Transfer Operation option---------------------------
            PAMTransferOptionPanel(
                isTransferOptionExpanded = AddOperationPopUpState.isTransferExpanded,
                senderAccountSelectedItem = AddOperationPopUpState.senderAccount,
                allAccountsList = AddOperationPopUpState.accountList,
                beneficiaryAccountSelectedItem = AddOperationPopUpState.beneficiaryAccount,
                onSenderAccountSelected = { senderAccount ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpAction.SelectSenderAccount(senderAccount)
                    )
                },
                onBeneficiaryAccountSelected = { beneficiaryAccount ->
                    accountDetailViewModel.dispatchAction(
                        AddOperationPopUpAction.SelectBeneficiaryAccount(beneficiaryAccount)
                    )
                }
            )
        }
    }
}