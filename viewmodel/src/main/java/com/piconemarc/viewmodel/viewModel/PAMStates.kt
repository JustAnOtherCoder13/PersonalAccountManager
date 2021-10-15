package com.piconemarc.viewmodel.viewModel

import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpUtilsProvider

// todo reducer pattern accept multiple reducer but only one store for all app, find a way to get all state in one place
object PAMStates : VMState {
    val addPopUpState : AddOperationPopUpUtilsProvider.AddOperationPopUpVMState =
        AddOperationPopUpUtilsProvider().providedVmState
}