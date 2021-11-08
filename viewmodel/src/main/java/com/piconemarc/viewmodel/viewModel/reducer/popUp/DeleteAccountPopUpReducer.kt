package com.piconemarc.viewmodel.viewModel.reducer.popUp

import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.viewmodel.viewModel.utils.Reducer
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates

internal val deleteAccountPopUpReducer: Reducer<ViewModelInnerStates.DeleteAccountPopUpVMState> =
    { old, action ->
        action as AppActions.DeleteAccountAction
        when (action) {
            is AppActions.DeleteAccountAction.InitPopUp -> old.copy(
                isPopUpExpanded = true,
            )
            is AppActions.DeleteAccountAction.ClosePopUp -> old.copy(
                isPopUpExpanded = false,
                accountToDelete = AccountUiModel()
            )
            is AppActions.DeleteAccountAction.UpdateAccountToDelete -> old.copy(
                accountToDelete = action.accountToDelete
            )
            is AppActions.DeleteAccountAction.DeleteAccount -> old
        }
    }