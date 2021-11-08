package com.piconemarc.viewmodel.viewModel


import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.VMState
import com.piconemarc.viewmodel.launchOnIOCatchingError
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.paymentScreenVMState_
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect

import javax.inject.Inject

@HiltViewModel
class MyPaymentViewModel @Inject constructor(
    store: DefaultStore<GlobalVmState>,

    private val getAllAccountsInteractor: GetAllAccountsInteractor,
) : BaseViewModel<AppActions.PaymentScreenAction, ViewModelInnerStates.PaymentScreenVmState>(
    store,
    paymentScreenVMState_
) {

    override fun dispatchAction(action: AppActions.PaymentScreenAction) {
        when (action) {
            is AppActions.PaymentScreenAction.InitScreen -> {
                viewModelScope.launchOnIOCatchingError(
                    block = {
                        getAllAccountsInteractor.getAllAccountsWithRelatedPaymentAsFlow().collect {
                            updateState(
                                GlobalAction.UpdatePaymentScreenState(
                                    AppActions.PaymentScreenAction.UpdateAllAccounts(it)
                                ),
                            )
                        }
                    }
                )
            }
            else -> updateState(GlobalAction.UpdatePaymentScreenState(action))
        }
    }
}