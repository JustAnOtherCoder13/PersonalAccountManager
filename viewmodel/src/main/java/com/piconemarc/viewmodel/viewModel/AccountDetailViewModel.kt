package com.piconemarc.viewmodel.viewModel

import androidx.compose.runtime.getValue
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountDetailViewModel @Inject constructor(
    private val addOperationPopUpActionDispatcher: AddOperationPopUpActionDispatcher
) : BaseScreenViewModel() {

    override fun dispatchAction(action: UiAction){
        when(action){
            is AddOperationPopUpAction -> addOperationPopUpActionDispatcher.dispatchAction(action)
        }
    }

}

//AddOperationPopUp States -----------------------------------------

object AddOperationPopUpUiState {
    val isPopUpExpanded by isPoUpExpanded_
    val operationCategories by allCategories
    val selectedCategoryName by selectedCategoryName_
    val operationName by operationName_
    val operationAmount by operationAmount_
    val isPaymentExpanded by isPaymentExpanded_
    val isRecurrentOptionExpanded by isRecurrentOptionExpanded_
    val enDateSelectedMonth by selectedEndDateMonth_
    val endDateSelectedYear by selectedEndDateYear_
    val isTransferExpanded by isTransferExpanded_
    val accountList by allAccounts
    val senderAccount by senderAccount_
    val beneficiaryAccount by beneficiaryAccount_
    val addPopUpTitle by addPopUpTitle_
    val selectableYearsList by selectableYearsList_
    val selectableMonthsList by selectableMonthsList_
}


