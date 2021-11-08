package com.piconemarc.viewmodel.viewModel.reducer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.viewmodel.*
import com.piconemarc.viewmodel.viewModel.ViewModelInnerStates
import com.piconemarc.viewmodel.viewModel.reducer.popUp.addAccountPopUpReducer
import com.piconemarc.viewmodel.viewModel.reducer.popUp.addOperationPopUpReducer
import com.piconemarc.viewmodel.viewModel.reducer.popUp.deleteAccountPopUpReducer
import com.piconemarc.viewmodel.viewModel.reducer.popUp.deleteOperationPopUpReducer
import com.piconemarc.viewmodel.viewModel.reducer.screen.appBaseScreenReducer
import com.piconemarc.viewmodel.viewModel.reducer.screen.myAccountDetailScreenReducer
import com.piconemarc.viewmodel.viewModel.reducer.screen.myAccountScreenReducer
import com.piconemarc.viewmodel.viewModel.reducer.screen.paymentScreenReducer
import kotlinx.coroutines.flow.MutableStateFlow

//Encapsulate each component or screen state in Mutable state-------------------------------------

//SCREEN_______________________________________________________________________________________
internal val baseAppScreenVmState_: MutableStateFlow<ViewModelInnerStates.BaseAppScreenVmState> =
    MutableStateFlow(
        ViewModelInnerStates.BaseAppScreenVmState()
    )
internal val myAccountScreenVMState_: MutableStateFlow<ViewModelInnerStates.MyAccountScreenVMState> =
    MutableStateFlow(
        ViewModelInnerStates.MyAccountScreenVMState()
    )

internal val myAccountDetailScreenVMState_: MutableStateFlow<ViewModelInnerStates.MyAccountDetailScreenVMState> =
    MutableStateFlow(
        ViewModelInnerStates.MyAccountDetailScreenVMState()
    )

internal val paymentScreenVMState_ : MutableStateFlow<ViewModelInnerStates.PaymentScreenVmState> =
    MutableStateFlow(
        ViewModelInnerStates.PaymentScreenVmState()
    )

//POP UP________________________________________________________________________________________

private val addOperationPopUpVMState_: MutableState<ViewModelInnerStates.AddOperationPopUpVMState> =
    mutableStateOf(
        ViewModelInnerStates.AddOperationPopUpVMState()
    )

private val deleteOperationPopUpVMState_: MutableState<ViewModelInnerStates.DeleteOperationPopUpVMState> =
    mutableStateOf(
        ViewModelInnerStates.DeleteOperationPopUpVMState()
    )

private val deleteAccountVmState_: MutableState<ViewModelInnerStates.DeleteAccountPopUpVMState> =
    mutableStateOf(
        ViewModelInnerStates.DeleteAccountPopUpVMState()
    )

private val addAccountPoUpVmState_: MutableState<ViewModelInnerStates.AddAccountPopUpVMState> =
    mutableStateOf(
        ViewModelInnerStates.AddAccountPopUpVMState()
    )

//Encapsulate each state in GlobalVMState to be modifiable in reducer--------------------------------------

data class GlobalVmState(
    // state as var to be re attribute in app reducer
    var baseAppScreenVmState: ViewModelInnerStates.BaseAppScreenVmState,
    var addOperationPopUpVMState: ViewModelInnerStates.AddOperationPopUpVMState,
    var deleteAccountPopUpVMState: ViewModelInnerStates.DeleteAccountPopUpVMState,
    var addAccountPopUpVMState: ViewModelInnerStates.AddAccountPopUpVMState,
    var myAccountScreenVmState: ViewModelInnerStates.MyAccountScreenVMState,
    var myAccountDetailScreenVMState: ViewModelInnerStates.MyAccountDetailScreenVMState,
    var deleteOperationPopUpVmState: ViewModelInnerStates.DeleteOperationPopUpVMState,
    var paymentScreenVmState : ViewModelInnerStates.PaymentScreenVmState
) : VMState


//Declare Action that will transform states, pass base action to trigger provided reducer-----------

sealed class GlobalAction : UiAction {
    data class UpdateBaseAppScreenVmState(val baseAction: UiAction) : GlobalAction()
    data class UpdateAddOperationPopUpState(val baseAction: UiAction) : GlobalAction()
    data class UpdateDeleteAccountPopUpState(val baseAction: UiAction) : GlobalAction()
    data class UpdateAddAccountPopUpState(val baseAction: UiAction) : GlobalAction()
    data class UpdateMyAccountDetailScreenState(val baseAction: UiAction) : GlobalAction()
    data class UpdateMyAccountScreenState(val baseAction: UiAction) : GlobalAction()
    data class UpdateDeleteOperationPopUpState(val baseAction: UiAction) : GlobalAction()
    data class UpdatePaymentScreenState(val baseAction: UiAction) : GlobalAction()
}

//Declare app reducer, for each component, copy old state,
// call provided reducer with old state and base action
//reducer will return updated state

internal val appReducer: Reducer<GlobalVmState> = { old, action ->
    action as GlobalAction
    when (action) {
        is GlobalAction.UpdateBaseAppScreenVmState -> {
            old.copy(
                baseAppScreenVmState = appBaseScreenReducer(
                    old.baseAppScreenVmState,
                    action.baseAction
                ),
            )
        }
        is GlobalAction.UpdateAddOperationPopUpState -> {
            old.copy(
                addOperationPopUpVMState = addOperationPopUpReducer(
                    old.addOperationPopUpVMState,
                    action.baseAction
                )
            )
        }
        is GlobalAction.UpdateDeleteAccountPopUpState -> {
            old.copy(
                deleteAccountPopUpVMState = deleteAccountPopUpReducer(
                    old.deleteAccountPopUpVMState,
                    action.baseAction
                )
            )
        }
        is GlobalAction.UpdateAddAccountPopUpState -> {
            old.copy(
                addAccountPopUpVMState = addAccountPopUpReducer(
                    old.addAccountPopUpVMState,
                    action.baseAction
                )
            )
        }
        is GlobalAction.UpdateMyAccountDetailScreenState -> {
            old.copy(
                myAccountDetailScreenVMState = myAccountDetailScreenReducer(
                    old.myAccountDetailScreenVMState,
                    action.baseAction
                )
            )
        }
        is GlobalAction.UpdateMyAccountScreenState -> {
            old.copy(
                myAccountScreenVmState = myAccountScreenReducer(
                    old.myAccountScreenVmState,
                    action.baseAction
                )
            )
        }
        is GlobalAction.UpdateDeleteOperationPopUpState -> {
            old.copy(
                deleteOperationPopUpVmState = deleteOperationPopUpReducer(
                    old.deleteOperationPopUpVmState,
                    action.baseAction
                )
            )
        }
        is GlobalAction.UpdatePaymentScreenState -> {
            old.copy(
                paymentScreenVmState = paymentScreenReducer(
                    old.paymentScreenVmState,
                    action.baseAction
                )
            )
        }
    }
}

class AppSubscriber {
    //Subscribe to store and update mutable state value with last updated state by app reducer
    internal val appStoreSubscriber: StoreSubscriber<GlobalVmState> =
        { globalVmState ->
            baseAppScreenVmState_.value = globalVmState.baseAppScreenVmState
            //addOperationPopUpVMState_.value = globalVmState.addOperationPopUpVMState
           // deleteAccountVmState_.value = globalVmState.deleteAccountPopUpVMState
            //addAccountPoUpVmState_.value = globalVmState.addAccountPopUpVMState
            myAccountDetailScreenVMState_.value = globalVmState.myAccountDetailScreenVMState
            myAccountScreenVMState_.value = globalVmState.myAccountScreenVmState
            //deleteOperationPopUpVMState_.value = globalVmState.deleteOperationPopUpVmState
            paymentScreenVMState_.value = globalVmState.paymentScreenVmState
        }

    //Expose state to view with delegate mutable state
    object AppUiState : UiState {
        val addOperationPopUpUiState by addOperationPopUpVMState_
        val deleteAccountUiState by deleteAccountVmState_
        val addAccountPopUpUiState by addAccountPoUpVmState_
        val deleteOperationPopUpUiState by deleteOperationPopUpVMState_
    }
}