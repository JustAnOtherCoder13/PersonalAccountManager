package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.core.domain.interactor.payment.DeletePaymentInteractor
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.deleteObsoletePaymentPopUpVMState_
import com.piconemarc.viewmodel.viewModel.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteObsoletePaymentPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val deletePaymentInteractor: DeletePaymentInteractor
) : ActionDispatcher<ViewModelInnerStates.DeleteObsoletePaymentPopUpVMState> {

    override val state: MutableStateFlow<ViewModelInnerStates.DeleteObsoletePaymentPopUpVMState>
        = deleteObsoletePaymentPopUpVMState_
    override val uiState: MutableState<ViewModelInnerStates.DeleteObsoletePaymentPopUpVMState>
        = mutableStateOf(state.value)

    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(GlobalAction.UpdateDeleteObsoletePaymentPopUpState(action))
        scope.launch { state.collectLatest { uiState.value = it } }

        when (action){
            is AppActions.DeleteObsoletePaymentPopUpAction.DeleteObsoletePayment -> {
                scope.launchOnIOCatchingError(
                    block = {
                        deletePaymentInteractor.deleteObsoletePayments(action.obsoletePayments)
                    },
                    doOnSuccess = {
                        dispatchAction(AppActions.DeleteObsoletePaymentPopUpAction.ClosePopUp,scope)
                    }
                )
            }
        }

    }
}