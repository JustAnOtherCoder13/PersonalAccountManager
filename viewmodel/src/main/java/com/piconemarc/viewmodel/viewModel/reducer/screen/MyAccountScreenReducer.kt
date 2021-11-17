package com.piconemarc.viewmodel.viewModel.reducer.screen

import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.Reducer
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates

internal val myAccountScreenReducer: Reducer<ViewModelInnerStates.MyAccountScreenVMState> =
    { old, action ->
        action as AppActions.MyAccountScreenAction
        when (action) {
             is AppActions.MyAccountScreenAction.UpdateAccountList -> { old.copy(allAccounts = action.accountList) }
        }
    }