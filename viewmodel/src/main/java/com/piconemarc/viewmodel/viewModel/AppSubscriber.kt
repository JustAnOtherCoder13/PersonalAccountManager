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

private val myAccountScreenVMState_ : MutableState<ViewModelInnerStates.MyAccountScreenVMState> =
    mutableStateOf(
        ViewModelInnerStates.MyAccountScreenVMState()
    )

private val myAccountDetailScreenVMState_ : MutableState<ViewModelInnerStates.MyAccountDetailScreenVMState> =
    mutableStateOf(
        ViewModelInnerStates.MyAccountDetailScreenVMState()
    )
private val deleteOperationPopUpVMState_ : MutableState<ViewModelInnerStates.DeleteOperationPopUpVMState>  =
    mutableStateOf(
        ViewModelInnerStates.DeleteOperationPopUpVMState()
    )

class AppSubscriber {

    val appStoreSubscriber: StoreSubscriber<ViewModelInnerStates.GlobalVmState> =
        { globalVmState ->
            baseAppScreenVmState_.value = globalVmState.baseAppScreenVmState
            addOperationPopUpVMState_.value = globalVmState.addOperationPopUpVMState
            deleteAccountVmState_.value = globalVmState.deleteAccountPopUpVMState
            addAccountPoUpVmState_.value = globalVmState.addAccountPopUpVMState
            myAccountDetailScreenVMState_.value = globalVmState.myAccountDetailScreenVMState
            myAccountScreenVMState_.value = globalVmState.myAccountScreenVmState
            deleteOperationPopUpVMState_.value = globalVmState.deleteOperationPopUpVmState
        }

    object GlobalUiState : UiState {
        val baseAppScreenUiState by baseAppScreenVmState_
        val addOperationPopUpUiState by addOperationPopUpVMState_
        val deleteAccountUiState by deleteAccountVmState_
        val addAccountPopUpUiState by addAccountPoUpVmState_
        val myAccountScreenUiState by myAccountScreenVMState_
        val myAccountDetailScreenUiState by myAccountDetailScreenVMState_
        val deleteOperationPopUpUiState by deleteOperationPopUpVMState_
    }
}