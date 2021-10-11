package com.piconemarc.viewmodel.viewModel

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.DataUiModel
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//todo find a way to simplify access beetween vm an reducer, adding a new value is a mess
@HiltViewModel
class AccountDetailViewModel @Inject constructor(
    private val addOperationScreenEvent: AddOperationScreenEvent
) : ViewModel() {

    fun dispatchEvent(event: PAMUiEvent){
        when(event){
            is AddOperationPopUpEvent -> when (event){
                is AddOperationPopUpEvent.InitPopUp -> addOperationScreenEvent.initPopUp()
                is AddOperationPopUpEvent.CLosePopUp ->addOperationScreenEvent.closePopUp()
                is AddOperationPopUpEvent.CollapseOptions -> addOperationScreenEvent.collapseOptions()
                is AddOperationPopUpEvent.ExpandPaymentOptions -> addOperationScreenEvent.expandPaymentOption()
                is AddOperationPopUpEvent.ExpandTransferOptions -> addOperationScreenEvent.expandTransferOption()
                is AddOperationPopUpEvent.ExpandRecurrentOptions -> addOperationScreenEvent.expandRecurrentOption()
                is AddOperationPopUpEvent.CollapseRecurrentOptions -> addOperationScreenEvent.collapseRecurrentOption()
                is AddOperationPopUpEvent.SelectCategory -> addOperationScreenEvent.selectCategory(event.selectedCategory.objectIdReference)
                is AddOperationPopUpEvent.FillOperationName -> addOperationScreenEvent.fillOperationName(event.operationName)
            }
        }
    }
}

//AddOperationPopUp Event and States -----------------------------------------

sealed class AddOperationPopUpEvent : PAMUiEvent{
    object InitPopUp : AddOperationPopUpEvent()
    object CLosePopUp : AddOperationPopUpEvent()
    object CollapseOptions : AddOperationPopUpEvent()
    object ExpandPaymentOptions : AddOperationPopUpEvent()
    object ExpandTransferOptions : AddOperationPopUpEvent()
    object ExpandRecurrentOptions : AddOperationPopUpEvent()
    object CollapseRecurrentOptions : AddOperationPopUpEvent()
    data class SelectCategory(val selectedCategory: DataUiModel) : AddOperationPopUpEvent()
    data class FillOperationName(val operationName: String) : AddOperationPopUpEvent()

}

object AddOperationPopUpState {
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
    val accountList by operationAccounts_
    val senderAccount: DataUiModel = DataUiModel("",0)
    val beneficiaryAccount: DataUiModel = DataUiModel("",0)
}

//
