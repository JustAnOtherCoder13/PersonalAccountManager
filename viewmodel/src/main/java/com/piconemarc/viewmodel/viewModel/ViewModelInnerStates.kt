package com.piconemarc.viewmodel.viewModel

import com.piconemarc.core.domain.Constants
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.OperationModel
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.VMState

object ViewModelInnerStates {

    data class GlobalVmState(
        var baseAppScreenVmState: BaseAppScreenVmState,
        var addOperationPopUpVMState: AddOperationPopUpVMState,
        var deleteAccountPopUpVMState: DeleteAccountPopUpVMState,
        var addAccountPopUpVMState: AddAccountPopUpVMState,
        var myAccountScreenVmState: MyAccountScreenVMState,
        var myAccountDetailScreenVMState: MyAccountDetailScreenVMState,
        var deleteOperationPopUpVmState : DeleteOperationPopUpVMState
    ) : VMState

    data class BaseAppScreenVmState(
        val selectedInterlayerButton: PAMIconButtons = PAMIconButtons.Home,
        val interLayerTitle: Int = com.piconemarc.model.R.string.myAccountsInterLayerTitle,
        val allAccounts: List<AccountModel> = listOf(),
        val footerTitle: PresentationDataModel = PresentationDataModel("All Accounts"),
        val footerBalance: PresentationDataModel = PresentationDataModel("0.0"),
        val footerRest: PresentationDataModel = PresentationDataModel("0.0")
    ) : VMState

    data class MyAccountScreenVMState(
        val allAccounts: List<AccountModel> = listOf(),
        val isVisible: Boolean = true
    ) : VMState

    data class MyAccountDetailScreenVMState(
        val accountName: PresentationDataModel = PresentationDataModel(),
        val actualMonth: PresentationDataModel = PresentationDataModel(),
        val accountMonthlyOperations: List<OperationModel> = listOf(),
        val accountBalance: PresentationDataModel = PresentationDataModel(),
        val accountRest: PresentationDataModel = PresentationDataModel(),
        val operationToDelete: OperationModel = OperationModel(),
        val isVisible: Boolean = false
    ) : VMState

    data class AddOperationPopUpVMState(
        val isPopUpExpanded: Boolean = false,
        val isPaymentExpanded: Boolean = false,
        val isRecurrentOptionExpanded: Boolean = false,
        val isTransferExpanded: Boolean = false,
        val allCategories: List<PresentationDataModel> = listOf(),
        val allAccounts: List<PresentationDataModel> = listOf(),
        val selectableEndDateYears: List<PresentationDataModel> = listOf(),
        val selectableEndDateMonths: List<PresentationDataModel> = listOf(),
        val selectedCategory: PresentationDataModel = PresentationDataModel(),
        val operationName: PresentationDataModel = PresentationDataModel(),
        val operationAmount: PresentationDataModel = PresentationDataModel(),
        val enDateSelectedMonth: PresentationDataModel = PresentationDataModel(),
        val endDateSelectedYear: PresentationDataModel = PresentationDataModel(),
        val senderAccount: PresentationDataModel = PresentationDataModel(),
        val beneficiaryAccount: PresentationDataModel = PresentationDataModel(),
        val addPopUpTitle: PresentationDataModel = Constants.OPERATION_MODEL,
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
        val accountToDeleteName: PresentationDataModel = PresentationDataModel(),
        val accountToDeleteBalance: PresentationDataModel = PresentationDataModel(),
        val accountToDelete: AccountModel = AccountModel()
    ) : VMState

    data class AddAccountPopUpVMState(
        val isPopUpExpanded: Boolean = false,
        val accountName: PresentationDataModel = PresentationDataModel(),
        val accountBalance: PresentationDataModel = PresentationDataModel(),
        val accountOverdraft: PresentationDataModel = PresentationDataModel(),
        val isNameError: Boolean = false,
        val isBalanceError: Boolean = true,
        val isOverdraftError: Boolean = true
    ) : VMState



}