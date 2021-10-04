package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import androidx.compose.runtime.*
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.CategoryModel

private val isPoUpExpanded_: MutableState<Boolean> = mutableStateOf(false)
private val isPaymentExpanded_: MutableState<Boolean> = mutableStateOf(false)
private val isRecurrentOptionExpanded_: MutableState<Boolean> = mutableStateOf(false)
private val isTransferExpanded_: MutableState<Boolean> = mutableStateOf(false)

object AddOperationScreenViewState {
    val isPopUpExpanded by isPoUpExpanded_
    val operationCategories: List<CategoryModel> = listOf()
    val selectedCategory: String = ""
    val operationName: String = ""
    val operationAmount: String = ""
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
    }

    fun initPopUp() {
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

    fun expandRecurrentOption(){
        DI.addOperationPopUpStore.dispatch(AddOperationPopUpAction.ExpandRecurrentOption)
    }

    fun collapseRecurrentOption (){
        DI.addOperationPopUpStore.dispatch(AddOperationPopUpAction.CloseRecurrentOption)
    }
}

fun observeAddPopUpValues() = DI.addOperationPopUpStore.add {
    isPoUpExpanded_.value = it.addOperationPopUpOperationOptionState.isPopUpExpanded
    isPaymentExpanded_.value = it.addOperationPopUpPaymentOptionState.isPaymentExpanded
    isRecurrentOptionExpanded_.value = it.addOperationPopUpPaymentOptionState.isRecurrentOptionExpanded
    isTransferExpanded_.value = it.addOperationPopUpTransferOptionState.isTransferExpanded
}

fun stopObservingAddPopUpValues() = DI.addOperationPopUpStore.remove {
    it.addOperationPopUpOperationOptionState.isPopUpExpanded
}




