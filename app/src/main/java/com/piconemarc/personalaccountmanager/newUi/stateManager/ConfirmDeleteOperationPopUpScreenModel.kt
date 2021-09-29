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

class ConfirmDeleteOperationPopUpScreenModel : BaseScreenModel() {
    override val currentState: PAMUiState = getState()
    override val getTargetState: (PAMUiState) -> Unit={ deletePopUpScreenState.value = it as DeletePopUpState}

    fun expand(operation: Operation) {
        onUiEvent(
            runBefore = { operation_.value = operation },
            event = DeletePopUpEvent.OnExpand)
    }

    fun collapse() {
        onUiEvent(DeletePopUpEvent.OnCollapse)
    }

    fun deleteOperation() {
        onUiEvent(
            runBefore = {
                /*todo delete operation here*/
                Toast.makeText(
                    MainActivity.applicationContext(),
                    "Operation delete ${operation.operationName}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            event = DeletePopUpEvent.OnDelete
        )
    }

    override fun getState(): DeletePopUpState = deletePopUpScreenState.value
}
//-------------------------------STATES-------------------------------------------

sealed class DeletePopUpState : PAMUiState {
    open val operation: Operation = Operation()
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