package com.piconemarc.viewmodel.viewModel.reducer.popUp

import com.piconemarc.viewmodel.Reducer
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.ViewModelInnerStates

val deleteOperationPopUpReducer: Reducer<ViewModelInnerStates.DeleteOperationPopUpVMState> =
    { old, action ->
        action as AppActions.DeleteOperationPopUpAction
        when (action) {
            is AppActions.DeleteOperationPopUpAction.InitPopUp<*> -> old.copy(
                isPopUpExpanded = true,
                isRelatedOperationDeleted = true
            )
            is AppActions.DeleteOperationPopUpAction.ClosePopUp -> old.copy(
                isPopUpExpanded = false
            )
            is AppActions.DeleteOperationPopUpAction.UpdateOperationToDelete<*> -> old.copy(
                operationToDelete = action.operationToDelete
            )
            is AppActions.DeleteOperationPopUpAction.UpdateTransferRelatedAccount -> old.copy(
                transferRelatedAccount = action.transferRelatedAccount
            )
            is AppActions.DeleteOperationPopUpAction.UpdateIsDeletedPermanently -> old.copy(
                isRelatedOperationDeleted = action.isRelatedOperationDeleted
            )
            is AppActions.DeleteOperationPopUpAction.DeleteOperation<*> -> old
            is AppActions.DeleteOperationPopUpAction.DeletePayment -> old
            is AppActions.DeleteOperationPopUpAction.DeleteTransfer -> old
        }
    }