package com.piconemarc.viewmodel.viewModel.reducer.popUp

import com.piconemarc.model.entity.AccountModel
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
                accountToDelete = AccountModel()
            )
            is AppActions.DeleteAccountAction.DeleteAccount -> old.copy(
                isPopUpExpanded = false,
            )
            is AppActions.DeleteAccountAction.UpdateAccountToDelete -> old.copy(
                accountToDelete = action.accountToDelete,
                accountToDeleteBalance =  action.accountToDelete.accountBalance.toString(),
                accountToDeleteName = action.accountToDelete.name,
            )
        }
    }