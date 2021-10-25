package com.piconemarc.viewmodel.viewModel.reducer.popUp

import com.piconemarc.core.domain.Constants
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.viewmodel.Reducer
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.ViewModelInnerStates
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber

internal val addOperationPopUpReducer: Reducer<ViewModelInnerStates.AddOperationPopUpVMState> =
    { old, action ->
        action as AppActions.AddOperationPopUpAction
        when (action) {
            is AppActions.AddOperationPopUpAction.InitPopUp -> {
                old.copy(
                    isPopUpExpanded = true,
                    selectedCategory = CategoryModel(),
                    addPopUpTitle = Constants.OPERATION_MODEL,
                    operationName = "",
                    operationAmount = "",
                    isAddOperation = true,
                    addPopUpOptionSelectedIcon = PAMIconButtons.Operation,
                    isAddOrMinusEnable = true,
                    isSenderAccountError = false,
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
                isSenderAccountError = false,
                isBeneficiaryAccountError = false,
            )
            is AppActions.AddOperationPopUpAction.CollapseOptions -> old.copy(
                isPaymentExpanded = false,
                isTransferExpanded = false,
                addPopUpTitle = Constants.OPERATION_MODEL,
                isAddOrMinusEnable = true,
                isSenderAccountError = false,
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
                senderAccountUi = AccountUiModel(name = Constants.SENDER_ACCOUNT_MODEL),
                beneficiaryAccountUi = AccountUiModel(name = Constants.BENEFICIARY_ACCOUNT_MODEL),
                isAddOrMinusEnable = false,
            )
            is AppActions.AddOperationPopUpAction.UpdateAccountList -> old.copy(
                allAccountUis = action.accountUiList
            )
            is AppActions.AddOperationPopUpAction.FillOperationAmount -> old.copy(
                operationAmount = action.amount
            )
            is AppActions.AddOperationPopUpAction.SelectEndDateYear -> {
                old.copy(
                    endDateSelectedYear = action.selectedEndDateYear,
                    isRecurrentEndDateError = AppSubscriber.AppUiState.addOperationPopUpUiState.enDateSelectedMonth == "Month"
                )
            }
            is AppActions.AddOperationPopUpAction.SelectEndDateMonth -> old.copy(
                enDateSelectedMonth = action.selectedEndDateMonth,
                isRecurrentEndDateError = AppSubscriber.AppUiState.addOperationPopUpUiState.endDateSelectedYear == "Year"
            )
            is AppActions.AddOperationPopUpAction.SelectSenderAccount -> old.copy(
                senderAccountUi = action.senderAccountUi
            )
            is AppActions.AddOperationPopUpAction.SelectBeneficiaryAccount -> old.copy(
                beneficiaryAccountUi = action.beneficiaryAccountUi
            )
            is AppActions.AddOperationPopUpAction.SelectOptionIcon -> old.copy(
                //addPopUpOptionSelectedIcon = action.selectedIcon
            )
            is AppActions.AddOperationPopUpAction.SelectAddOrMinus -> old.copy(
                isAddOperation = action.isAddOperation
            )
            is AppActions.AddOperationPopUpAction.AddNewOperation -> old.copy(
                isOperationNameError = action.operation.name.trim().isEmpty(),
                isOperationAmountError = action.operation.amount == 0.0,
                isSenderAccountError = AppSubscriber.AppUiState.addOperationPopUpUiState.addPopUpOptionSelectedIcon == PAMIconButtons.Transfer
                        && AppSubscriber.AppUiState.addOperationPopUpUiState.senderAccountUi.name == "Sender account" ,
                isBeneficiaryAccountError = AppSubscriber.AppUiState.addOperationPopUpUiState.addPopUpOptionSelectedIcon == PAMIconButtons.Transfer
                        && AppSubscriber.AppUiState.addOperationPopUpUiState.beneficiaryAccountUi.name == "Beneficiary account" ,
            )

        }
    }