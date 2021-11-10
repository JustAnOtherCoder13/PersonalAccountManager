package com.piconemarc.personalaccountmanager.ui.component.popUp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.pAMAddOperationPopUpBackgroundColor
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.AddOperationPopUpLeftSideMenuIconPanel
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.AddOperationPopUpPunctualOrRecurrentSwitchButton
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.AddOperationPopUpTransferOptionPanel
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.*
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates

@Composable
fun AddOperationPopUp(
    addOperationPopUpState: ViewModelInnerStates.AddOperationPopUpVMState,
    onAddOperationPopUpEvent: (action: AppActions.AddOpePopupAction) -> Unit
) {
    //Pop up Body --------------------------------------------
    BasePopUp(
        title = addOperationPopUpState.addPopUpTitle,
        onAcceptButtonClicked = {
            val newOperation = OperationUiModel(
                name =addOperationPopUpState.operationName,
                amount = try {
                    addOperationPopUpState.operationAmount.toDouble()
                }catch (e:Exception){ 0.0 } ,
                categoryId = addOperationPopUpState.selectedCategory.id,
                accountId = addOperationPopUpState.selectedAccountId,
            )
            onAddOperationPopUpEvent(
                when (addOperationPopUpState.addPopUpOptionSelectedIcon) {
                    is PAMIconButtons.Operation -> {
                        AppActions.AddOpePopupAction.AddOperation(
                            newOperation = newOperation,
                            isOperationError = checkOperationNameAndAmountError(addOperationPopUpState)
                        )
                    }
                    is PAMIconButtons.Payment -> {
                        AppActions.AddOpePopupAction.AddPayment(
                            isOnPaymentScreen = addOperationPopUpState.isOnPaymentScreen,
                            newOperation = newOperation,
                            isOperationError = checkOperationNameAndAmountError(addOperationPopUpState),
                            paymentEndDate = Pair(addOperationPopUpState.enDateSelectedMonth, addOperationPopUpState.endDateSelectedYear),
                            isPaymentStartThisMonth = addOperationPopUpState.isPaymentStartThisMonth
                        )
                    }
                    else -> {
                        AppActions.AddOpePopupAction.AddTransfer

                    }
                }
            )
        },
        onDismiss = { onAddOperationPopUpEvent(AppActions.AddOpePopupAction.ClosePopUp) },
        isExpanded = addOperationPopUpState.isPopUpExpanded,
        menuIconPanel = {
            //if on payment screen no need to show left side icon panel, cause could only be payment
            if (!addOperationPopUpState.isOnPaymentScreen)
                AddOperationPopUpLeftSideMenuIconPanel(
                    onIconButtonClicked = { pamIconButton ->
                        when (pamIconButton) {
                            is PAMIconButtons.Operation -> {
                                onAddOperationPopUpEvent(
                                    AppActions.AddOpePopupAction.OnOperationIconSelected(
                                        isAddOperation = addOperationPopUpState.isAddOperation,
                                        operationAmount = addOperationPopUpState.operationAmount
                                    )
                                )
                            }
                            is PAMIconButtons.Payment -> {
                                onAddOperationPopUpEvent(
                                    AppActions.AddOpePopupAction.OnPaymentIconSelected(
                                        isAddOperation = addOperationPopUpState.isAddOperation,
                                        operationAmount = addOperationPopUpState.operationAmount
                                    )
                                )
                            }
                            is PAMIconButtons.Transfer -> {
                                onAddOperationPopUpEvent(
                                    AppActions.AddOpePopupAction.OnTransferIconSelected(
                                        operationAmount = addOperationPopUpState.operationAmount
                                    )
                                )
                            }
                            else -> {
                            }
                        }
                    },
                    selectedIcon = addOperationPopUpState.addPopUpOptionSelectedIcon
                )
        },
        popUpBackgroundColor = pAMAddOperationPopUpBackgroundColor(
            isAddOperation = addOperationPopUpState.isAddOperation,
            isAddOrMinusEnable = addOperationPopUpState.isAddOrMinusEnable
        ).value
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //add or minus switch
            AddOperationPopUpAddOrMinusSwitchButton(
                onAddOrMinusClicked = { isAddClicked ->
                    onAddOperationPopUpEvent(
                        AppActions.AddOpePopupAction.OnAddOrMinusSelected(
                            isAddClicked,
                            addOperationPopUpState.operationAmount
                        )
                    )
                },
                isAddOperation = addOperationPopUpState.isAddOperation,
                isEnable = addOperationPopUpState.isAddOrMinusEnable
            )
            // operation name--------------------------
            BrownBackgroundTextFieldItem(
                title = stringResource(R.string.operationName),
                onTextChange = { operationName ->
                    onAddOperationPopUpEvent(
                        AppActions.AddOpePopupAction.OnFillOperationName(
                            operationName
                        )
                    )
                },
                textValue = addOperationPopUpState.operationName,
                isPopUpExpanded = addOperationPopUpState.isPopUpExpanded,
                isError = addOperationPopUpState.isOperationNameError,
                errorMsg = stringResource(R.string.AddOperationPopUpOperationNameErrorMessage)
            )
            // operation Amount--------------------------
            BrownBackgroundAmountTextFieldItem(
                title = stringResource(R.string.operationAmount),
                onTextChange = { operationAmount ->
                    onAddOperationPopUpEvent(
                        AppActions.AddOpePopupAction.OnFillOperationAmount(
                            operationAmount
                        )
                    )
                },
                amountValue = addOperationPopUpState.operationAmount,
                isPopUpExpanded = addOperationPopUpState.isPopUpExpanded,
                isError = addOperationPopUpState.isOperationAmountError,
                errorMsg = stringResource(R.string.AddOperationPopUpAmountErrorMessage)
            )
            //category drop down -----------------------------------
            PAMBaseDropDownMenuWithBackground(
                selectedItem = addOperationPopUpState.selectedCategory,
                itemList = addOperationPopUpState.allCategories,
                onItemSelected = { category ->
                    onAddOperationPopUpEvent(
                        AppActions.AddOpePopupAction.OnSelectCategory(
                            category
                        )
                    )
                }
            )
            //Payment Operation option--------------------------
            AddOperationPopUpPunctualOrRecurrentSwitchButton(
                isRecurrentOptionExpanded = addOperationPopUpState.isRecurrentOptionExpanded,
                isPaymentOptionExpanded = addOperationPopUpState.isPaymentExpanded,
                onPunctualButtonSelected = {
                    onAddOperationPopUpEvent(
                        AppActions.AddOpePopupAction.OnRecurrentOptionSelected(
                            false
                        )
                    )
                },
                onRecurrentButtonSelected = {
                    onAddOperationPopUpEvent(
                        AppActions.AddOpePopupAction.OnRecurrentOptionSelected(
                            true
                        )
                    )
                },
                onMonthSelected = { endDateMonth ->
                    onAddOperationPopUpEvent(
                        AppActions.AddOpePopupAction.OnPaymentEndDateSelected(
                            endDateMonth
                        )
                    )
                },
                onYearSelected = { endDateYear ->
                    onAddOperationPopUpEvent(
                        AppActions.AddOpePopupAction.OnPaymentEndDateSelected(
                            endDateYear
                        )
                    )
                },
                endDateSelectedMonth = addOperationPopUpState.enDateSelectedMonth,
                endDateSelectedYear = addOperationPopUpState.endDateSelectedYear,
                selectableMonthList = addOperationPopUpState.selectableEndDateMonths,
                selectableYearList = addOperationPopUpState.selectableEndDateYears,
                isRecurrentSwitchError = addOperationPopUpState.isRecurrentEndDateError
            )
            //Transfer Operation option---------------------------
            AddOperationPopUpTransferOptionPanel(
                isTransferOptionExpanded = addOperationPopUpState.isTransferExpanded,
                senderAccount = addOperationPopUpState.selectedAccount,
                allAccountsList = addOperationPopUpState.allAccounts,
                beneficiaryAccountUiSelectedItem = addOperationPopUpState.beneficiaryAccount,

                onBeneficiaryAccountSelected = { beneficiaryAccount ->
                    onAddOperationPopUpEvent(
                        AppActions.AddOpePopupAction.OnBeneficiaryAccountSelected(
                            beneficiaryAccount
                        )
                    )
                },
                isBeneficiaryAccountError = addOperationPopUpState.isBeneficiaryAccountError
            )
            if (addOperationPopUpState.isOnPaymentScreen)
                OptionCheckBox(
                    onCheckedChange = {
                        onAddOperationPopUpEvent(
                            AppActions.AddOpePopupAction.OnIsPaymentStartThisMonthChecked(it)
                        )
                    },
                    isChecked = addOperationPopUpState.isPaymentStartThisMonth,
                    optionText = stringResource(R.string.AddOperationPopUpAddPaymentOptionMessage)
                )
        }
    }
}


private fun checkOperationNameAndAmountError(addOperationPopUpState: ViewModelInnerStates.AddOperationPopUpVMState) =
    (addOperationPopUpState.operationName.trim().isEmpty()
            || (addOperationPopUpState.operationAmount.trim().isEmpty()
            || addOperationPopUpState.operationAmount.trim() == "-"))