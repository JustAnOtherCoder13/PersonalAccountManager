package com.piconemarc.viewmodel.viewModel

import android.util.Log
import com.piconemarc.core.domain.Constants
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.PAMIconButtons
import com.piconemarc.viewmodel.Reducer
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.addOperationPopUpUiState

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
            is AppActions.GlobalAction.UpdateAddOperationPopUpState -> {
                old.copy(
                    addOperationPopUpVMState = addOperationPopUpReducer(
                        old.addOperationPopUpVMState,
                        action.baseAction
                    )
                )
            }
            is AppActions.GlobalAction.UpdateDeleteAccountPopUpState -> {
                old.copy(
                    deleteAccountPopUpVMState = deleteAccountPopUpReducer(
                        old.deleteAccountPopUpVMState,
                        action.baseAction
                    )
                )
            }
            is AppActions.GlobalAction.UpdateAddAccountPopUpState -> {
                old.copy(
                    addAccountPopUpVMState = addAccountPopUpReducer(
                        old.addAccountPopUpVMState,
                        action.baseAction
                    )
                )
            }
            is AppActions.GlobalAction.UpdateMyAccountDetailScreenState -> {
                old.copy(
                    myAccountDetailScreenVMState = myAccountDetailScreenReducer(
                        old.myAccountDetailScreenVMState,
                        action.baseAction
                    )
                )
            }
            is AppActions.GlobalAction.UpdateMyAccountScreenState -> {
                old.copy(
                    myAccountScreenVmState = myAccountScreenReducer(
                        old.myAccountScreenVmState,
                        action.baseAction
                    )
                )
            }
            is AppActions.GlobalAction.UpdateDeleteOperationPopUpState -> {
                old.copy(
                    deleteOperationPopUpVmState = deleteOperationPopUpReducer(
                        old.deleteOperationPopUpVmState,
                        action.baseAction
                    )
                )
            }
        }
    }

    val appBaseScreenReducer: Reducer<ViewModelInnerStates.BaseAppScreenVmState> = { old, action ->
        action as AppActions.BaseAppScreenAction
        when (action) {
            is AppActions.BaseAppScreenAction.InitScreen -> old.copy(
                selectedInterlayerButton = PAMIconButtons.Home
            )
            is AppActions.BaseAppScreenAction.SelectInterlayer -> {
                old.copy(
                    selectedInterlayerButton = action.selectedInterlayerButton,
                    interLayerTitle = action.selectedInterlayerButton.iconName
                )
            }
            is AppActions.BaseAppScreenAction.UpdateAccounts -> old.copy(allAccounts = action.allAccounts)
            is AppActions.BaseAppScreenAction.UpdateFooterBalance -> {
                var allAccountBalance = 0.0
                action.allAccounts.forEach {

                    allAccountBalance += it.accountBalance
                }
                old.copy(
                    footerBalance = PresentationDataModel(allAccountBalance.toString())
                )
            }
            is AppActions.BaseAppScreenAction.UpdateFooterRest -> {
                var allAccountRest = 0.0
                action.allAccounts.forEach {
                    allAccountRest += (it.accountBalance + it.accountOverdraft)
                }
                old.copy(
                    footerRest = PresentationDataModel(allAccountRest.toString())
                )
            }
            is AppActions.BaseAppScreenAction.UpdateFooterTitle -> old.copy(footerTitle = action.footerTitle)
            is AppActions.BaseAppScreenAction.UpdateInterlayerTiTle -> old.copy(interLayerTitle = action.interlayerTitle)

        }
    }

    val addOperationPopUpReducer: Reducer<ViewModelInnerStates.AddOperationPopUpVMState> =
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
                    isRecurrentOptionExpanded = false,
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
                    isRecurrentOptionExpanded = false,
                    addPopUpTitle = Constants.TRANSFER_MODEL,
                    senderAccount = Constants.SENDER_ACCOUNT_MODEL,
                    beneficiaryAccount = Constants.BENEFICIARY_ACCOUNT_MODEL,
                    isAddOrMinusEnable = false,
                )
                is AppActions.AddOperationPopUpAction.UpdateAccountList -> old.copy(
                    allAccounts = action.accountList
                )
                is AppActions.AddOperationPopUpAction.FillOperationAmount -> old.copy(
                    operationAmount = action.amount
                )
                is AppActions.AddOperationPopUpAction.SelectEndDateYear -> {
                    old.copy(
                        endDateSelectedYear = action.selectedEndDateYear,
                        isRecurrentEndDateError = addOperationPopUpUiState.enDateSelectedMonth.stringValue == "Month"
                    )
                }
                is AppActions.AddOperationPopUpAction.SelectEndDateMonth -> old.copy(
                    enDateSelectedMonth = action.selectedEndDateMonth,
                    isRecurrentEndDateError = addOperationPopUpUiState.endDateSelectedYear.stringValue == "Year"
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
                is AppActions.AddOperationPopUpAction.AddNewOperation -> old.copy(
                    isOperationNameError = action.operation.name.trim().isEmpty(),
                    isOperationAmountError = action.operation.amount == 0.0,
                    isSenderAccountError = addOperationPopUpUiState.addPopUpOptionSelectedIcon == PAMIconButtons.Transfer
                            && addOperationPopUpUiState.senderAccount.stringValue == "Sender account" ,
                    isBeneficiaryAccountError = addOperationPopUpUiState.addPopUpOptionSelectedIcon == PAMIconButtons.Transfer
                            && addOperationPopUpUiState.beneficiaryAccount.stringValue == "Beneficiary account" ,
                )

            }
        }

    val deleteAccountPopUpReducer: Reducer<ViewModelInnerStates.DeleteAccountPopUpVMState> =
        { old, action ->
            action as AppActions.DeleteAccountAction
            when (action) {
                is AppActions.DeleteAccountAction.InitPopUp -> old.copy(
                    isPopUpExpanded = true,
                )
                is AppActions.DeleteAccountAction.ClosePopUp -> old.copy(
                    isPopUpExpanded = false,
                    accountToDelete = AccountModel()
                )
                is AppActions.DeleteAccountAction.DeleteAccount -> old.copy(
                    isPopUpExpanded = false,
                )
                is AppActions.DeleteAccountAction.UpdateAccountToDelete -> old.copy(
                    accountToDelete = action.accountToDelete,
                    accountToDeleteBalance = PresentationDataModel(
                        action.accountToDelete.accountBalance.toString(),
                        action.accountToDelete.id
                    ),
                    accountToDeleteName = PresentationDataModel(
                        action.accountToDelete.name,
                        action.accountToDelete.id
                    )
                )
            }
        }

    val addAccountPopUpReducer: Reducer<ViewModelInnerStates.AddAccountPopUpVMState> =
        { old, action ->
            action as AppActions.AddAccountPopUpAction
            when (action) {
                is AppActions.AddAccountPopUpAction.InitPopUp -> old.copy(
                    isPopUpExpanded = true,
                    accountName = PresentationDataModel(),
                    accountBalance = PresentationDataModel(),
                    accountOverdraft = PresentationDataModel(),
                    isBalanceError = true,
                    isOverdraftError = true
                )
                is AppActions.AddAccountPopUpAction.ClosePopUp -> old.copy(
                    isPopUpExpanded = false
                )
                is AppActions.AddAccountPopUpAction.AddNewAccount -> old.copy(
                    isNameError = action.accountName.stringValue.trim().isEmpty()
                )
                is AppActions.AddAccountPopUpAction.FillAccountName -> old.copy(
                    accountName = action.accountName
                )
                is AppActions.AddAccountPopUpAction.FillAccountBalance -> old.copy(
                    accountBalance = action.accountBalance,
                    isBalanceError = action.accountBalance.stringValue.trim().isEmpty()
                )
                is AppActions.AddAccountPopUpAction.FillAccountOverdraft -> old.copy(
                    accountOverdraft = action.accountOverdraft,
                    isOverdraftError = action.accountOverdraft.stringValue.trim().isEmpty()
                )
            }
        }

    val myAccountScreenReducer: Reducer<ViewModelInnerStates.MyAccountScreenVMState> =
        { old, action ->
            action as AppActions.MyAccountScreenAction
            when (action) {
                is AppActions.MyAccountScreenAction.InitScreen -> {
                    Log.d("TAG", ": ")
                    old.copy(
                        isVisible = true
                    )
                }
                is AppActions.MyAccountScreenAction.CloseScreen -> old.copy(
                    isVisible = false
                )
                is AppActions.MyAccountScreenAction.UpdateAccountList -> {

                    old.copy(
                        allAccounts = action.accountList
                    )
                }

            }
        }

    val myAccountDetailScreenReducer: Reducer<ViewModelInnerStates.MyAccountDetailScreenVMState> =
        { old, action ->
            action as AppActions.MyAccountDetailScreenAction
            when (action) {
                is AppActions.MyAccountDetailScreenAction.InitScreen -> old.copy(
                    isVisible = true
                )
                is AppActions.MyAccountDetailScreenAction.CloseScreen -> old.copy(
                    isVisible = false
                )
                is AppActions.MyAccountDetailScreenAction.UpdateAccountBalance -> old.copy(
                    accountBalance = action.accountBalance
                )
                is AppActions.MyAccountDetailScreenAction.UpdateAccountRest -> old.copy(
                    accountRest = action.accountRest
                )
                is AppActions.MyAccountDetailScreenAction.UpdateAccountMonthlyOperations -> old.copy(
                    accountMonthlyOperations = action.accountMonthlyOperations
                )
                is AppActions.MyAccountDetailScreenAction.UpdateAccountName -> old.copy(
                    accountName = action.accountName
                )
            }
        }

    val deleteOperationPopUpReducer: Reducer<ViewModelInnerStates.DeleteOperationPopUpVMState> =
        { old, action ->
            action as AppActions.DeleteOperationPopUpAction
            when (action) {
                is AppActions.DeleteOperationPopUpAction.InitPopUp -> old.copy(
                    isPopUpExpanded = true
                )
                is AppActions.DeleteOperationPopUpAction.ClosePopUp -> old.copy(
                    isPopUpExpanded = false
                )
                is AppActions.DeleteOperationPopUpAction.UpdateOperationToDelete -> old.copy(
                    operationToDelete = action.operationToDelete
                )
                is AppActions.DeleteOperationPopUpAction.DeleteOperation -> old.copy(
                    isPopUpExpanded = false
                )
            }
        }

}