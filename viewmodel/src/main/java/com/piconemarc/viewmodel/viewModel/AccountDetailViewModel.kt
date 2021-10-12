package com.piconemarc.viewmodel.viewModel

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountDetailViewModel @Inject constructor(
    private val addOperationPopUpActionDispatcher: AddOperationPopUpActionDispatcher
) : ViewModel() {

    fun dispatchAction(action: PAMUiAction){
        when(action){
            is AddOperationPopUpAction -> addOperationPopUpActionDispatcher.dispatchAction(action)
        }
    }
}

//AddOperationPopUp States -----------------------------------------

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
    val senderAccount: PresentationDataModel = PresentationDataModel("",0)
    val beneficiaryAccount: PresentationDataModel = PresentationDataModel("",0)
}


