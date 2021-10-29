package com.piconemarc.viewmodel.viewModel.reducer.screen

import androidx.compose.ui.unit.dp
import com.piconemarc.viewmodel.Reducer
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.ViewModelInnerStates

val paymentScreenReducer : Reducer<ViewModelInnerStates.PaymentScreenVmState> = {
    old, action ->
    action as AppActions.PaymentScreenAction
    when(action){
        is AppActions.PaymentScreenAction.InitScreen -> old.copy(
            isVisible = true
        )
        is AppActions.PaymentScreenAction.CloseScreen-> old.copy(
            isVisible = false
        )
        is AppActions.PaymentScreenAction.UpdateAllAccounts-> old.copy(
            allAccounts = action.allAccounts
        )
        is AppActions.PaymentScreenAction.UpdateBoxHeightForAccount -> old.copy(
            boxHeight = 100.dp + (action.allPaymentForAccountCount * 36).dp
        )
        is AppActions.PaymentScreenAction.UpdatePaymentsForAccount -> old
    }

}