package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import com.piconemarc.core.domain.interactor.account.DeleteAccountInteractor
import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteAccountPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val getAccountForIdInteractor: GetAccountForIdInteractor,
    private val deleteAccountInteractor: DeleteAccountInteractor

) : ActionDispatcher {
    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(GlobalAction.UpdateDeleteAccountPopUpState(action))
        when (action) {
            is AppActions.DeleteAccountAction.InitPopUp -> {
                scope.launch {
                    updateState(
                        //todo no need to be in scope
                        GlobalAction.UpdateDeleteAccountPopUpState(
                        AppActions.DeleteAccountAction.UpdateAccountToDelete(
                            action.accountUiToDelete
                        )
                    ))
                }
            }
            is AppActions.DeleteAccountAction.DeleteAccount -> {
                scope.launch {
                    deleteAccountInteractor.deleteAccount(action.accountUiToDelete)
                }
            }
        }
    }

}