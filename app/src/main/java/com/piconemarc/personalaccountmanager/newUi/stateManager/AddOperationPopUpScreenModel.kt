package com.piconemarc.personalaccountmanager.newUi.stateManager

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

private val addOperationPopUpState: MutableState<AddOperationPopUpState> =
    mutableStateOf(AddOperationPopUpState.Idle)


sealed class AddOperationPopUpState : PAMUiState {
    open val isExpanded = false

    object Idle : AddOperationPopUpState()

    object Operation : AddOperationPopUpState()

    object Payment : AddOperationPopUpState()

    object Transfer : AddOperationPopUpState()
}

sealed class AddOperationPopUpEvent(
    override val source: PAMUiState,
    override val target: PAMUiState
) : PAMUiEvent {

    object OnExpand : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Idle,
        target = AddOperationPopUpState.Operation
    )

    object OnClose : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Operation,
        target = AddOperationPopUpState.Idle
    )

    object OnOperationOptionRequire : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Payment,
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

class AddOperationPopUpScreenModel() : BaseScreenModel() {
    override val currentState: PAMUiState = getState()
    override val getTargetState: (PAMUiState) -> Unit = { addOperationPopUpState.value = it as AddOperationPopUpState}

    fun expand (){
        onUiEvent(AddOperationPopUpEvent.OnExpand,
            )
    }
    fun openPaymentOption() {
        onUiEvent(AddOperationPopUpEvent.OnPaymentOptionRequire)
    }

    fun closeOption() {
        onUiEvent(
            event = AddOperationPopUpEvent.OnOperationOptionRequire,
            runBefore = {
                if (addOperationPopUpState.value == AddOperationPopUpState.Transfer){
                    onUiEvent(AddOperationPopUpEvent.OnPaymentOptionRequire)
                }
            }
            )
    }

    fun openTransferOption() {
        onUiEvent(
            event = AddOperationPopUpEvent.OnTransferOptionRequire,
        runBefore={
            if (addOperationPopUpState.value == AddOperationPopUpState.Operation){
                onUiEvent(AddOperationPopUpEvent.OnPaymentOptionRequire)
            }
        }
        )
    }

    fun close(){
        onUiEvent(AddOperationPopUpEvent.OnClose)
    }

    override fun getState(): AddOperationPopUpState = addOperationPopUpState.value
}
