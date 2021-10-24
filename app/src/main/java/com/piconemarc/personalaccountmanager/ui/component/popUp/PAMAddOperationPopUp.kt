package com.piconemarc.personalaccountmanager.ui.component.popUp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.model.entity.EndDate
import com.piconemarc.model.entity.OperationModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.pAMAddOperationPopUpBackgroundColor
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.*
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.addOperationPopUpUiState
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.myAccountDetailScreenUiState

@Composable
fun PAMAddOperationPopUp(
    viewModel: AppViewModel
) {
    //Pop up Body --------------------------------------------
    PAMBasePopUp(
        title = addOperationPopUpUiState.addPopUpTitle,
        onAcceptButtonClicked = {
            viewModel.dispatchAction(
                AppActions.AddOperationPopUpAction.AddNewOperation(
                    OperationModel(
                        name = addOperationPopUpUiState.operationName,
                        amount = try {
                            addOperationPopUpUiState.operationAmount.toDouble()
                        } catch (e: NumberFormatException) {
                            0.0
                        },
                        accountId = myAccountDetailScreenUiState.selectedAccount.id,
                        categoryId = addOperationPopUpUiState.selectedCategory.id,
                        isRecurrent = addOperationPopUpUiState.isRecurrentOptionExpanded,
                        endDate = if (!addOperationPopUpUiState.isRecurrentEndDateError) EndDate(
                            month = addOperationPopUpUiState.enDateSelectedMonth,
                            year = addOperationPopUpUiState.endDateSelectedYear
                        ) else null,
                        senderAccountId = if(!addOperationPopUpUiState.isSenderAccountError)
                            addOperationPopUpUiState.senderAccount.id else null,
                        beneficiaryAccountId = if(!addOperationPopUpUiState.isBeneficiaryAccountError)
                            addOperationPopUpUiState.beneficiaryAccount.id else null

                    )
                )
            )

        },
        onDismiss = { viewModel.dispatchAction(AppActions.AddOperationPopUpAction.ClosePopUp) },
        isExpanded = addOperationPopUpUiState.isPopUpExpanded,
        menuIconPanel = {
            PAMAddOperationPopUpLeftSideMenuIconPanel(
                onIconButtonClicked = {
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectOptionIcon(it)
                    )
                },
                selectedIcon = addOperationPopUpUiState.addPopUpOptionSelectedIcon
            )
        },
        popUpBackgroundColor = pAMAddOperationPopUpBackgroundColor(
            isAddOperation = addOperationPopUpUiState.isAddOperation,
            isAddOrMinusEnable = addOperationPopUpUiState.isAddOrMinusEnable
        ).value
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //add or minus switch
            AddOrMinusSwitchButton(
                onAddOrMinusClicked = { isAddClicked ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectAddOrMinus(
                            isAddClicked
                        )
                    )
                },
                isAddOperation = addOperationPopUpUiState.isAddOperation,
                isEnable = addOperationPopUpUiState.isAddOrMinusEnable
            )
            // operation name--------------------------
            PAMBrownBackgroundTextFieldItem(
                title =  stringResource(R.string.operationName),
                onTextChange = { operationName ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.FillOperationName(
                            operationName
                        )
                    )
                },
                textValue = addOperationPopUpUiState.operationName,
                isPopUpExpanded = addOperationPopUpUiState.isPopUpExpanded,
                isError = addOperationPopUpUiState.isOperationNameError,
                errorMsg = "Please, fill operation name"
            )
            // operation Amount--------------------------
            PAMBrownBackgroundAmountTextFieldItem(
                title =  stringResource(R.string.operationAmount),
                onTextChange = { operationAmount ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.FillOperationAmount(
                            operationAmount
                        )
                    )
                },
                amountValue = addOperationPopUpUiState.operationAmount,
                isPopUpExpanded = addOperationPopUpUiState.isPopUpExpanded,
                isError = addOperationPopUpUiState.isOperationAmountError,
                errorMsg = "Please, fill an amount for this operation"
            )
            //category drop down -----------------------------------
            PAMBaseDropDownMenuWithBackground(
                selectedItem = addOperationPopUpUiState.selectedCategory,
                itemList = addOperationPopUpUiState.allCategories,
                onItemSelected = { category ->
                    viewModel.dispatchAction(
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
                    viewModel
                        .dispatchAction(AppActions.AddOperationPopUpAction.CloseRecurrentOption)
                },
                onRecurrentButtonSelected = {
                    viewModel
                        .dispatchAction(AppActions.AddOperationPopUpAction.ExpandRecurrentOption)
                },
                onMonthSelected = { endDateMonth ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectEndDateMonth(
                            endDateMonth
                        )
                    )
                },
                onYearSelected = { endDateYear ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectEndDateYear(
                            endDateYear
                        )
                    )
                },
                endDateSelectedMonth = addOperationPopUpUiState.enDateSelectedMonth,
                endDateSelectedYear = addOperationPopUpUiState.endDateSelectedYear,
                selectableMonthList = addOperationPopUpUiState.selectableEndDateMonths,
                selectableYearList = addOperationPopUpUiState.selectableEndDateYears,
                isRecurrentSwitchError = addOperationPopUpUiState.isRecurrentEndDateError
            )
            //Transfer Operation option---------------------------
            PAMTransferOptionPanel(
                isTransferOptionExpanded = addOperationPopUpUiState.isTransferExpanded,
                senderAccountSelectedItem = addOperationPopUpUiState.senderAccount,
                allAccountsList = addOperationPopUpUiState.allAccounts,
                beneficiaryAccountSelectedItem = addOperationPopUpUiState.beneficiaryAccount,
                onSenderAccountSelected = { senderAccount ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectSenderAccount(
                            senderAccount
                        )
                    )
                },
                onBeneficiaryAccountSelected = { beneficiaryAccount ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectBeneficiaryAccount(
                            beneficiaryAccount
                        )
                    )
                },
                isSenderAccountError = addOperationPopUpUiState.isSenderAccountError,
                isBeneficiaryAccountError = addOperationPopUpUiState.isBeneficiaryAccountError
            )
        }
    }
}

