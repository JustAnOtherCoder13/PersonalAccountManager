package com.piconemarc.viewmodel.viewModel.actionDispatcher.screen

import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.launchOnIOCatchingError
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class PaymentScreenActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,

    ) : ActionDispatcher {

    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(GlobalAction.UpdatePaymentScreenState(action))
        when (action) {
            is AppActions.PaymentScreenAction.InitScreen -> {
                scope.launchOnIOCatchingError(
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
        }
    }
}