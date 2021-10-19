package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import com.piconemarc.core.domain.Constants.BENEFICIARY_ACCOUNT_MODEL
import com.piconemarc.core.domain.Constants.CATEGORY_MODEL
import com.piconemarc.core.domain.Constants.MONTH_MODEL
import com.piconemarc.core.domain.Constants.OPERATION_MODEL
import com.piconemarc.core.domain.Constants.PAYMENT_MODEL
import com.piconemarc.core.domain.Constants.SELECTABLE_MONTHS_LIST
import com.piconemarc.core.domain.Constants.SELECTABLE_YEARS_LIST
import com.piconemarc.core.domain.Constants.SENDER_ACCOUNT_MODEL
import com.piconemarc.core.domain.Constants.TRANSFER_MODEL
import com.piconemarc.core.domain.Constants.YEAR_MODEL
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.viewModel.Reducer
import com.piconemarc.viewmodel.viewModel.UiAction
import com.piconemarc.viewmodel.viewModel.VMState


class AddOperationPopUpUtilsProvider  {

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
        val addPopUpTitle: PresentationDataModel = OPERATION_MODEL
    ) : VMState

    sealed class AddOperationPopUpAction : UiAction {
        object InitPopUp : AddOperationPopUpAction()
        object ExpandPaymentOption : AddOperationPopUpAction()
        object CollapseOptions : AddOperationPopUpAction()
        object ExpandTransferOption : AddOperationPopUpAction()
        object ExpandRecurrentOption : AddOperationPopUpAction()
        object CloseRecurrentOption : AddOperationPopUpAction()
        object ClosePopUp : AddOperationPopUpAction()
        data class SelectCategory(val category: PresentationDataModel) : AddOperationPopUpAction()
        data class FillOperationName(val operation: PresentationDataModel) :
            AddOperationPopUpAction()

        data class FillOperationAmount(val amount: PresentationDataModel) :
            AddOperationPopUpAction()

        data class UpdateCategoriesList(val allCategories: List<PresentationDataModel>) :
            AddOperationPopUpAction()

        data class UpdateAccountList(val accountList: List<PresentationDataModel>) :
            AddOperationPopUpAction()

        data class SelectEndDateYear(val selectedEndDateYear: PresentationDataModel) :
            AddOperationPopUpAction()

        data class SelectEndDateMonth(val selectedEndDateMonth: PresentationDataModel) :
            AddOperationPopUpAction()

        data class SelectSenderAccount(val senderAccount: PresentationDataModel) :
            AddOperationPopUpAction()

        data class SelectBeneficiaryAccount(val beneficiaryAccount: PresentationDataModel) :
            AddOperationPopUpAction()
    }


    val providedReducer: Reducer<AddOperationPopUpVMState> =
        { old, action ->
            action as AddOperationPopUpAction
            when (action) {
                is AddOperationPopUpAction.InitPopUp -> {
                    old.copy(
                        isPopUpExpanded = true,
                        selectedCategory = CATEGORY_MODEL,
                        addPopUpTitle = OPERATION_MODEL,
                        operationName = PresentationDataModel(),
                        operationAmount = PresentationDataModel(),
                    )
                }
                is AddOperationPopUpAction.ClosePopUp -> old.copy(
                    isPopUpExpanded = false,
                    isPaymentExpanded = false,
                    isRecurrentOptionExpanded = false,
                    isTransferExpanded = false,
                )
                is AddOperationPopUpAction.SelectCategory -> old.copy(
                    selectedCategory = action.category
                )
                is AddOperationPopUpAction.FillOperationName -> old.copy(
                    operationName = action.operation
                )
                is AddOperationPopUpAction.UpdateCategoriesList -> old.copy(
                    allCategories = action.allCategories
                )
                is AddOperationPopUpAction.ExpandPaymentOption -> old.copy(
                    isPaymentExpanded = true,
                    isTransferExpanded = false,
                    addPopUpTitle = PAYMENT_MODEL
                )
                is AddOperationPopUpAction.CollapseOptions -> old.copy(
                    isPaymentExpanded = false,
                    isRecurrentOptionExpanded = false,
                    isTransferExpanded = false,
                    addPopUpTitle = OPERATION_MODEL
                )
                is AddOperationPopUpAction.ExpandRecurrentOption -> old.copy(
                    isRecurrentOptionExpanded = true,
                    selectableEndDateYears = SELECTABLE_YEARS_LIST,
                    endDateSelectedYear = YEAR_MODEL,
                    selectableEndDateMonths = SELECTABLE_MONTHS_LIST,
                    enDateSelectedMonth = MONTH_MODEL
                )
                is AddOperationPopUpAction.CloseRecurrentOption -> old.copy(
                    isRecurrentOptionExpanded = false
                )
                is AddOperationPopUpAction.ExpandTransferOption -> old.copy(
                    isPaymentExpanded = true,
                    isTransferExpanded = true,
                    addPopUpTitle = TRANSFER_MODEL,
                    senderAccount = SENDER_ACCOUNT_MODEL,
                    beneficiaryAccount = BENEFICIARY_ACCOUNT_MODEL
                )
                is AddOperationPopUpAction.UpdateAccountList -> old.copy(
                    allAccounts = action.accountList
                )
                is AddOperationPopUpAction.FillOperationAmount -> old.copy(
                    operationAmount = action.amount
                )
                is AddOperationPopUpAction.SelectEndDateYear -> old.copy(
                    endDateSelectedYear = action.selectedEndDateYear
                )
                is AddOperationPopUpAction.SelectEndDateMonth -> old.copy(
                    enDateSelectedMonth = action.selectedEndDateMonth
                )
                is AddOperationPopUpAction.SelectSenderAccount -> old.copy(
                    senderAccount = action.senderAccount
                )
                is AddOperationPopUpAction.SelectBeneficiaryAccount -> old.copy(
                    beneficiaryAccount = action.beneficiaryAccount
                )
            }
        }
}