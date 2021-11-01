package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import com.piconemarc.core.domain.interactor.account.DeleteAccountInteractor
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.launchOnIOCatchingError
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class DeleteAccountPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val deleteAccountInteractor: DeleteAccountInteractor
) : ActionDispatcher {
    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {

        updateState(GlobalAction.UpdateDeleteAccountPopUpState(action))
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