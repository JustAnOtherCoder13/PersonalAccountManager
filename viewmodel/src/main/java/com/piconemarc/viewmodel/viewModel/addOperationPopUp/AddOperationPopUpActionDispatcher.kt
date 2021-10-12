package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.core.domain.AddOperationPopUpGlobalState
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.viewModel.DefaultStore
import com.piconemarc.viewmodel.viewModel.PAMUiAction
import com.piconemarc.viewmodel.viewModel.StoreSubscriber
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

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
) : CoroutineScope {
    // Scope and job to launch data request
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private var getCategoriesJob: Job? = null
    private var getAllAccountsJob: Job? = null

    //action dispatcher
    fun dispatchAction(action: AddOperationPopUpAction) {
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
        addOperationPopUpStore.dispatch(
            AddOperationPopUpAction.SelectBeneficiaryAccount(
                beneficiaryAccount
            )
        )
    }

    private fun selectSenderAccount(senderAccount: PresentationDataModel) {
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.SelectSenderAccount(senderAccount))

    }

    private fun selectEndDateMonth(selectedEndDateMonth: PresentationDataModel) {
        addOperationPopUpStore.dispatch(
            AddOperationPopUpAction.SelectEndDateMonth(
                selectedEndDateMonth
            )
        )
    }

    private fun selectEndDateYear(selectedEndDateYear: PresentationDataModel) {
        addOperationPopUpStore.dispatch(
            AddOperationPopUpAction.SelectEndDateYear(
                selectedEndDateYear
            )
        )
    }

    private fun fillOperationAmount(amount: PresentationDataModel) {
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.FillOperationAmount(amount))
    }

    private fun initPopUp() {
        addSubscriber()
        getCategoriesJob = scope.launch {
            getAllCategoriesInteractor.getAllCategoriesToDataUiModelList().collect { allCategories ->
                updateCategoriesList(allCategories)
            }
        }
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.Init)
    }

    private fun closePopUp() {
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.ClosePopUp)
        removeSubscriber()
        scope.cancel()
    }

    private fun collapseOptions() {
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.CollapseOptions)
    }

    private fun expandPaymentOption() {
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.ExpandPaymentOption)
    }

    private fun expandTransferOption() {
        getAllAccountsJob = scope.launch {
            getAllAccountsInteractor.getAllAccountsToDataUiModelList().collect { allAccounts ->
                updateAccountsList(allAccounts)
            }
        }
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.ExpandTransferOption)
    }

    private fun expandRecurrentOption() {
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.ExpandRecurrentOption)

    }

    private fun collapseRecurrentOption() {
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.CloseRecurrentOption)
    }

    private fun selectCategory(selectedCategoryUiModel: PresentationDataModel) {
        addOperationPopUpStore.dispatch(
            AddOperationPopUpAction.SelectCategory(selectedCategoryUiModel)
        )
    }

    private fun fillOperationName(operation: PresentationDataModel) {
        addOperationPopUpStore.dispatch(
            AddOperationPopUpAction.FillOperationName(
                operation
            )
        )
    }

    private fun updateCategoriesList(updatedCategoriesList: List<PresentationDataModel>) {
        addOperationPopUpStore.dispatch(
            AddOperationPopUpAction.UpdateCategoriesList(updatedCategoriesList)
        )
    }

    private fun updateAccountsList(updatedAccountList: List<PresentationDataModel>) {
        addOperationPopUpStore.dispatch(
            AddOperationPopUpAction.UpdateAccountList(updatedAccountList)
        )
    }

    private fun addSubscriber() = addOperationPopUpStore.add(subscriber)
    private fun removeSubscriber() = addOperationPopUpStore.remove(subscriber)


    //Subscriber to get updatedState
    private val subscriber: StoreSubscriber<AddOperationPopUpGlobalState> =
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

sealed class AddOperationPopUpAction : PAMUiAction {
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