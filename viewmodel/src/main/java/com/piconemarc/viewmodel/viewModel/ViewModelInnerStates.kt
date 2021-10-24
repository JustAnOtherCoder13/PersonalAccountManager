package com.piconemarc.viewmodel.viewModel

import com.piconemarc.core.domain.Constants
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.model.entity.OperationModel
import com.piconemarc.viewmodel.VMState

object ViewModelInnerStates {
    data class BaseAppScreenVmState(
        val selectedInterlayerButton: PAMIconButtons = PAMIconButtons.Home,
        val interLayerTitle: Int = com.piconemarc.model.R.string.myAccountsInterLayerTitle,
        val allAccounts: List<AccountModel> = listOf(),
        val footerTitle: String = "All Accounts",
        val footerBalance: String = "0.0",
        val footerRest: String = "0.0"
    ) : VMState

    data class MyAccountScreenVMState(
        val allAccounts: List<AccountModel> = listOf(),
        val isVisible: Boolean = true
    ) : VMState

    data class MyAccountDetailScreenVMState(
        val selectedAccount: AccountModel = AccountModel(),
        val actualMonth: String = "",
        val accountMonthlyOperations: List<OperationModel> = listOf(),
        val accountBalance: String = "",
        val accountRest: String = "",
        val operationToDelete: OperationModel = OperationModel(),
        val isVisible: Boolean = false
    ) : VMState

    data class AddOperationPopUpVMState(
        val isPopUpExpanded: Boolean = false,
        val isPaymentExpanded: Boolean = false,
        val isRecurrentOptionExpanded: Boolean = false,
        val isTransferExpanded: Boolean = false,
        val allCategories: List<CategoryModel> = listOf(),
        val allAccounts: List<AccountModel> = listOf(),
        val selectableEndDateYears: List<String> = listOf(),
        val selectableEndDateMonths: List<String> = listOf(),
        val selectedCategory: CategoryModel = CategoryModel(),
        val operationName: String = "",
        val operationAmount: String = "",
        val enDateSelectedMonth: String = "",
        val endDateSelectedYear: String = "",
        val senderAccount: AccountModel = AccountModel(),
        val beneficiaryAccount: AccountModel = AccountModel(),
        val addPopUpTitle: String = Constants.OPERATION_MODEL,
        val isAddOperation: Boolean = true,
        val addPopUpOptionSelectedIcon: PAMIconButtons = PAMIconButtons.Operation,
        val isOperationNameError : Boolean=false,
        val isOperationAmountError : Boolean=false,
        val isRecurrentEndDateError : Boolean = true,
        val isSenderAccountError : Boolean = false,
        val isBeneficiaryAccountError : Boolean = false,
        val isAddOrMinusEnable : Boolean = true
    ) : VMState

    data class DeleteOperationPopUpVMState(
        val isPopUpExpanded: Boolean=false,
        val operationToDelete : OperationModel = OperationModel()
    ) : VMState

    data class DeleteAccountPopUpVMState(
        val isPopUpExpanded: Boolean = false,
        val accountToDeleteName: String = "",
        val accountToDeleteBalance: String = "",
        val accountToDelete: AccountModel = AccountModel()
    ) : VMState

    data class AddAccountPopUpVMState(
        val isPopUpExpanded: Boolean = false,
        val accountName: String = "",
        val accountBalance: String = "",
        val accountOverdraft: String = "",
        val isNameError: Boolean = false,
        val isBalanceError: Boolean = true,
        val isOverdraftError: Boolean = true
    ) : VMState
}