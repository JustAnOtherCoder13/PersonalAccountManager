package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.viewModel.ActionDispatcher
import com.piconemarc.viewmodel.viewModel.DefaultStore
import com.piconemarc.viewmodel.viewModel.StoreSubscriber
import com.piconemarc.viewmodel.viewModel.UiAction
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

//EXPAND AND COLLAPSE VALUES --------------------------------------------------------------
internal val isPoUpExpanded_: MutableState<Boolean> = mutableStateOf(false)
internal val isPaymentExpanded_: MutableState<Boolean> = mutableStateOf(false)
internal val isRecurrentOptionExpanded_: MutableState<Boolean> = mutableStateOf(false)
internal val isTransferExpanded_: MutableState<Boolean> = mutableStateOf(false)

//DATA PRESENTATION VALUES -------------------------------------------------------------------
internal val allCategories: MutableState<List<PresentationDataModel>> =
    mutableStateOf(listOf())
internal val allAccounts: MutableState<List<PresentationDataModel>> =
    mutableStateOf(listOf())
internal val selectableYearsList_: MutableState<List<PresentationDataModel>> =
    mutableStateOf(listOf())
internal val selectableMonthsList_: MutableState<List<PresentationDataModel>> =
    mutableStateOf(listOf())

internal val selectedCategoryName_: MutableState<PresentationDataModel> = mutableStateOf(
    PresentationDataModel()
)
internal val operationName_: MutableState<PresentationDataModel> = mutableStateOf(
    PresentationDataModel()
)
internal val operationAmount_: MutableState<PresentationDataModel> = mutableStateOf(
    PresentationDataModel()
)
internal val addPopUpTitle_: MutableState<PresentationDataModel> = mutableStateOf(
    PresentationDataModel()
)
internal val selectedEndDateYear_: MutableState<PresentationDataModel> = mutableStateOf(
    PresentationDataModel()
)
internal val selectedEndDateMonth_: MutableState<PresentationDataModel> = mutableStateOf(
    PresentationDataModel()
)
internal val senderAccount_ : MutableState<PresentationDataModel> = mutableStateOf(
    PresentationDataModel()
)
internal val beneficiaryAccount_ : MutableState<PresentationDataModel> = mutableStateOf(
    PresentationDataModel()
)



class AddOperationPopUpActionDispatcher @Inject constructor(
    private val getAllCategoriesInteractor: GetAllCategoriesInteractor,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
    private val addOperationPopUpStore: DefaultStore<AddOperationPopUpGlobalState>
) : ActionDispatcher<AddOperationPopUpAction,AddOperationPopUpGlobalState> {
    private var getCategoriesJob: Job? = null
    private var getAllAccountsJob: Job? = null

    override val store: DefaultStore<AddOperationPopUpGlobalState>
        get() = addOperationPopUpStore

    //action dispatcher
    override fun dispatchAction(action: AddOperationPopUpAction) {
        when (action) {
            is AddOperationPopUpAction.Init -> initPopUp()
            is AddOperationPopUpAction.ClosePopUp -> closePopUp()
            is AddOperationPopUpAction.CollapseOptions -> collapseOptions()
            is AddOperationPopUpAction.ExpandPaymentOption -> expandPaymentOption()
            is AddOperationPopUpAction.ExpandTransferOption -> expandTransferOption()
            is AddOperationPopUpAction.ExpandRecurrentOption -> expandRecurrentOption()
            is AddOperationPopUpAction.CloseRecurrentOption -> collapseRecurrentOption()
            is AddOperationPopUpAction.SelectCategory -> selectCategory(action.category)
            is AddOperationPopUpAction.FillOperationName -> fillOperationName(action.operation)
            is AddOperationPopUpAction.UpdateAccountList -> updateAccountsList(action.accountList)
            is AddOperationPopUpAction.UpdateCategoriesList -> updateCategoriesList(action.allCategories)
            is AddOperationPopUpAction.FillOperationAmount -> fillOperationAmount(action.amount)
            is AddOperationPopUpAction.SelectEndDateYear -> selectEndDateYear(action.selectedEndDateYear)
            is AddOperationPopUpAction.SelectEndDateMonth -> selectEndDateMonth(action.selectedEndDateMonth)
            is AddOperationPopUpAction.SelectSenderAccount -> selectSenderAccount(action.senderAccount)
            is AddOperationPopUpAction.SelectBeneficiaryAccount -> selectBeneficiaryAccount(action.beneficiaryAccount)
        }
    }

    // IMPLEMENT ACTIONS ---------------------------------------------------------------------------------


    private fun selectBeneficiaryAccount(beneficiaryAccount: PresentationDataModel) {
        store.dispatch(
            AddOperationPopUpAction.SelectBeneficiaryAccount(
                beneficiaryAccount
            )
        )
    }

    private fun selectSenderAccount(senderAccount: PresentationDataModel) {
        store.dispatch(AddOperationPopUpAction.SelectSenderAccount(senderAccount))

    }

    private fun selectEndDateMonth(selectedEndDateMonth: PresentationDataModel) {
        store.dispatch(
            AddOperationPopUpAction.SelectEndDateMonth(
                selectedEndDateMonth
            )
        )
    }

    private fun selectEndDateYear(selectedEndDateYear: PresentationDataModel) {
        store.dispatch(
            AddOperationPopUpAction.SelectEndDateYear(
                selectedEndDateYear
            )
        )
    }

    private fun fillOperationAmount(amount: PresentationDataModel) {
        store.dispatch(AddOperationPopUpAction.FillOperationAmount(amount))
    }

    private fun initPopUp() {
        addSubscriber()
        getCategoriesJob = scope.launch {
            getAllCategoriesInteractor.getAllCategoriesToDataUiModelList().collect { allCategories ->
                updateCategoriesList(allCategories)
            }
        }
        store.dispatch(AddOperationPopUpAction.Init)
    }

    private fun closePopUp() {
        store.dispatch(AddOperationPopUpAction.ClosePopUp)
        removeSubscriber()
        scope.cancel()
    }

    private fun collapseOptions() {
        store.dispatch(AddOperationPopUpAction.CollapseOptions)
    }

    private fun expandPaymentOption() {
        store.dispatch(AddOperationPopUpAction.ExpandPaymentOption)
    }

    private fun expandTransferOption() {
        getAllAccountsJob = scope.launch {
            getAllAccountsInteractor.getAllAccountsToDataUiModelList().collect { allAccounts ->
                updateAccountsList(allAccounts)
            }
        }
        store.dispatch(AddOperationPopUpAction.ExpandTransferOption)
    }

    private fun expandRecurrentOption() {
        store.dispatch(AddOperationPopUpAction.ExpandRecurrentOption)

    }

    private fun collapseRecurrentOption() {
        store.dispatch(AddOperationPopUpAction.CloseRecurrentOption)
    }

    private fun selectCategory(selectedCategoryUiModel: PresentationDataModel) {
        store.dispatch(
            AddOperationPopUpAction.SelectCategory(selectedCategoryUiModel)
        )
    }

    private fun fillOperationName(operation: PresentationDataModel) {
        store.dispatch(
            AddOperationPopUpAction.FillOperationName(
                operation
            )
        )
    }

    private fun updateCategoriesList(updatedCategoriesList: List<PresentationDataModel>) {
        store.dispatch(
            AddOperationPopUpAction.UpdateCategoriesList(updatedCategoriesList)
        )
    }

    private fun updateAccountsList(updatedAccountList: List<PresentationDataModel>) {
        store.dispatch(
            AddOperationPopUpAction.UpdateAccountList(updatedAccountList)
        )
    }


    //Subscriber to get updatedState
    override val subscriber: StoreSubscriber<AddOperationPopUpGlobalState> =
        { addOperationPopUpGlobalState ->
            isPoUpExpanded_.value =
                addOperationPopUpGlobalState.isPopUpExpanded

            isPaymentExpanded_.value =
                addOperationPopUpGlobalState.isPaymentExpanded

            isRecurrentOptionExpanded_.value =
                addOperationPopUpGlobalState.isRecurrentOptionExpanded

            isTransferExpanded_.value =
                addOperationPopUpGlobalState.isTransferExpanded

            allCategories.value =
                addOperationPopUpGlobalState.allCategories

            allAccounts.value =
                addOperationPopUpGlobalState.allAccounts

            selectableYearsList_.value =
                addOperationPopUpGlobalState.selectableEndDateYears

            selectableMonthsList_.value =
                addOperationPopUpGlobalState.selectableEndDateMonths

            selectedCategoryName_.value =
                addOperationPopUpGlobalState.selectedCategory

            operationName_.value =
                addOperationPopUpGlobalState.operationName

            operationAmount_.value =
                addOperationPopUpGlobalState.operationAmount

            addPopUpTitle_.value =
                addOperationPopUpGlobalState.addPopUpTitle

            selectedEndDateYear_.value =
                addOperationPopUpGlobalState.endDateSelectedYear

            selectedEndDateMonth_.value =
                addOperationPopUpGlobalState.enDateSelectedMonth

            senderAccount_.value =
                addOperationPopUpGlobalState.senderAccount

            beneficiaryAccount_.value =
                addOperationPopUpGlobalState.beneficiaryAccount
        }
}

sealed class AddOperationPopUpAction : UiAction {
    object Init : AddOperationPopUpAction()
    object ExpandPaymentOption : AddOperationPopUpAction()
    object CollapseOptions : AddOperationPopUpAction()
    object ExpandTransferOption : AddOperationPopUpAction()
    object ExpandRecurrentOption : AddOperationPopUpAction()
    object CloseRecurrentOption : AddOperationPopUpAction()
    object ClosePopUp : AddOperationPopUpAction()
    data class SelectCategory(val category: PresentationDataModel) : AddOperationPopUpAction()
    data class FillOperationName(val operation: PresentationDataModel) : AddOperationPopUpAction()
    data class FillOperationAmount(val amount: PresentationDataModel) : AddOperationPopUpAction()
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