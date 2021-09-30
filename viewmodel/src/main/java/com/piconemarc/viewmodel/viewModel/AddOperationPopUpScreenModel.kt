package com.piconemarc.viewmodel.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.model.entity.OperationModel

private val addOperationPopUpState: MutableState<AddOperationPopUpState> =
    mutableStateOf(AddOperationPopUpState.Idle)

sealed class AddOperationPopUpState : PAMUiState {
    open val isExpanded = false
    open val popUpTitle = "MainActivity.applicationContext().getString(R.string.category)"
    open val operation = OperationModel()
    val category = CategoryModel()

    object Idle : AddOperationPopUpState(){
    }

    object Operation : AddOperationPopUpState(){
        override val isExpanded: Boolean = true
    }

    object Payment : AddOperationPopUpState(){
        override val isExpanded: Boolean = true
        override val popUpTitle: String = "MainActivity.applicationContext().getString(R.string.payment)"
    }

    object Transfer : AddOperationPopUpState(){
        override val isExpanded: Boolean = true
        override val popUpTitle: String = "MainActivity.applicationContext().getString(R.string.transfer)"
    }

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
    override val getTargetState: (PAMUiState) -> Unit = { addOperationPopUpState.value = it as AddOperationPopUpState }

    fun expand (){
        onUiEvent(
            AddOperationPopUpEvent.OnExpand,
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

    fun addOperation(){
        onUiEvent(
            event = AddOperationPopUpEvent.OnClose,
            runBefore = {
                //todo add operation here
                //Toast.makeText(MainActivity.applicationContext(),"Add operation : ${operation.category}",Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun selectCategory(category: CategoryModel){
        //getState().operation.copy(category = category)
    }

    override fun getState(): AddOperationPopUpState = addOperationPopUpState.value
}
