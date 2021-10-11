package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.core.domain.AddOperationPopUpGlobalState
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.model.entity.DataUiModel
import com.piconemarc.model.entity.OperationModel
import com.piconemarc.viewmodel.viewModel.DefaultStore
import com.piconemarc.viewmodel.viewModel.StoreSubscriber
import com.piconemarc.viewmodel.viewModel.UiAction
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

internal val isPoUpExpanded_: MutableState<Boolean> = mutableStateOf(false)
internal val isPaymentExpanded_: MutableState<Boolean> = mutableStateOf(false)
internal val isRecurrentOptionExpanded_: MutableState<Boolean> = mutableStateOf(false)
internal val isTransferExpanded_: MutableState<Boolean> = mutableStateOf(false)
internal val operationCategories_: MutableState<List<DataUiModel>> = mutableStateOf(listOf())
internal val operationAccounts_: MutableState<List<DataUiModel>> = mutableStateOf(listOf())
internal val allAccounts_: MutableState<List<AccountModel>> = mutableStateOf(listOf())
internal val selectedCategoryName_: MutableState<DataUiModel> =
    mutableStateOf(DataUiModel("Category", 0))
internal val operationName_: MutableState<String> = mutableStateOf("")
internal val operationAmount_: MutableState<String> = mutableStateOf("")

//internal value
internal val allCategories_: MutableState<List<CategoryModel>> = mutableStateOf(listOf())
internal val selectedCategory_: MutableState<CategoryModel> = mutableStateOf(CategoryModel())
internal val operation_: MutableState<OperationModel> = mutableStateOf(OperationModel())


class AddOperationScreenEvent @Inject constructor(
    private val getAllCategoriesInteractor: GetAllCategoriesInteractor,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
    private val addOperationPopUpStore: DefaultStore<AddOperationPopUpGlobalState>
) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)
    private var getCategoriesJob: Job? = null
    private var getAllAccountsJob: Job? = null

    fun initPopUp() {
        addSubscriber()
        getCategoriesJob = scope.launch {
            getAllCategoriesInteractor.getAllCategories().collect {
                updateCategoriesList(it)
            }
        }
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.Init)
    }

    fun closePopUp() {
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.ClosePopUp)
        removeSubscriber()
        scope.cancel()

    }

    fun collapseOptions() {
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.CollapseOptions)
    }

    fun expandPaymentOption() {
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.ExpandPaymentOption)
    }

    fun expandTransferOption() {
        getAllAccountsJob = scope.launch {
            getAllAccountsInteractor.getAllAccounts().collect { allAccounts ->
                updateAccountsList(allAccounts)
            }
        }
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.ExpandTransferOption)
    }

    fun expandRecurrentOption() {
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.ExpandRecurrentOption)
    }

    fun collapseRecurrentOption() {
        addOperationPopUpStore.dispatch(AddOperationPopUpAction.CloseRecurrentOption)
    }

    fun selectCategory(selectedCategoryId: Long) {
        addOperationPopUpStore.dispatch(
            AddOperationPopUpAction.SelectCategory(
                allCategories_.value.find { selectedCategoryId == it.id } ?: selectedCategory_.value
            )
        )
    }

    fun fillOperationName(operationName: String) {
        addOperationPopUpStore.dispatch(
            AddOperationPopUpAction.FillOperationName(
                operationName
            )
        )
    }

    private fun updateCategoriesList(updatedCategoriesList: List<CategoryModel>) {
        addOperationPopUpStore.dispatch(
            AddOperationPopUpAction.UpdateCategoriesList(updatedCategoriesList)
        )
    }

    private fun updateAccountsList(updatedAccountList: List<AccountModel>) {
        addOperationPopUpStore.dispatch(
            AddOperationPopUpAction.UpdateAccountList(updatedAccountList)
        )
    }


    private val subscriber: StoreSubscriber<AddOperationPopUpGlobalState> =
        { addOperationPopUpGlobalState ->
            isPoUpExpanded_.value =
                addOperationPopUpGlobalState.addOperationPopUpOperationOptionState.isPopUpExpanded
            isPaymentExpanded_.value =
                addOperationPopUpGlobalState.addOperationPopUpPaymentOptionState.isPaymentExpanded
            isRecurrentOptionExpanded_.value =
                addOperationPopUpGlobalState.addOperationPopUpPaymentOptionState.isRecurrentOptionExpanded
            isTransferExpanded_.value =
                addOperationPopUpGlobalState.addOperationPopUpTransferOptionState.isTransferExpanded
            allCategories_.value =
                addOperationPopUpGlobalState.addOperationPopUpOperationOptionState.operationCategories
            allAccounts_.value =
                addOperationPopUpGlobalState.addOperationPopUpTransferOptionState.accountList
            operationCategories_.value =
                addOperationPopUpGlobalState.addOperationPopUpOperationOptionState.operationCategories.map {
                    DataUiModel(
                        it.name,
                        it.id
                    )
                }
            operationAccounts_.value =
                addOperationPopUpGlobalState.addOperationPopUpTransferOptionState.accountList.map {
                    DataUiModel(it.name, it.id)
                }
            selectedCategory_.value =
                addOperationPopUpGlobalState.addOperationPopUpOperationOptionState.selectedCategory
            selectedCategoryName_.value = DataUiModel(
                addOperationPopUpGlobalState.addOperationPopUpOperationOptionState.selectedCategory.name,
                addOperationPopUpGlobalState.addOperationPopUpOperationOptionState.selectedCategory.id
            )
            operationName_.value =
                addOperationPopUpGlobalState.addOperationPopUpOperationOptionState.operationName
            operationAmount_.value =
                addOperationPopUpGlobalState.addOperationPopUpOperationOptionState.operationAmount
        }

    private fun addSubscriber() = addOperationPopUpStore.add(subscriber)
    private fun removeSubscriber() = addOperationPopUpStore.remove(subscriber)
}

internal sealed class AddOperationPopUpAction : UiAction {
    object Init : AddOperationPopUpAction()
    object ExpandPaymentOption : AddOperationPopUpAction()
    object CollapseOptions : AddOperationPopUpAction()
    object ExpandTransferOption : AddOperationPopUpAction()
    object ExpandRecurrentOption : AddOperationPopUpAction()
    object CloseRecurrentOption : AddOperationPopUpAction()
    object ClosePopUp : AddOperationPopUpAction()
    data class SelectCategory(val category: CategoryModel) : AddOperationPopUpAction()
    data class FillOperationName(val operationName: String) : AddOperationPopUpAction()
    data class UpdateCategoriesList(val allCategories: List<CategoryModel>) :
        AddOperationPopUpAction()

    data class UpdateAccountList(val accountList: List<AccountModel>) : AddOperationPopUpAction()
}