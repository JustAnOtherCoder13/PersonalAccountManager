package com.piconemarc.viewmodel.viewModel.reducer.screen

import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.Reducer
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates

val paymentScreenReducer : Reducer<ViewModelInnerStates.PaymentScreenVmState> = {
    old, action ->
    action as AppActions.PaymentScreenAction
    when(action){
        is AppActions.PaymentScreenAction.UpdateAllAccounts-> {
            old.copy(
                allAccounts = action.allAccounts
            )
        }
        is AppActions.PaymentScreenAction.PassSinglePayment -> old

        is AppActions.PaymentScreenAction.PassAllPaymentsForAccount -> old
    }

}