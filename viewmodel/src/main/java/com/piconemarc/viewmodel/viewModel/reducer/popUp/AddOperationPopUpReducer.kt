package com.piconemarc.viewmodel.viewModel.reducer.popUp

import com.piconemarc.core.domain.utils.Constants
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.model.entity.CategoryUiModel
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.Reducer
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates

internal val addOperationPopUpReducer: Reducer<ViewModelInnerStates.AddOperationPopUpVMState> =
    { old, action ->
        action as AppActions.AddOperationPopupAction
        when (action) {

            is AppActions.AddOperationPopupAction.InitPopUp -> {
                old.copy(
                    isOnPaymentScreen = action.isOnPaymentScreen,
                    isPaymentStartThisMonth = true,
                    isAddOperation = false,
                    isVisible = true,
                    isAddOrMinusEnable = true,
                    isTransferExpanded = false,
                    isRecurrentOptionExpanded = action.isOnPaymentScreen,
                    addPopUpOptionSelectedIcon = if (action.isOnPaymentScreen) PAMIconButtons.Payment else PAMIconButtons.Operation,
                    isPaymentExpanded = action.isOnPaymentScreen,
                    addPopUpTitle = when (action.isOnPaymentScreen) {
                        true -> Constants.PAYMENT
                        else -> Constants.OPERATION
                    },

                    selectableEndDateYears = Constants.SELECTABLE_YEARS_LIST,
                    endDateSelectedYear = Constants.YEAR,
                    selectableEndDateMonths = Constants.SELECTABLE_MONTHS_LIST,
                    enDateSelectedMonth = Constants.MONTH,

                    selectedAccountId = action.selectedAccountId,
                    selectedCategory = CategoryUiModel(),

                    operationName = "",
                    operationAmount = "-",

                    isBeneficiaryAccountError = false,
                    isOperationNameError = false,
                    isOperationAmountError = false,
                )
            }
            is AppActions.AddOperationPopupAction.ClosePopUp -> {
                old.copy(
                    isVisible = false
                )
            }
            is AppActions.AddOperationPopupAction.OnOperationIconSelected -> {
                old.copy(
                    addPopUpTitle = Constants.OPERATION,
                    addPopUpOptionSelectedIcon = PAMIconButtons.Operation,

                    isPaymentExpanded = false,
                    isTransferExpanded = false,
                    isRecurrentOptionExpanded = false,
                    isAddOrMinusEnable = true,
                    isBeneficiaryAccountError = false,

                    operationAmount = getFormattedAmount(
                        action.isAddOperation,
                        action.operationAmount
                    )
                )
            }
            is AppActions.AddOperationPopupAction.OnPaymentIconSelected -> {
                old.copy(
                    addPopUpTitle = Constants.PAYMENT,
                    addPopUpOptionSelectedIcon = PAMIconButtons.Payment,
                    endDateSelectedYear = Constants.YEAR,
                    enDateSelectedMonth = Constants.MONTH,
                    beneficiaryAccount = AccountUiModel(name = Constants.BENEFICIARY_ACCOUNT),

                    isPaymentExpanded = true,
                    isTransferExpanded = false,
                    isRecurrentOptionExpanded = true,
                    isAddOrMinusEnable = true,
                    isBeneficiaryAccountError = false,

                    operationAmount = getFormattedAmount(
                        action.isAddOperation,
                        action.operationAmount
                    ),
                )
            }
            is AppActions.AddOperationPopupAction.OnTransferIconSelected -> {
                old.copy(
                    addPopUpOptionSelectedIcon = PAMIconButtons.Transfer,
                    addPopUpTitle = Constants.TRANSFER,
                    beneficiaryAccount = AccountUiModel(name = Constants.BENEFICIARY_ACCOUNT),

                    isPaymentExpanded = true,
                    isTransferExpanded = true,
                    isRecurrentOptionExpanded = false,
                    isAddOrMinusEnable = false,

                    operationAmount = getFormattedAmount(true, action.operationAmount),
                )
            }
            is AppActions.AddOperationPopupAction.OnAddOrMinusSelected -> {
                old.copy(
                    isAddOperation = action.isAddOperation,
                    operationAmount = getFormattedAmount(
                        action.isAddOperation,
                        action.operationAmount
                    )
                )
            }
            is AppActions.AddOperationPopupAction.OnFillOperationName -> {
                old.copy(
                    operationName = action.operationName
                )
            }
            is AppActions.AddOperationPopupAction.OnFillOperationAmount -> {
                old.copy(
                    operationAmount = action.operationAmount,
                    isAddOperation = try {
                        action.operationAmount.toDouble() >= 0
                    } catch (e: NumberFormatException) {
                        action.operationAmount != "-"
                    }
                )
            }
            is AppActions.AddOperationPopupAction.OnSelectCategory -> {
                old.copy(
                    selectedCategory = action.selectedCategory
                )
            }
            is AppActions.AddOperationPopupAction.OnPaymentEndDateSelected -> {
                try {
                    //if string could be cast to int it's a year
                    action.selectedMonthOrYear.toInt()
                    old.copy(
                        endDateSelectedYear = action.selectedMonthOrYear
                    )
                } catch (e: java.lang.NumberFormatException) {
                    //else it's a month
                    old.copy(
                        enDateSelectedMonth = action.selectedMonthOrYear
                    )
                }
            }
            is AppActions.AddOperationPopupAction.OnBeneficiaryAccountSelected -> {
                old.copy(
                    beneficiaryAccount = action.beneficiaryAccount
                )
            }
            is AppActions.AddOperationPopupAction.OnRecurrentOptionSelected -> {
                old.copy(
                    isRecurrentOptionExpanded = action.isRecurrent,
                    isRecurrentEndDateError = action.isRecurrent,
                    endDateSelectedYear = Constants.YEAR,
                    enDateSelectedMonth = Constants.MONTH,
                )
            }

            is AppActions.AddOperationPopupAction.UpdateState -> {
                old.copy(
                    allAccounts = action.allAccounts ?: listOf(),
                    allCategories = action.allCategories ?: listOf(),
                    selectedAccount = action.selectedAccount ?: AccountUiModel()
                )
            }
            is AppActions.AddOperationPopupAction.CheckError -> {
                old.copy(
                    isOperationNameError = action.operationName.trim().isEmpty(),
                    isOperationAmountError = action.operationAmount.toDouble() == 0.0,
                    isRecurrentEndDateError = action.paymentEndDate.first.trim()
                        .isNotEmpty() && action.paymentEndDate.second.trim().isNotEmpty(),
                    isBeneficiaryAccountError = action.beneficiaryAccount.id == 0L
                )
            }
            is AppActions.AddOperationPopupAction.OnIsPaymentStartThisMonthChecked -> {
                old.copy(
                    isPaymentStartThisMonth = action.isChecked
                )
            }
            is AppActions.AddOperationPopupAction.AddOperation -> old
            is AppActions.AddOperationPopupAction.AddPayment -> old
            is AppActions.AddOperationPopupAction.AddTransfer -> old
        }
    }

private fun getFormattedAmount(isAddOperation: Boolean, amount: String) =
    if (amount.trim().isNotEmpty()) {
        if (!isAddOperation)
            amount.prependIndent(if (amount[0] != ("-")[0]) "-" else "")
        else amount.drop(if (amount[0] == "-"[0]) 1 else 0)
    } else amount.prependIndent(if (isAddOperation) "" else ("-"))