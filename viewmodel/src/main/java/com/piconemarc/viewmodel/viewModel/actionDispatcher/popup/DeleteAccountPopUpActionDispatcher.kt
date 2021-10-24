package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import com.piconemarc.core.domain.interactor.account.DeleteAccountInteractor
import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.viewmodel.ComponentActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteAccountPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val getAccountForIdInteractor: GetAccountForIdInteractor,
    private val deleteAccountInteractor: DeleteAccountInteractor

) : ComponentActionDispatcher {
    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(
            GlobalAction.UpdateDeleteAccountPopUpState(
                action)
        )
        when (action) {
            is AppActions.DeleteAccountAction.InitPopUp -> {
                scope.launch {
                    updateState(
                        GlobalAction.UpdateDeleteAccountPopUpState(
                        AppActions.DeleteAccountAction.UpdateAccountToDelete(
                            getAccountForIdInteractor.getAccountForId(action.accountName.objectIdReference)
                        )
                    ))
                }
            }
            is AppActions.DeleteAccountAction.DeleteAccount -> {
                scope.launch {
                    deleteAccountInteractor.deleteAccount(AppSubscriber.AppUiState.deleteAccountUiState.accountToDelete)
                }
            }
        }
    }

}