package com.piconemarc.viewmodel.viewModel

import com.piconemarc.core.domain.Constants
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.viewmodel.VMState

object ViewModelInnerStates {
    data class BaseAppScreenVmState(
        val selectedInterlayerButton: PAMIconButtons = PAMIconButtons.Home,
        val interLayerTitle: Int = com.piconemarc.model.R.string.myAccountsInterLayerTitle,
        val allAccountUis: List<AccountUiModel> = listOf(),
        val footerTitle: String = "All Accounts",
        val footerBalance: Double = 0.0,
        val footerRest: Double = 0.0
    ) : VMState

    data class MyAccountScreenVMState(
        val allAccountUis: List<AccountUiModel> = listOf(),
        val isVisible: Boolean = true
    ) : VMState

    data class MyAccountDetailScreenVMState(
        val selectedAccount: AccountUiModel = AccountUiModel(),
        val accountMonthlyOperations: List<OperationUiModel> = listOf(),
        val actualMonth: String = "",
        val isVisible: Boolean = false
    ) : VMState

    data class AddOperationPopUpVMState(
        val isPopUpExpanded: Boolean = false,
        val isPaymentExpanded: Boolean = false,
        val isRecurrentOptionExpanded: Boolean = false,
        val isTransferExpanded: Boolean = false,
        val isAddOperation: Boolean = true,
        val isAddOrMinusEnable : Boolean = true,
        val allCategories: List<CategoryModel> = listOf(),
        val allAccountUis: List<AccountUiModel> = listOf(),
        val selectableEndDateYears: List<String> = listOf(),
        val selectableEndDateMonths: List<String> = listOf(),
        val selectedCategory: CategoryModel = CategoryModel(),
        val operationName: String = "",
        val operationAmount: String = "",
        val enDateSelectedMonth: String = "",
        val endDateSelectedYear: String = "",
        val senderAccountUi: AccountUiModel = AccountUiModel(),
        val beneficiaryAccountUi: AccountUiModel = AccountUiModel(),
        val addPopUpTitle: String = Constants.OPERATION_MODEL,
        val addPopUpOptionSelectedIcon: PAMIconButtons = PAMIconButtons.Operation,
        val isOperationNameError : Boolean=false,
        val isOperationAmountError : Boolean=false,
        val isRecurrentEndDateError : Boolean = true,
        val isSenderAccountError : Boolean = false,
        val isBeneficiaryAccountError : Boolean = false,

        ) : VMState

    data class DeleteOperationPopUpVMState(
        val isPopUpExpanded: Boolean=false,
        val operationToDelete : OperationUiModel = OperationUiModel()
    ) : VMState

    data class DeleteAccountPopUpVMState(
        val isPopUpExpanded: Boolean = false,
        val accountUiToDelete: AccountUiModel = AccountUiModel()
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