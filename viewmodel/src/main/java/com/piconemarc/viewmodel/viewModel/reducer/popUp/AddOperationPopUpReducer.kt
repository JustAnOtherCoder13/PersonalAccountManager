package com.piconemarc.viewmodel.viewModel.reducer.popUp

import android.util.Log
import com.piconemarc.core.domain.utils.Constants
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.model.entity.CategoryUiModel
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.Reducer
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates

internal val addOperationPopUpReducer: Reducer<ViewModelInnerStates.AddOperationPopUpVMState> =
    { old, action ->
        action as AppActions.AddOpePopupAction
        when (action) {

            is AppActions.AddOpePopupAction.InitPopUp -> {
                old.copy(
                    isOnPaymentScreen = action.isOnPaymentScreen,
                    isAddOperation = false,
                    isPopUpExpanded = true,
                    isAddOrMinusEnable = true,
                    isTransferExpanded = false,
                    isRecurrentOptionExpanded = action.isOnPaymentScreen,
                    addPopUpOptionSelectedIcon = PAMIconButtons.Operation,
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

                    //isPaymentStartThisMonth = true,

                    isBeneficiaryAccountError = false,
                    isOperationNameError = false,
                    isOperationAmountError = false,

                    )
            }
            is AppActions.AddOpePopupAction.ClosePopUp -> {
                old.copy(
                    isPopUpExpanded = false
                )
            }
            is AppActions.AddOpePopupAction.OnOperationIconSelected -> {
                old.copy(
                    isPaymentExpanded = false,
                    isTransferExpanded = false,
                    isRecurrentOptionExpanded = false,
                    addPopUpTitle = Constants.OPERATION,
                    isAddOrMinusEnable = true,
                    isBeneficiaryAccountError = false,
                    beneficiaryAccount = AccountUiModel(name = Constants.BENEFICIARY_ACCOUNT),
                    addPopUpOptionSelectedIcon = PAMIconButtons.Operation,
                    operationAmount = getFormattedAmount(action.isAddOperation,action.operationAmount)
                )
            }
            is AppActions.AddOpePopupAction.OnPaymentIconSelected -> {
                old.copy(
                    isPaymentExpanded = true,
                    isTransferExpanded = false,
                    isRecurrentOptionExpanded = true,
                    addPopUpTitle = Constants.PAYMENT,
                    isAddOrMinusEnable = true,
                    isBeneficiaryAccountError = false,
                    beneficiaryAccount = AccountUiModel(name = Constants.BENEFICIARY_ACCOUNT),
                    addPopUpOptionSelectedIcon = PAMIconButtons.Payment,
                    operationAmount = getFormattedAmount(action.isAddOperation,action.operationAmount),
                    endDateSelectedYear = Constants.YEAR,
                    enDateSelectedMonth = Constants.MONTH,
                    )
            }
            is AppActions.AddOpePopupAction.OnTransferIconSelected -> {
                old.copy(
                    isPaymentExpanded = true,
                    isTransferExpanded = true,
                    isRecurrentOptionExpanded = false,
                    addPopUpTitle = Constants.TRANSFER_MODEL,
                    beneficiaryAccount = AccountUiModel(name = Constants.BENEFICIARY_ACCOUNT),
                    isAddOrMinusEnable = false,
                    addPopUpOptionSelectedIcon = PAMIconButtons.Transfer,
                    operationAmount = getFormattedAmount(true,action.operationAmount),
                )
            }
            is AppActions.AddOpePopupAction.OnAddOrMinusSelected -> {
                old.copy(
                    isAddOperation = action.isAddOperation,
                    operationAmount = getFormattedAmount(action.isAddOperation,action.operationAmount)
                )
            }
            is AppActions.AddOpePopupAction.OnFillOperationName -> {
                old.copy(
                    operationName = action.operationName
                )
            }
            is AppActions.AddOpePopupAction.OnFillOperationAmount -> {
                old.copy(
                    operationAmount = action.operationAmount,
                    isAddOperation = try {
                        action.operationAmount.toDouble() >= 0
                    }catch (e:NumberFormatException){
                        action.operationAmount != "-"
                    }
                )
            }
            is AppActions.AddOpePopupAction.OnSelectCategory -> {
                old.copy(
                    selectedCategory = action.selectedCategory
                )
            }
            is AppActions.AddOpePopupAction.OnPaymentEndDateSelected -> {
                try {
                    //if string could be cast to int it's a year
                    action.selectedMonthOrYear.toInt()
                    old.copy(endDateSelectedYear = action.selectedMonthOrYear)
                }catch (e:java.lang.NumberFormatException){
                    //else it's a month
                    old.copy(enDateSelectedMonth = action.selectedMonthOrYear)
                }
            }
            is AppActions.AddOpePopupAction.OnBeneficiaryAccountSelected -> {
                old.copy(
                    beneficiaryAccount = action.beneficiaryAccount
                )
            }
            is AppActions.AddOpePopupAction.OnRecurrentOptionSelected -> {
                old.copy(
                    isRecurrentOptionExpanded = action.isRecurrent,
                    isRecurrentEndDateError = action.isRecurrent,
                    endDateSelectedYear = Constants.YEAR,
                    enDateSelectedMonth = Constants.MONTH,
                    )
            }
            is AppActions.AddOpePopupAction.AddOperation -> old
            is AppActions.AddOpePopupAction.AddPayment -> old
            is AppActions.AddOpePopupAction.AddTransfer -> old

            is AppActions.AddOpePopupAction.LaunchIoThread -> {
                old.copy(
                    allAccounts = action.allAccounts?: listOf(),
                    allCategories = action.allCategories?: listOf(),
                    selectedAccount = action.selectedAccount?:AccountUiModel()
                )
            }
            is AppActions.AddOpePopupAction.CheckError -> {
                Log.e("TAG", "reduce: ${action.operationName} ${action.operationAmount}", )
                old.copy(
                    isOperationNameError = action.operationName.trim().isEmpty(),
                    isOperationAmountError = action.operationAmount.trim().isEmpty() || action.operationAmount.trim() == "-",
                    isRecurrentEndDateError = action.isRecurrentEndDateError,
                    isBeneficiaryAccountError = action.isBeneficiaryAccountError
                )
            }


            /*
                is AppActions.AddOperationPopUpAction.InitPopUp ->
                    old.copy(
                        isOnPaymentScreen = action.isOnPaymentScreen,
                        isPopUpExpanded = true,
                        selectedCategory = CategoryUiModel(),
                        addPopUpTitle = if (!action.isOnPaymentScreen)Constants.OPERATION else Constants.PAYMENT,
                        operationName = "",
                        operationAmount = "",
                        addPopUpOptionSelectedIcon = PAMIconButtons.Operation,
                        isPaymentExpanded = action.isOnPaymentScreen,
                        isPaymentStartThisMonth = true,
                        isAddOrMinusEnable = true,
                        isBeneficiaryAccountError = false,
                        isOperationNameError = false,
                        isOperationAmountError = false,
                        selectedAccountId = action.selectedAccountId
                    )

                is AppActions.AddOperationPopUpAction.ClosePopUp -> old.copy(
                    isPopUpExpanded = false,
                    isPaymentExpanded = false,
                    isRecurrentOptionExpanded = false,
                    isTransferExpanded = false,
                    )
                is AppActions.AddOperationPopUpAction.ExpandPaymentOption -> old.copy(
                    isPaymentExpanded = true,
                    isTransferExpanded = false,
                    addPopUpTitle = Constants.PAYMENT,
                    isAddOrMinusEnable = true,
                    isBeneficiaryAccountError = false,
                )
                is AppActions.AddOperationPopUpAction.CollapseOptions -> old.copy(
                    isPaymentExpanded = false,
                    isTransferExpanded = false,
                    addPopUpTitle = Constants.OPERATION,
                    isAddOrMinusEnable = true,
                    isBeneficiaryAccountError = false,
                )
                is AppActions.AddOperationPopUpAction.ExpandRecurrentOption -> old.copy(
                    isRecurrentOptionExpanded = true,
                    selectableEndDateYears = Constants.SELECTABLE_YEARS_LIST,
                    endDateSelectedYear = Constants.YEAR_MODEL,
                    selectableEndDateMonths = Constants.SELECTABLE_MONTHS_LIST,
                    enDateSelectedMonth = Constants.MONTH_MODEL,
                    isRecurrentEndDateError = true
                )
                is AppActions.AddOperationPopUpAction.CloseRecurrentOption -> old.copy(
                    isRecurrentOptionExpanded = false,
                    isRecurrentEndDateError = false
                )
                is AppActions.AddOperationPopUpAction.ExpandTransferOption -> old.copy(
                    isPaymentExpanded = true,
                    isTransferExpanded = true,
                    addPopUpTitle = Constants.TRANSFER_MODEL,
                    beneficiaryAccount = AccountUiModel(name = Constants.BENEFICIARY_ACCOUNT),
                    isAddOrMinusEnable = false,
                    selectedAccount = action.selectedAccount
                )
                is AppActions.AddOperationPopUpAction.SelectCategory -> old.copy(
                    selectedCategory = action.category
                )
                is AppActions.AddOperationPopUpAction.FillOperationName -> old.copy(
                    operationName = action.operation
                )
                is AppActions.AddOperationPopUpAction.UpdateCategoriesList -> old.copy(
                    allCategories = action.allCategories
                )
                is AppActions.AddOperationPopUpAction.UpdateAccountList -> {
                    old.copy(allAccounts = action.accountList)
                }
                is AppActions.AddOperationPopUpAction.FillOperationAmount -> old.copy(
                    operationAmount = action.amount,
                    isAddOperation = try {
                        action.amount.toDouble() >= 0
                    }catch (e:NumberFormatException){
                        action.amount != "-"
                    }
                )
                is AppActions.AddOperationPopUpAction.SelectEndDateYear -> {
                    old.copy(
                        endDateSelectedYear = action.selectedEndDateYear,
                        isRecurrentEndDateError = false //addOperationPopUpUiState.value.enDateSelectedMonth == "Month"
                    )
                }
                is AppActions.AddOperationPopUpAction.UpdateIsPaymentStartThisMonth->
                    old.copy(
                        isPaymentStartThisMonth = action.isPaymentStartThisMonth
                    )
                is AppActions.AddOperationPopUpAction.SelectEndDateMonth -> old.copy(
                    enDateSelectedMonth = action.selectedEndDateMonth,
                    isRecurrentEndDateError = false // addOperationPopUpUiState.value.endDateSelectedYear == "Year"
                )

                is AppActions.AddOperationPopUpAction.SelectBeneficiaryAccount -> old.copy(
                    beneficiaryAccount = action.beneficiaryAccountUi
                )
                is AppActions.AddOperationPopUpAction.SelectOptionIcon -> old.copy(
                    addPopUpOptionSelectedIcon = action.selectedIcon
                )
                is AppActions.AddOperationPopUpAction.SelectAddOrMinus -> old.copy(
                    isAddOperation = action.isAddOperation,
                    operationAmount = getFormattedAmount(action.isAddOperation,action.operationAmount)
                )
                is AppActions.AddOperationPopUpAction.AddNewOperation<*> -> old.copy(
                    isOperationNameError = action.operation.name.trim().isEmpty(),
                    isOperationAmountError = action.operation.amount == 0.0,
                    isBeneficiaryAccountError = action.isBeneficiaryAccountError
                )
            */
        }
    }

private fun getFormattedAmount(isAddOperation: Boolean, amount: String) =
    if (amount.trim().isNotEmpty()) {
        if (!isAddOperation)
            amount.prependIndent(if (amount[0] != ("-")[0]) "-" else "")
        else amount.drop(if (amount[0] == "-"[0]) 1 else 0)
    } else amount.prependIndent(if (isAddOperation) "" else ("-"))