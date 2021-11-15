package com.piconemarc.viewmodel.viewModel.utils

import com.piconemarc.core.domain.utils.Constants
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.*

object ViewModelInnerStates {
    data class BaseAppScreenVmState(
        val selectedInterlayerButton: PAMIconButtons = PAMIconButtons.Home,
        val interLayerTitle: Int = com.piconemarc.model.R.string.myAccountsInterLayerTitle,
        val allAccountUis: List<AccountUiModel> = listOf(),
        val footerBalance: Double = 0.0,
        val footerRest: Double = 0.0
    ) : VMState

    data class MyAccountScreenVMState(
        val allAccounts: List<AccountUiModel> = listOf(),
        val isVisible: Boolean = true
    ) : VMState

    data class MyAccountDetailScreenVMState(
        val selectedAccount: AccountUiModel = AccountUiModel(),
        val accountMonthlyOperations: List<OperationUiModel> = listOf(),
        val isVisible: Boolean = false,
        val operationDetailMessage : String = ""
    ) : VMState

    data class PaymentScreenVmState(
        val allAccounts: List<AccountWithRelatedPaymentUiModel> = listOf(),
        val isVisible: Boolean = false,
    ): VMState

    data class AddOperationPopUpVMState(
        val addPopUpTitle: String = Constants.OPERATION,
        val addPopUpOptionSelectedIcon: PAMIconButtons = PAMIconButtons.Operation,

        val isPopUpExpanded: Boolean = false,
        val isPaymentExpanded: Boolean = false,
        val isTransferExpanded: Boolean = false,
        val isRecurrentOptionExpanded: Boolean = false,
        val isOnPaymentScreen : Boolean = false,

        val isAddOperation: Boolean = true,
        val isAddOrMinusEnable: Boolean = true,
        val isPaymentStartThisMonth : Boolean = false,

        val selectedAccountId : Long = 0,
        val allCategories: List<CategoryUiModel> = listOf(),
        val allAccounts: List<AccountUiModel> = listOf(),
        val selectableEndDateYears: List<String> = listOf(),
        val selectableEndDateMonths: List<String> = listOf(),
        val selectedCategory: CategoryUiModel = CategoryUiModel(),
        val selectedAccount: AccountUiModel = AccountUiModel(),
        val beneficiaryAccount: AccountUiModel = AccountUiModel(),

        val operationName: String = "",
        val operationAmount: String = "",
        val enDateSelectedMonth: String = "",
        val endDateSelectedYear: String = "",

        val isOperationNameError: Boolean = false,
        val isOperationAmountError: Boolean = false,
        val isRecurrentEndDateError: Boolean = true,
        val isBeneficiaryAccountError: Boolean = false,

    ) : VMState

    data class DeleteOperationPopUpVMState(
        val isPopUpExpanded: Boolean = false,
        val operationToDelete: BaseOperation = BaseOperation(),
        val isRelatedOperationDeleted: Boolean = true,
        val transferRelatedAccount: AccountUiModel = AccountUiModel(),
    ) : VMState

    data class DeleteAccountPopUpVMState(
        val isPopUpExpanded: Boolean = false,
        val accountToDelete: AccountUiModel = AccountUiModel()
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