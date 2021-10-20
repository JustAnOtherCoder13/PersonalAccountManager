package com.piconemarc.viewmodel.viewModel

import com.piconemarc.core.domain.Constants
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.PAMIconButtons
import com.piconemarc.viewmodel.VMState

object ViewModelInnerStates {

    data class GlobalVmState(
        var baseAppScreenVmState: BaseAppScreenVmState = BaseAppScreenVmState(),
        var addOperationPopUpVMState: AddOperationPopUpVMState = AddOperationPopUpVMState()
    ) : VMState

    data class BaseAppScreenVmState(
        val selectedInterlayerButton : PAMIconButtons = PAMIconButtons.Home,
        val allAccounts : List<AccountModel> =  listOf(),
        val footerTitle : PresentationDataModel = PresentationDataModel("All Accounts"),
        val footerBalance : PresentationDataModel = PresentationDataModel("0.0"),
        val footerRest : PresentationDataModel = PresentationDataModel("0.0")
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
        val isAddOperation : Boolean = true,
        val addPopUpOptionSelectedIcon : PAMIconButtons = PAMIconButtons.Operation
    ) : VMState

}