package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.core.domain.AddOperationPopUpGlobalState
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.model.entity.OperationModel
import com.piconemarc.viewmodel.viewModel.StoreSubscriber

private val isPoUpExpanded_: MutableState<Boolean> = mutableStateOf(false)
private val isPaymentExpanded_: MutableState<Boolean> = mutableStateOf(false)
private val isRecurrentOptionExpanded_: MutableState<Boolean> = mutableStateOf(false)
private val isTransferExpanded_: MutableState<Boolean> = mutableStateOf(false)
private val operationCategories_: MutableState<List<String>> = mutableStateOf(listOf())
private val selectedCategoryName_: MutableState<String> = mutableStateOf("Category")
private val operationName_: MutableState<String> = mutableStateOf("")
private val operationAmount_: MutableState<String> = mutableStateOf("")

//internal value
private val allCategories_: MutableState<List<CategoryModel>> = mutableStateOf(listOf())
private val selectedCategory_: MutableState<CategoryModel> = mutableStateOf(CategoryModel())
private val operation_: MutableState<OperationModel> = mutableStateOf(OperationModel())




object AddOperationScreenViewState {
    val isPopUpExpanded by isPoUpExpanded_
    val operationCategories by operationCategories_
    val selectedCategoryName by selectedCategoryName_
    val operationName by operationName_
    val operationAmount by operationAmount_
    val isPaymentExpanded by isPaymentExpanded_
    val isRecurrentOptionExpanded by isRecurrentOptionExpanded_
    val enDateSelectedMonth: String = ""
    val endDateSelectedYear: String = ""
    val isTransferExpanded by isTransferExpanded_
    val accountList: List<AccountModel> = listOf()
    val senderAccount: String = ""
    val beneficiaryAccount: String = ""
}

object AddOperationScreenEvent {
    fun closePopUp() {
        DI.addOperationPopUpStore.dispatch(AddOperationPopUpAction.ClosePopUp)
        removeSubscriber()
    }

    fun initPopUp() {
        addSubscriber()
        DI.addOperationPopUpStore.dispatch(AddOperationPopUpAction.Init)
    }

    fun collapseOptions() {
        DI.addOperationPopUpStore.dispatch(AddOperationPopUpAction.CollapseOptions)
    }

    fun expandPaymentOption() {
        DI.addOperationPopUpStore.dispatch(AddOperationPopUpAction.ExpandPaymentOption)
    }

    fun expandTransferOption() {
        DI.addOperationPopUpStore.dispatch(AddOperationPopUpAction.ExpandTransferOption)
    }

    fun expandRecurrentOption() {
        DI.addOperationPopUpStore.dispatch(AddOperationPopUpAction.ExpandRecurrentOption)
    }

    fun collapseRecurrentOption() {
        DI.addOperationPopUpStore.dispatch(AddOperationPopUpAction.CloseRecurrentOption)
    }

    fun selectCategory(selectedCategory: String) {
        DI.addOperationPopUpStore.dispatch(
            AddOperationPopUpAction.SelectCategory(
                getCategoryForName(selectedCategory)
            )
        )
    }

    fun fillOperationName(operationName : String){
        DI.addOperationPopUpStore.dispatch(
            AddOperationPopUpAction.FillOperationName(
                operationName
            )
        )
    }
}

private val subscriber: StoreSubscriber<AddOperationPopUpGlobalState> = {
    isPoUpExpanded_.value = it.addOperationPopUpOperationOptionState.isPopUpExpanded
    isPaymentExpanded_.value = it.addOperationPopUpPaymentOptionState.isPaymentExpanded
    isRecurrentOptionExpanded_.value =
        it.addOperationPopUpPaymentOptionState.isRecurrentOptionExpanded
    isTransferExpanded_.value = it.addOperationPopUpTransferOptionState.isTransferExpanded
    operationCategories_.value = mapCategoriesToString(it)
    selectedCategory_.value = it.addOperationPopUpOperationOptionState.selectedCategory
    selectedCategoryName_.value = it.addOperationPopUpOperationOptionState.selectedCategory.name
    operationName_.value = it.addOperationPopUpOperationOptionState.operationName
    operationAmount_.value = it.addOperationPopUpOperationOptionState.operationAmount
}

private fun addSubscriber() = DI.addOperationPopUpStore.add(subscriber)
private fun removeSubscriber() = DI.addOperationPopUpStore.remove(subscriber)


//--------------------------------------------HELPERS to refactor in interactor---------------------
private fun mapCategoriesToString(it: AddOperationPopUpGlobalState): MutableList<String> {
    allCategories_.value = it.addOperationPopUpOperationOptionState.operationCategories
    val categoriesToString: MutableList<String> = mutableListOf()
    it.addOperationPopUpOperationOptionState.operationCategories.forEachIndexed() { _, operationCategory ->
        categoriesToString.add(operationCategory.name)
    }
    return categoriesToString
}

private fun getCategoryForName(selectedCategory: String) =
    allCategories_.value.find { selectedCategory == it.name } ?: selectedCategory_.value
