package com.piconemarc.viewmodel.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.model.entity.OperationModel


private val deletePopUpScreenState: MutableState<DeletePopUpState> =
    mutableStateOf(DeletePopUpState.Idle)
private val operationModel_: MutableState<OperationModel> =
    mutableStateOf(OperationModel())

class ConfirmDeleteOperationPopUpScreenModel : BaseScreenModel() {
    override val currentState: PAMUiState = getState()
    override val getTargetState: (PAMUiState) -> Unit={ deletePopUpScreenState.value = it as DeletePopUpState }

    fun expand(operationModel: OperationModel) {
        onUiEvent(
            runBefore = { operationModel_.value = operationModel },
            event = DeletePopUpEvent.OnExpand
        )
    }

    fun collapse() {
        onUiEvent(DeletePopUpEvent.OnCollapse)
    }

    fun deleteOperation() {
        onUiEvent(
            runBefore = {
                /*todo delete operation here*/
                /*Toast.makeText(
                    MainActivity.applicationContext(),
                    "Operation delete ${operationModel.operationName}",
                    Toast.LENGTH_SHORT
                ).show()*/
            },
            event = DeletePopUpEvent.OnDelete
        )
    }

    override fun getState(): DeletePopUpState = deletePopUpScreenState.value
}
//-------------------------------STATES-------------------------------------------

sealed class DeletePopUpState : PAMUiState {
    open val operation :OperationModel = OperationModel()
    open val isExpanded: Boolean = false

    object Idle : DeletePopUpState()

    object Expand : DeletePopUpState() {
        override val isExpanded = true
        override val operation by operationModel_
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