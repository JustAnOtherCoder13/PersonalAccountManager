package com.piconemarc.viewmodel.viewModel.test

import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.viewmodel.viewModel.DefaultStore
import com.piconemarc.viewmodel.viewModel.PAMUiState
import com.piconemarc.viewmodel.viewModel.Reducer
import com.piconemarc.viewmodel.viewModel.UiAction


//-------------------------------------------------------STATES-------------------------------------
data class AddOperationPopUpOperationOptionState(
    val isPopUpExpanded: Boolean = false,
    val operationCategories: List<CategoryModel> = listOf(),
    val selectedCategory: String = "",
    val operationName: String = "",
    val operationAmount: String = "",
) : PAMUiState

data class AddOperationPopUpPaymentOptionState(
    val isPaymentExpanded: Boolean = false,
    val isRecurrentOptionExpanded: Boolean = false,
    val enDateSelectedMonth: String = "",
    val endDateSelectedYear: String = ""
) : PAMUiState

data class AddOperationPopUpTransferOptionState(
    val isTransferExpanded: Boolean = false,
    val accountList: List<AccountModel> = listOf(),
    val senderAccount: String = "",
    val beneficiaryAccount: String = ""
) : PAMUiState

data class AddOperationPopUpGlobalState(
    val addOperationPopUpOperationOptionState: AddOperationPopUpOperationOptionState = AddOperationPopUpOperationOptionState(),
    val addOperationPopUpPaymentOptionState: AddOperationPopUpPaymentOptionState = AddOperationPopUpPaymentOptionState(),
    val addOperationPopUpTransferOptionState: AddOperationPopUpTransferOptionState = AddOperationPopUpTransferOptionState()
) : PAMUiState

//---------------------------------------------------------ACTIONS----------------------------------

sealed class AddOperationPopUpAction : UiAction {
    object Init : AddOperationPopUpAction()
    object ExpandPaymentOption : AddOperationPopUpAction()
    object CollapseOptions : AddOperationPopUpAction()
    object ExpandTransferOption : AddOperationPopUpAction()
    object ExpandRecurrentOption : AddOperationPopUpAction()
    object CloseRecurrentOption : AddOperationPopUpAction()
    object ClosePopUp : AddOperationPopUpAction()
}

//-------------------------------------------------------------------REDUCERS-----------------------

val OperationStateReducer: Reducer<AddOperationPopUpOperationOptionState> = { old, action ->
    when (action) {
        is AddOperationPopUpAction.Init -> old.copy(isPopUpExpanded = true)
        is AddOperationPopUpAction.ClosePopUp -> old.copy(isPopUpExpanded = false)
        else -> old
    }
}

val PaymentStateReducer: Reducer<AddOperationPopUpPaymentOptionState> = { old, action ->
    when (action) {
        is AddOperationPopUpAction.ExpandPaymentOption -> old.copy(isPaymentExpanded = true)
        is AddOperationPopUpAction.CollapseOptions -> old.copy(
            isPaymentExpanded = false,
            isRecurrentOptionExpanded = false
        )
        is AddOperationPopUpAction.ExpandRecurrentOption -> old.copy(isRecurrentOptionExpanded = true)
        is AddOperationPopUpAction.CloseRecurrentOption -> old.copy(isRecurrentOptionExpanded = false)
        is AddOperationPopUpAction.ExpandTransferOption -> old.copy(isPaymentExpanded = true)
        is AddOperationPopUpAction.ClosePopUp -> old.copy(
            isPaymentExpanded = false,
            isRecurrentOptionExpanded = false
        )
        else -> old
    }
}

val TransferStateReducer: Reducer<AddOperationPopUpTransferOptionState> = { old, action ->
    when (action) {
        is AddOperationPopUpAction.ExpandTransferOption -> old.copy(isTransferExpanded = true)
        is AddOperationPopUpAction.CollapseOptions -> old.copy(isTransferExpanded = false)
        is AddOperationPopUpAction.ClosePopUp -> old.copy(isTransferExpanded = false)
        is AddOperationPopUpAction.ExpandPaymentOption -> old.copy(isTransferExpanded = false)
        else -> old
    }
}

val AddOperationStateReducer: Reducer<AddOperationPopUpGlobalState> = { old, action ->
    AddOperationPopUpGlobalState(
        addOperationPopUpOperationOptionState = OperationStateReducer(
            old.addOperationPopUpOperationOptionState,
            action
        ),
        addOperationPopUpPaymentOptionState = PaymentStateReducer(
            old.addOperationPopUpPaymentOptionState,
            action
        ),
        addOperationPopUpTransferOptionState = TransferStateReducer(
            old.addOperationPopUpTransferOptionState,
            action
        )
    )
}

//di to extract later

object DI {
    val store =
        DefaultStore(
            initialState = AddOperationPopUpGlobalState(),
            reducer = AddOperationStateReducer
        )
}