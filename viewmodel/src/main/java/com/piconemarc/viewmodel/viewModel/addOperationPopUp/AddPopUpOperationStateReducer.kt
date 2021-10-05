package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import com.piconemarc.core.domain.AddOperationPopUpGlobalState
import com.piconemarc.core.domain.AddOperationPopUpOperationOptionState
import com.piconemarc.core.domain.AddOperationPopUpPaymentOptionState
import com.piconemarc.core.domain.AddOperationPopUpTransferOptionState
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.model.entity.OperationModel
import com.piconemarc.viewmodel.viewModel.DefaultStore
import com.piconemarc.viewmodel.viewModel.Reducer
import com.piconemarc.viewmodel.viewModel.UiAction

//---------------------------------------------------------ACTIONS----------------------------------

internal sealed class AddOperationPopUpAction : UiAction {
    object Init : AddOperationPopUpAction()
    object ExpandPaymentOption : AddOperationPopUpAction()
    object CollapseOptions : AddOperationPopUpAction()
    object ExpandTransferOption : AddOperationPopUpAction()
    object ExpandRecurrentOption : AddOperationPopUpAction()
    object CloseRecurrentOption : AddOperationPopUpAction()
    object ClosePopUp : AddOperationPopUpAction()
    data class SelectCategory(val category : CategoryModel) : AddOperationPopUpAction()
    data class FillOperation(val operation : OperationModel) : AddOperationPopUpAction()
}

//-------------------------------------------------------------------REDUCERS-----------------------

private val OperationStateReducer: Reducer<AddOperationPopUpOperationOptionState> = { old, action ->
    when (action) {
        is AddOperationPopUpAction.Init -> old.copy(
            isPopUpExpanded = true,
            operationCategories = listOf(CategoryModel("category 1"), CategoryModel("category 2")),
            selectedCategory = CategoryModel("Category")
        )
        is AddOperationPopUpAction.ClosePopUp -> old.copy(isPopUpExpanded = false)
        is AddOperationPopUpAction.SelectCategory -> old.copy(selectedCategory = action.category )
        is AddOperationPopUpAction.FillOperation -> old.copy(operationName = action.operation.operationName, operationAmount = action.operation.operationAmount.toString())
        else -> old
    }
}

private val PaymentStateReducer: Reducer<AddOperationPopUpPaymentOptionState> = { old, action ->
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

private val TransferStateReducer: Reducer<AddOperationPopUpTransferOptionState> = { old, action ->
    when (action) {
        is AddOperationPopUpAction.ExpandTransferOption -> old.copy(isTransferExpanded = true)
        is AddOperationPopUpAction.CollapseOptions -> old.copy(isTransferExpanded = false)
        is AddOperationPopUpAction.ClosePopUp -> old.copy(isTransferExpanded = false)
        is AddOperationPopUpAction.ExpandPaymentOption -> old.copy(isTransferExpanded = false)
        else -> old
    }
}

private val AddOperationStateReducer: Reducer<AddOperationPopUpGlobalState> = { old, action ->
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

 internal object DI {
    val addOperationPopUpStore =
        DefaultStore(
            initialState = AddOperationPopUpGlobalState(),
            reducer = AddOperationStateReducer
        )
}