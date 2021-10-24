package com.piconemarc.viewmodel.viewModel.reducer.popUp

import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.Reducer
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.ViewModelInnerStates

internal val addAccountPopUpReducer: Reducer<ViewModelInnerStates.AddAccountPopUpVMState> =
    { old, action ->
        action as AppActions.AddAccountPopUpAction
        when (action) {
            is AppActions.AddAccountPopUpAction.InitPopUp -> old.copy(
                isPopUpExpanded = true,
                accountName = PresentationDataModel(),
                accountBalance = PresentationDataModel(),
                accountOverdraft = PresentationDataModel(),
                isBalanceError = true,
                isOverdraftError = true
            )
            is AppActions.AddAccountPopUpAction.ClosePopUp -> old.copy(
                isPopUpExpanded = false
            )
            is AppActions.AddAccountPopUpAction.AddNewAccount -> old.copy(
                isNameError = action.accountName.stringValue.trim().isEmpty()
            )
            is AppActions.AddAccountPopUpAction.FillAccountName -> old.copy(
                accountName = action.accountName
            )
            is AppActions.AddAccountPopUpAction.FillAccountBalance -> old.copy(
                accountBalance = action.accountBalance,
                isBalanceError = action.accountBalance.stringValue.trim().isEmpty()
            )
            is AppActions.AddAccountPopUpAction.FillAccountOverdraft -> old.copy(
                accountOverdraft = action.accountOverdraft,
                isOverdraftError = action.accountOverdraft.stringValue.trim().isEmpty()
            )
        }
    }