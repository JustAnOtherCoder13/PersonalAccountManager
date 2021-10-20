package com.piconemarc.viewmodel.viewModel

import com.piconemarc.core.domain.Constants
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.PAMIconButtons
import com.piconemarc.viewmodel.Reducer

object AppReducers {

    val globalReducer: Reducer<ViewModelInnerStates.GlobalVmState> = { old, action ->
        action as AppActions.GlobalAction
        when (action) {
            is AppActions.GlobalAction.UpdateBaseAppScreenVmState -> {

                old.copy(
                    baseAppScreenVmState = appBaseScreenReducer(
                        old.baseAppScreenVmState,
                        action.baseAction
                    ),
                )
            }
            is AppActions.GlobalAction.UpdateAddPopUpState -> {
                old.copy(
                    addOperationPopUpVMState = addPopUpReducer(
                        old.addOperationPopUpVMState,
                        action.baseAction
                    )
                )

            }
        }
    }

    val appBaseScreenReducer : Reducer<ViewModelInnerStates.BaseAppScreenVmState> = {
            old, action ->
        action as AppActions.BaseAppScreenAction
        when (action){
            is AppActions.BaseAppScreenAction.InitScreen -> old.copy(
                selectedInterlayerButton = PAMIconButtons.Home
            )
            is AppActions.BaseAppScreenAction.SelectInterlayer -> {
                old.copy(selectedInterlayerButton = action.selectedInterlayerButton)
            }
            is AppActions.BaseAppScreenAction.UpdateAccounts -> old.copy(allAccounts = action.allAccounts)
            is AppActions.BaseAppScreenAction.UpdateFooterBalance -> old.copy(footerBalance = action.footerBalance)
            is AppActions.BaseAppScreenAction.UpdateFooterRest -> old.copy(footerRest = action.footerRest)
            is AppActions.BaseAppScreenAction.UpdateFooterTitle -> old.copy(footerTitle = action.footerTitle)
        }
    }

    val addPopUpReducer: Reducer<ViewModelInnerStates.AddOperationPopUpVMState> =
        { old, action ->
            action as AppActions.AddOperationPopUpAction
            when (action) {
                is AppActions.AddOperationPopUpAction.InitPopUp -> {
                    old.copy(
                        isPopUpExpanded = true,
                        selectedCategory = Constants.CATEGORY_MODEL,
                        addPopUpTitle = Constants.OPERATION_MODEL,
                        operationName = PresentationDataModel(),
                        operationAmount = PresentationDataModel(),
                        isAddOperation = true,
                        addPopUpOptionSelectedIcon = PAMIconButtons.Operation
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
                    addPopUpTitle = Constants.PAYMENT_MODEL
                )
                is AppActions.AddOperationPopUpAction.CollapseOptions -> old.copy(
                    isPaymentExpanded = false,
                    isRecurrentOptionExpanded = false,
                    isTransferExpanded = false,
                    addPopUpTitle = Constants.OPERATION_MODEL
                )
                is AppActions.AddOperationPopUpAction.ExpandRecurrentOption -> old.copy(
                    isRecurrentOptionExpanded = true,
                    selectableEndDateYears = Constants.SELECTABLE_YEARS_LIST,
                    endDateSelectedYear = Constants.YEAR_MODEL,
                    selectableEndDateMonths = Constants.SELECTABLE_MONTHS_LIST,
                    enDateSelectedMonth = Constants.MONTH_MODEL
                )
                is AppActions.AddOperationPopUpAction.CloseRecurrentOption -> old.copy(
                    isRecurrentOptionExpanded = false
                )
                is AppActions.AddOperationPopUpAction.ExpandTransferOption -> old.copy(
                    isPaymentExpanded = true,
                    isTransferExpanded = true,
                    addPopUpTitle = Constants.TRANSFER_MODEL,
                    senderAccount = Constants.SENDER_ACCOUNT_MODEL,
                    beneficiaryAccount = Constants.BENEFICIARY_ACCOUNT_MODEL
                )
                is AppActions.AddOperationPopUpAction.UpdateAccountList -> old.copy(
                    allAccounts = action.accountList
                )
                is AppActions.AddOperationPopUpAction.FillOperationAmount -> old.copy(
                    operationAmount = action.amount
                )
                is AppActions.AddOperationPopUpAction.SelectEndDateYear -> old.copy(
                    endDateSelectedYear = action.selectedEndDateYear
                )
                is AppActions.AddOperationPopUpAction.SelectEndDateMonth -> old.copy(
                    enDateSelectedMonth = action.selectedEndDateMonth
                )
                is AppActions.AddOperationPopUpAction.SelectSenderAccount -> old.copy(
                    senderAccount = action.senderAccount
                )
                is AppActions.AddOperationPopUpAction.SelectBeneficiaryAccount -> old.copy(
                    beneficiaryAccount = action.beneficiaryAccount
                )
                is AppActions.AddOperationPopUpAction.SelectOptionIcon -> old.copy(
                    addPopUpOptionSelectedIcon = action.selectedIcon
                )
                is AppActions.AddOperationPopUpAction.SelectAddOrMinus -> old.copy(
                    isAddOperation = action.isAddOperation
                )
            }
        }

}