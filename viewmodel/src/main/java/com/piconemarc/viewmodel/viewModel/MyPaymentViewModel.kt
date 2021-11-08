package com.piconemarc.viewmodel.viewModel


import androidx.lifecycle.viewModelScope
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.viewmodel.viewModel.utils.DefaultStore
import com.piconemarc.viewmodel.viewModel.utils.launchOnIOCatchingError
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.paymentScreenVMState_
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.BaseViewModel
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPaymentViewModel @Inject constructor(
    store: DefaultStore<GlobalVmState>,

    private val getAllAccountsInteractor: GetAllAccountsInteractor,
) : BaseViewModel<AppActions.PaymentScreenAction, ViewModelInnerStates.PaymentScreenVmState>(
    store,
    paymentScreenVMState_
) {

    init {
        viewModelScope.launch(block = { state.collectLatest { uiState.value = it } })

        viewModelScope.launchOnIOCatchingError(
            block = {
                getAllAccountsInteractor.getAllAccountsWithRelatedPaymentAsFlow().collect {
                    dispatchAction(
                        AppActions.PaymentScreenAction.UpdateAllAccounts(it)
                    )
                }
            }
        )

    }

    override fun dispatchAction(action: AppActions.PaymentScreenAction) {
        updateState(GlobalAction.UpdatePaymentScreenState(action))
    }

}