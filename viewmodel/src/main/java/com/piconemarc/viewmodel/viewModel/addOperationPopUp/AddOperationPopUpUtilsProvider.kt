package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.core.domain.Constants
import com.piconemarc.core.domain.Constants.OPERATION_MODEL
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.viewModel.*


class AddOperationPopUpUtilsProvider : UtilsProvider<AddOperationPopUpUtilsProvider.AddOperationPopUpVMState> {

    override val providedUiState = AddOperationPopUpUiState

    object AddOperationPopUpUiState : UiState {
        val isPopUpExpanded by AddOperationPopUpMutableStates.isPoUpExpanded_
        val operationCategories by AddOperationPopUpMutableStates.allCategories
        val selectedCategoryName by AddOperationPopUpMutableStates.selectedCategoryName_
        val operationName by AddOperationPopUpMutableStates.operationName_
        val operationAmount by AddOperationPopUpMutableStates.operationAmount_
        val isPaymentExpanded by AddOperationPopUpMutableStates.isPaymentExpanded_
        val isRecurrentOptionExpanded by AddOperationPopUpMutableStates.isRecurrentOptionExpanded_
        val enDateSelectedMonth by AddOperationPopUpMutableStates.selectedEndDateMonth_
        val endDateSelectedYear by AddOperationPopUpMutableStates.selectedEndDateYear_
        val isTransferExpanded by AddOperationPopUpMutableStates.isTransferExpanded_
        val accountList by AddOperationPopUpMutableStates.allAccounts
        val senderAccount by AddOperationPopUpMutableStates.senderAccount_
        val beneficiaryAccount by AddOperationPopUpMutableStates.beneficiaryAccount_
        val addPopUpTitle by AddOperationPopUpMutableStates.addPopUpTitle_
        val selectableYearsList by AddOperationPopUpMutableStates.selectableYearsList_
        val selectableMonthsList by AddOperationPopUpMutableStates.selectableMonthsList_
    }

    override val providedVmState = AddOperationPopUpVMState()

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


    override val providedReducer: Reducer<AddOperationPopUpVMState> =
        { old, action ->
            action as AddOperationPopUpAction
            when (action) {
                is AddOperationPopUpAction.InitPopUp -> {
                    old.copy(
                        isPopUpExpanded = true,
                        selectedCategory = Constants.CATEGORY_MODEL,
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
                    addPopUpTitle = Constants.PAYMENT_MODEL
                )
                is AddOperationPopUpAction.CollapseOptions -> old.copy(
                    isPaymentExpanded = false,
                    isRecurrentOptionExpanded = false,
                    isTransferExpanded = false,
                    addPopUpTitle = OPERATION_MODEL
                )
                is AddOperationPopUpAction.ExpandRecurrentOption -> old.copy(
                    isRecurrentOptionExpanded = true,
                    selectableEndDateYears = Constants.SELECTABLE_YEARS_LIST,
                    endDateSelectedYear = Constants.YEAR_MODEL,
                    selectableEndDateMonths = Constants.SELECTABLE_MONTHS_LIST,
                    enDateSelectedMonth = Constants.MONTH_MODEL
                )
                is AddOperationPopUpAction.CloseRecurrentOption -> old.copy(
                    isRecurrentOptionExpanded = false
                )
                is AddOperationPopUpAction.ExpandTransferOption -> old.copy(
                    isPaymentExpanded = true,
                    isTransferExpanded = true,
                    addPopUpTitle = Constants.TRANSFER_MODEL,
                    senderAccount = Constants.SENDER_ACCOUNT_MODEL,
                    beneficiaryAccount = Constants.BENEFICIARY_ACCOUNT_MODEL
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

    override val providedSubscriber: StoreSubscriber<AddOperationPopUpVMState> =
        { addOperationPopUpState ->
            AddOperationPopUpMutableStates.isPoUpExpanded_.value =
                addOperationPopUpState.isPopUpExpanded

            AddOperationPopUpMutableStates.isPaymentExpanded_.value =
                addOperationPopUpState.isPaymentExpanded

            AddOperationPopUpMutableStates.isRecurrentOptionExpanded_.value =
                addOperationPopUpState.isRecurrentOptionExpanded

            AddOperationPopUpMutableStates.isTransferExpanded_.value =
                addOperationPopUpState.isTransferExpanded

            AddOperationPopUpMutableStates.allCategories.value =
                addOperationPopUpState.allCategories

            AddOperationPopUpMutableStates.allAccounts.value =
                addOperationPopUpState.allAccounts

            AddOperationPopUpMutableStates.selectableYearsList_.value =
                addOperationPopUpState.selectableEndDateYears

            AddOperationPopUpMutableStates.selectableMonthsList_.value =
                addOperationPopUpState.selectableEndDateMonths

            AddOperationPopUpMutableStates.selectedCategoryName_.value =
                addOperationPopUpState.selectedCategory

            AddOperationPopUpMutableStates.operationName_.value =
                addOperationPopUpState.operationName

            AddOperationPopUpMutableStates.operationAmount_.value =
                addOperationPopUpState.operationAmount

            AddOperationPopUpMutableStates.addPopUpTitle_.value =
                addOperationPopUpState.addPopUpTitle

            AddOperationPopUpMutableStates.selectedEndDateYear_.value =
                addOperationPopUpState.endDateSelectedYear

            AddOperationPopUpMutableStates.selectedEndDateMonth_.value =
                addOperationPopUpState.enDateSelectedMonth

            AddOperationPopUpMutableStates.senderAccount_.value =
                addOperationPopUpState.senderAccount

            AddOperationPopUpMutableStates.beneficiaryAccount_.value =
                addOperationPopUpState.beneficiaryAccount
        }

    private object AddOperationPopUpMutableStates {
        //EXPAND AND COLLAPSE VALUES --------------------------------------------------------------
        val isPoUpExpanded_: MutableState<Boolean> = mutableStateOf(false)
        val isPaymentExpanded_: MutableState<Boolean> = mutableStateOf(false)
        val isRecurrentOptionExpanded_: MutableState<Boolean> = mutableStateOf(false)
        val isTransferExpanded_: MutableState<Boolean> = mutableStateOf(false)

        //DATA PRESENTATION VALUES -------------------------------------------------------------------
        val allCategories: MutableState<List<PresentationDataModel>> =
            mutableStateOf(listOf())
        val allAccounts: MutableState<List<PresentationDataModel>> =
            mutableStateOf(listOf())
        val selectableYearsList_: MutableState<List<PresentationDataModel>> =
            mutableStateOf(listOf())
        val selectableMonthsList_: MutableState<List<PresentationDataModel>> =
            mutableStateOf(listOf())

        val selectedCategoryName_: MutableState<PresentationDataModel> = mutableStateOf(
            PresentationDataModel()
        )
        val operationName_: MutableState<PresentationDataModel> = mutableStateOf(
            PresentationDataModel()
        )
        val operationAmount_: MutableState<PresentationDataModel> = mutableStateOf(
            PresentationDataModel()
        )
        val addPopUpTitle_: MutableState<PresentationDataModel> = mutableStateOf(
            PresentationDataModel()
        )
        val selectedEndDateYear_: MutableState<PresentationDataModel> = mutableStateOf(
            PresentationDataModel()
        )
        val selectedEndDateMonth_: MutableState<PresentationDataModel> = mutableStateOf(
            PresentationDataModel()
        )
        val senderAccount_: MutableState<PresentationDataModel> = mutableStateOf(
            PresentationDataModel()
        )
        val beneficiaryAccount_: MutableState<PresentationDataModel> = mutableStateOf(
            PresentationDataModel()
        )
    }
}