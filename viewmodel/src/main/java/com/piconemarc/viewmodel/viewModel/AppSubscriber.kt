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

private val deleteAccountVmState_ : MutableState<ViewModelInnerStates.DeleteAccountPopUpVMState> =
    mutableStateOf(
        ViewModelInnerStates.DeleteAccountPopUpVMState()
    )

private val addAccountPoUpVmState_ : MutableState<ViewModelInnerStates.AddAccountPopUpVMState> =
    mutableStateOf(
        ViewModelInnerStates.AddAccountPopUpVMState()
    )

class AppSubscriber {

    val appStoreSubscriber: StoreSubscriber<ViewModelInnerStates.GlobalVmState> =
        { globalVmState ->
            baseAppScreenVmState_.value = globalVmState.baseAppScreenVmState
            addOperationPopUpVMState_.value = globalVmState.addOperationPopUpVMState
            deleteAccountVmState_.value = globalVmState.deleteAccountPopUpVMState
            addAccountPoUpVmState_.value = globalVmState.addAccountPopUpVMState
        }

    object GlobalUiState : UiState {
        val baseAppScreenUiState by baseAppScreenVmState_
        val addOperationPopUpUiState by addOperationPopUpVMState_
        val deleteAccountVMState by deleteAccountVmState_
        val addAccountPopUpVMState by addAccountPoUpVmState_
    }
}