package com.piconemarc.viewmodel.viewModel.reducer.screen

import com.piconemarc.viewmodel.Reducer
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.ViewModelInnerStates

internal val myAccountDetailScreenReducer: Reducer<ViewModelInnerStates.MyAccountDetailScreenVMState> =
    { old, action ->
        action as AppActions.MyAccountDetailScreenAction
        when (action) {
            is AppActions.MyAccountDetailScreenAction.InitScreen -> old.copy(
                isVisible = true
            )
            is AppActions.MyAccountDetailScreenAction.CloseScreen -> old.copy(
                isVisible = false
            )
            is AppActions.MyAccountDetailScreenAction.UpdateAccountBalance -> old.copy(
                accountBalance = action.accountBalance
            )
            is AppActions.MyAccountDetailScreenAction.UpdateAccountRest -> old.copy(
                accountRest = action.accountRest
            )
            is AppActions.MyAccountDetailScreenAction.UpdateAccountMonthlyOperations -> old.copy(
                accountMonthlyOperations = action.accountMonthlyOperations
            )
            is AppActions.MyAccountDetailScreenAction.UpdateAccountName -> old.copy(
                accountName = action.accountName
            )
        }
    }