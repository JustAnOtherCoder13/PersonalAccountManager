package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.core.domain.interactor.account.DeleteAccountInteractor
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.deleteAccountVmState_
import com.piconemarc.viewmodel.viewModel.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteAccountPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val deleteAccountInteractor: DeleteAccountInteractor
) : ActionDispatcher<ViewModelInnerStates.DeleteAccountPopUpVMState> {

    override val state: MutableStateFlow<ViewModelInnerStates.DeleteAccountPopUpVMState>
        = deleteAccountVmState_
    override val uiState: MutableState<ViewModelInnerStates.DeleteAccountPopUpVMState>
        = mutableStateOf(state.value)

    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(GlobalAction.UpdateDeleteAccountPopUpState(action))
        scope.launch{state.collectLatest { uiState.value = it }}

        when (action) {
            is AppActions.DeleteAccountAction.InitPopUp -> {
                updateState(
                    GlobalAction.UpdateDeleteAccountPopUpState(
                        AppActions.DeleteAccountAction.UpdateAccountToDelete(
                            action.accountUiToDelete
                        )
                    )
                )
            }
            is AppActions.DeleteAccountAction.DeleteAccount -> {
                scope.launchOnIOCatchingError(
                    block = { deleteAccountInteractor.deleteAccount(action.accountUiToDelete) },
                    doOnSuccess = {
                        updateState(
                            GlobalAction.UpdateDeleteAccountPopUpState(
                                AppActions.DeleteAccountAction.ClosePopUp
                            )
                        )
                    }
                )
            }
        }
    }


}