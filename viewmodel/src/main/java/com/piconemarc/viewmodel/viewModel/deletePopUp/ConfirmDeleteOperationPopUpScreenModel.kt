
package com.piconemarc.viewmodel.viewModel.deletePopUp
/*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.core.domain.PAMUiState
import com.piconemarc.model.entity.OperationModel
import com.piconemarc.viewmodel.viewModel.BaseScreenModel
import com.piconemarc.viewmodel.viewModel.PAMUiEvent


private val deletePopUpScreenState: MutableState<DeleteOperationPopUpState> =
    mutableStateOf(DeleteOperationPopUpState.Idle)
private val operationModel_: MutableState<OperationModel> =
    mutableStateOf(OperationModel())

class ConfirmDeleteOperationPopUpScreenModel : BaseScreenModel() {

    fun expand(operationModel: OperationModel) {
        onUiEvent(
            runBefore = { operationModel_.value = operationModel },
            event = DeletePopUpEvent.OnExpand
        )
    }

    fun collapse() { onUiEvent(DeletePopUpEvent.OnCollapse) }

    fun deleteOperation() { onUiEvent(DeletePopUpEvent.OnDelete) }

    override fun getState(): DeleteOperationPopUpState = deletePopUpScreenState.value

    override fun onUiEvent(
        event: PAMUiEvent,
        runBefore: () -> Unit,
        runAfter: () -> Unit
    ) {
        //popUpState
        if (getState() == event.source) {
            runBefore()
            deletePopUpScreenState.value =
                event.target as DeleteOperationPopUpState
            runAfter()
        }
    }
}
//-------------------------------STATES-------------------------------------------

sealed class DeleteOperationPopUpState : PAMUiState {
    open val isExpanded: Boolean = false
    open val operation by operationModel_

    object Idle : DeleteOperationPopUpState(){
        override val operation: OperationModel = OperationModel()
    }

    object Expand : DeleteOperationPopUpState() {
        override val isExpanded = true
    }
}

//----------------------------------EVENTS----------------------------------------

sealed class DeletePopUpEvent(
    override val source: DeleteOperationPopUpState,
    override val target: DeleteOperationPopUpState
) : PAMUiEvent {
    object OnExpand : DeletePopUpEvent(
        DeleteOperationPopUpState.Idle,
        DeleteOperationPopUpState.Expand
    )
    object OnCollapse : DeletePopUpEvent(
        DeleteOperationPopUpState.Expand,
        DeleteOperationPopUpState.Idle
    )
    object OnDelete : DeletePopUpEvent(
        DeleteOperationPopUpState.Expand,
        DeleteOperationPopUpState.Idle
    )
}*/
