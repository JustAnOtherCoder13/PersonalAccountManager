package com.piconemarc.viewmodel.viewModel.reducer.screen

import com.piconemarc.viewmodel.Reducer
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.ViewModelInnerStates

internal val myAccountScreenReducer: Reducer<ViewModelInnerStates.MyAccountScreenVMState> =
    { old, action ->
        action as AppActions.MyAccountScreenAction
        when (action) {
            is AppActions.MyAccountScreenAction.InitScreen -> { old.copy(isVisible = true) }
            is AppActions.MyAccountScreenAction.CloseScreen -> old.copy(isVisible = false)
            is AppActions.MyAccountScreenAction.UpdateAccountList -> { old.copy(allAccounts = action.accountList) }
        }
    }