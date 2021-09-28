package com.piconemarc.personalaccountmanager.newUi.stateManager

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.personalaccountmanager.MainActivity
import com.piconemarc.personalaccountmanager.entity.Operation
import com.piconemarc.personalaccountmanager.newUi.stateManager.DeletePopUpState.Expand.operation

private val deletePopUpScreenState: MutableState<DeletePopUpState> =
    mutableStateOf(DeletePopUpState.Idle)
private val operation_: MutableState<Operation> =
    mutableStateOf(Operation())

class ConfirmDeleteOperationPopUpScreenModel {

    fun expand(operation: Operation) {
        operation_.value = operation
        onEvent(DeletePopUpEvent.OnExpand)
    }

    fun collapse() {
        onEvent(DeletePopUpEvent.OnCollapse)
    }

    fun deleteOperation() {
        onEvent(
            runBefore = {
                        /*todo delete operation here*/
                    Toast.makeText(MainActivity.applicationContext(),"Operation delete ${operation.operationName}",Toast.LENGTH_SHORT).show()
                        },
            event = DeletePopUpEvent.OnDelete
        )
    }

    fun state(): DeletePopUpState = deletePopUpScreenState.value

    private fun onEvent(
        event: DeletePopUpEvent,
        runBefore : ()-> Unit = {},
        runAfter : ()-> Unit = {}
        ) {
        onUiEvent(
            currentState = state(),
            getMutableState = { deletePopUpScreenState.value = it as DeletePopUpState },
            event_ = event,
            runBefore =runBefore,
            runAfter = runAfter
        )
    }
}
//-------------------------------STATES-------------------------------------------

sealed class DeletePopUpState : PAMUiState {
    open val operation : Operation = Operation()
    open val isExpanded: Boolean = false

    object Idle : DeletePopUpState()

    object Expand : DeletePopUpState() {
        override val operation: Operation by operation_
        override val isExpanded: Boolean get() = true
    }
}

//----------------------------------EVENTS----------------------------------------

sealed class DeletePopUpEvent(
    override val source: DeletePopUpState,
    override val target: DeletePopUpState
) : PAMUiEvent {
    object OnExpand : DeletePopUpEvent(DeletePopUpState.Idle, DeletePopUpState.Expand)
    object OnCollapse : DeletePopUpEvent(DeletePopUpState.Expand, DeletePopUpState.Idle)
    object OnDelete : DeletePopUpEvent(DeletePopUpState.Expand, DeletePopUpState.Idle)
}
