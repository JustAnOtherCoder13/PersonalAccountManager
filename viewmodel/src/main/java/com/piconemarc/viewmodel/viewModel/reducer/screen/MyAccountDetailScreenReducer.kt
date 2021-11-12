package com.piconemarc.viewmodel.viewModel.reducer.screen

import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.Reducer
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates

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
            is AppActions.MyAccountDetailScreenAction.UpdateAccountAndMonthlyOperations -> old.copy(
                selectedAccount = action.selectedAccount,
                accountMonthlyOperations = action.relatedMonthlyOperations
            )
            is AppActions.MyAccountDetailScreenAction.GetSelectedOperation -> old
            is AppActions.MyAccountDetailScreenAction.UpdateOperationMessage -> old.copy(
                operationDetailMessage = action.message
            )
        }
    }