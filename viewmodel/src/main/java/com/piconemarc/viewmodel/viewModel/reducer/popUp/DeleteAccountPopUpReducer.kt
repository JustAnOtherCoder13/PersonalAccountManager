package com.piconemarc.viewmodel.viewModel.reducer.popUp

import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.viewmodel.Reducer
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.ViewModelInnerStates

internal val deleteAccountPopUpReducer: Reducer<ViewModelInnerStates.DeleteAccountPopUpVMState> =
    { old, action ->
        action as AppActions.DeleteAccountAction
        when (action) {
            is AppActions.DeleteAccountAction.InitPopUp -> old.copy(
                isPopUpExpanded = true,
            )
            is AppActions.DeleteAccountAction.ClosePopUp -> old.copy(
                isPopUpExpanded = false,
                accountUiToDelete = AccountUiModel()
            )
            is AppActions.DeleteAccountAction.UpdateAccountToDelete -> old.copy(
                accountUiToDelete = action.accountToDelete
            )
            is AppActions.DeleteAccountAction.DeleteAccount -> old
        }
    }