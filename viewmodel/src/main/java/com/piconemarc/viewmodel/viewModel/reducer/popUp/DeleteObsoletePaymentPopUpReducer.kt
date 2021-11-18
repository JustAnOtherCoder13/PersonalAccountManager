package com.piconemarc.viewmodel.viewModel.reducer.popUp

import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.Reducer
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates

val deleteObsoletePaymentPopUpReducer : Reducer<ViewModelInnerStates.DeleteObsoletePaymentPopUpVMState> =
    {old,action ->
        action as AppActions.DeleteObsoletePaymentPopUpAction
        when(action){
            is AppActions.DeleteObsoletePaymentPopUpAction.InitPopUp -> old.copy(
                isVisible = true
            )
            is AppActions.DeleteObsoletePaymentPopUpAction.ClosePopUp -> old.copy(
                isVisible = false
            )
            is AppActions.DeleteObsoletePaymentPopUpAction.DeleteObsoletePayment -> old
        }

    }