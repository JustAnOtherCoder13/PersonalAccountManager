package com.piconemarc.viewmodel.viewModel.reducer.screen

import com.piconemarc.viewmodel.Reducer
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.ViewModelInnerStates

internal val myAccountDetailScreenReducer: Reducer<ViewModelInnerStates.MyAccountDetailScreenVMState> =
    { old, action ->
        action as AppActions.MyAccountDetailScreenAction
        when (action) {
            is AppActions.MyAccountDetailScreenAction.InitScreen -> old.copy(
                isVisible = true,
                selectedAccount = action.selectedAccount
            )
            is AppActions.MyAccountDetailScreenAction.CloseScreen -> old.copy(
                isVisible = false
            )
            is AppActions.MyAccountDetailScreenAction.UpdateAccountMonthlyOperations -> old.copy(
                accountMonthlyOperations = action.accountMonthlyOperations
            )
            is AppActions.MyAccountDetailScreenAction.UpdateSelectedAccount -> old.copy(
                selectedAccount = action.account
            )
            is AppActions.MyAccountDetailScreenAction.GetSelectedOperation -> old
            is AppActions.MyAccountDetailScreenAction.UpdateOperationMessage -> old.copy(
                operationDetailMessage = action.message
            )
        }
    }