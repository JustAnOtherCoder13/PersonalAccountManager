package com.piconemarc.viewmodel.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.viewmodel.StoreSubscriber
import com.piconemarc.viewmodel.UiState

private val baseAppScreenVmState_: MutableState<ViewModelInnerStates.BaseAppScreenVmState> =
    mutableStateOf(
        ViewModelInnerStates.BaseAppScreenVmState()
    )
private val addOperationPopUpVMState_: MutableState<ViewModelInnerStates.AddOperationPopUpVMState> =
    mutableStateOf(
        ViewModelInnerStates.AddOperationPopUpVMState()
    )

class AppSubscriber {

    val appStoreSubscriber: StoreSubscriber<ViewModelInnerStates.GlobalVmState> =
        { globalVmState ->
            baseAppScreenVmState_.value = globalVmState.baseAppScreenVmState
            addOperationPopUpVMState_.value = globalVmState.addOperationPopUpVMState
        }

    object GlobalUiState : UiState {
        val baseAppScreenUiState by baseAppScreenVmState_
        val addOperationPopUpUiState by addOperationPopUpVMState_
    }

}