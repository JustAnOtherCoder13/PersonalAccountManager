package com.piconemarc.personalaccountmanager.newUi.stateManager

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

private val addOperationPopUpState : MutableState<AddOperationPopUpState> = mutableStateOf(AddOperationPopUpState.Idle)


sealed class AddOperationPopUpState : PAMUiState{
    open val isExpanded = false

    object Idle :AddOperationPopUpState()

    object Operation : AddOperationPopUpState()

    object Payment : AddOperationPopUpState()

    object Transfer : AddOperationPopUpState()
}

sealed class AddOperationPopUpEvent(
    override val source: PAMUiState,
    override val target: PAMUiState
) : PAMUiEvent{
    object Operation :AddOperationPopUpEvent(
        source = AddOperationPopUpState.Idle,
        target = AddOperationPopUpState.Operation
    )
    object OnPaymentOptionRequire : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Operation,
        target = AddOperationPopUpState.Payment
    )
    object OnTransferOptionRequire : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Payment,
        target = AddOperationPopUpState.Transfer
    )
}

class AddOperationPopUpScreenModel(){

    fun operationToPaymentOption(){
        onEvent(AddOperationPopUpEvent.OnPaymentOptionRequire)
    }
    fun paymentOptionToOperation(){
        onEvent(AddOperationPopUpEvent.Operation)
    }
    fun transferOptionToPaymentOption(){
        onEvent(AddOperationPopUpEvent.OnPaymentOptionRequire)
    }
    fun paymentToTransferOption(){
        onEvent(AddOperationPopUpEvent.OnTransferOptionRequire)
    }

    private fun onEvent(
        event: AddOperationPopUpEvent,
        runBefore: () -> Unit = {},
        runAfter: () -> Unit = {}
    ){
        onUiEvent(
            currentState = state(),
            getMutableState = { addOperationPopUpState.value = it as AddOperationPopUpState },
            event_ = event,
            runBefore = runBefore,
            runAfter = runAfter
        )
    }

    fun state(): AddOperationPopUpState = addOperationPopUpState.value
}

