package com.piconemarc.viewmodel.viewModel.globalState

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.viewmodel.viewModel.*
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpUtilsProvider
import com.piconemarc.viewmodel.viewModel.baseAppScreen.BaseAppScreenUtilProvider
import com.piconemarc.viewmodel.viewModel.globalState.GlobalUtilProvider.GlobalMutableState.addOperationPopUpVMState_
import com.piconemarc.viewmodel.viewModel.globalState.GlobalUtilProvider.GlobalMutableState.baseAppScreenVmState_

class GlobalUtilProvider {


    data class GlobalVmState(
        val baseAppScreenVmState: BaseAppScreenUtilProvider.BaseAppScreenVmState = BaseAppScreenUtilProvider.BaseAppScreenVmState(),
        var addOperationPopUpVMState: AddOperationPopUpUtilsProvider.AddOperationPopUpVMState = AddOperationPopUpUtilsProvider.AddOperationPopUpVMState()
    ) : VMState

    sealed class GlobalAction : UiAction {
        data class UpdateBaseAppScreenVmState(val baseAction: UiAction) : GlobalAction()
        data class UpdateAddPopUpState(val baseAction: UiAction) : GlobalAction()
    }

    val appScreenReducer : Reducer<BaseAppScreenUtilProvider.BaseAppScreenVmState> = BaseAppScreenUtilProvider().appScreenReducer
    val addPopUpReducer : Reducer<AddOperationPopUpUtilsProvider.AddOperationPopUpVMState> = AddOperationPopUpUtilsProvider().providedReducer

    val globalReducer: Reducer<GlobalVmState> = { old, action ->
        when (action) {
            is GlobalAction.UpdateBaseAppScreenVmState ->
                old.copy(
                    baseAppScreenVmState = appScreenReducer(old.baseAppScreenVmState, action.baseAction),
                )
            is GlobalAction.UpdateAddPopUpState -> {
                old.copy(
                    addOperationPopUpVMState = addPopUpReducer(old.addOperationPopUpVMState,action.baseAction)
                )


            }
            else -> { old }
        }
    }

    private object GlobalMutableState {
        val baseAppScreenVmState_: MutableState<BaseAppScreenUtilProvider.BaseAppScreenVmState> =
            mutableStateOf(
                BaseAppScreenUtilProvider.BaseAppScreenVmState()
            )
        val addOperationPopUpVMState_: MutableState<AddOperationPopUpUtilsProvider.AddOperationPopUpVMState> =
            mutableStateOf(
                AddOperationPopUpUtilsProvider.AddOperationPopUpVMState()
            )
    }

    val globalStoreSubscriber: StoreSubscriber<GlobalVmState> = { globalVmState ->
        baseAppScreenVmState_.value = globalVmState.baseAppScreenVmState
        addOperationPopUpVMState_.value = globalVmState.addOperationPopUpVMState
    }

 object GlobalUiState : UiState {
     val baseAppScreenVmState by baseAppScreenVmState_
     val addOperationPopUpUiState by addOperationPopUpVMState_
 }

}