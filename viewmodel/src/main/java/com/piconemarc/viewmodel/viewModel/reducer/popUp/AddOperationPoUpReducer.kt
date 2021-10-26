package com.piconemarc.viewmodel.viewModel.reducer.popUp

import com.piconemarc.core.domain.Constants
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.model.entity.CategoryUiModel
import com.piconemarc.viewmodel.Reducer
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.ViewModelInnerStates
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.addOperationPopUpUiState

internal val addOperationPopUpReducer: Reducer<ViewModelInnerStates.AddOperationPopUpVMState> =
    { old, action ->
        action as AppActions.AddOperationPopUpAction
        when (action) {
            is AppActions.AddOperationPopUpAction.InitPopUp -> {
                old.copy(
                    isPopUpExpanded = true,
                    selectedCategory = CategoryUiModel(),
                    addPopUpTitle = Constants.OPERATION_MODEL,
                    operationName = "",
                    operationAmount = "",
                    isAddOperation = true,
                    addPopUpOptionSelectedIcon = PAMIconButtons.Operation,
                    isAddOrMinusEnable = true,
                    isBeneficiaryAccountError = false,
                    isOperationNameError = false,
                    isOperationAmountError = false
                )
            }
            is AppActions.AddOperationPopUpAction.ClosePopUp -> old.copy(
                isPopUpExpanded = false,
                isPaymentExpanded = false,
                isRecurrentOptionExpanded = false,
                isTransferExpanded = false,
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
            is AppActions.AddOperationPopUpAction.ExpandPaymentOption -> old.copy(
                isPaymentExpanded = true,
                isTransferExpanded = false,
                addPopUpTitle = Constants.PAYMENT_MODEL,
                isAddOrMinusEnable = true,
                isBeneficiaryAccountError = false,
            )
            is AppActions.AddOperationPopUpAction.CollapseOptions -> old.copy(
                isPaymentExpanded = false,
                isTransferExpanded = false,
                addPopUpTitle = Constants.OPERATION_MODEL,
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
                beneficiaryAccount = AccountUiModel(name = Constants.BENEFICIARY_ACCOUNT_MODEL),
                isAddOrMinusEnable = false,
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
                    isRecurrentEndDateError = addOperationPopUpUiState.enDateSelectedMonth == "Month"
                )
            }
            is AppActions.AddOperationPopUpAction.SelectEndDateMonth -> old.copy(
                enDateSelectedMonth = action.selectedEndDateMonth,
                isRecurrentEndDateError = addOperationPopUpUiState.endDateSelectedYear == "Year"
            )

            is AppActions.AddOperationPopUpAction.SelectBeneficiaryAccount -> old.copy(
                beneficiaryAccount = action.beneficiaryAccountUi
            )
            is AppActions.AddOperationPopUpAction.SelectOptionIcon -> old.copy(
                addPopUpOptionSelectedIcon = action.selectedIcon
            )
            is AppActions.AddOperationPopUpAction.SelectAddOrMinus -> old.copy(
                isAddOperation = action.isAddOperation,
                operationAmount =if (!action.isAddOperation) "-"+addOperationPopUpUiState.operationAmount else addOperationPopUpUiState.operationAmount
            )
            is AppActions.AddOperationPopUpAction.AddNewOperation -> old.copy(
                isOperationNameError = action.operation.name.trim().isEmpty(),
                isOperationAmountError = action.operation.amount == 0.0,
                isBeneficiaryAccountError = addOperationPopUpUiState.addPopUpOptionSelectedIcon == PAMIconButtons.Transfer
                        && addOperationPopUpUiState.beneficiaryAccount.name == "Beneficiary account" ,
            )
        }
    }