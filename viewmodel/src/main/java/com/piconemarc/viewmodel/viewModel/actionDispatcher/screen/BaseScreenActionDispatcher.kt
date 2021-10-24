package com.piconemarc.viewmodel.viewModel.actionDispatcher.screen

import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.viewmodel.ComponentActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.viewModel.AppActions.BaseAppScreenAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class BaseScreenActionDispatcher @Inject constructor(
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
    override val store: DefaultStore<GlobalVmState>
) : ComponentActionDispatcher {
    private var getAllAccountsJob: Job? = null
//todo use coroutine scope
    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(GlobalAction.UpdateBaseAppScreenVmState(action))
        when (action) {
            is BaseAppScreenAction.InitScreen -> {
                getAllAccountsJob = scope.launch {
                    getAllAccountsInteractor.getAllAccounts().collect { allAccounts ->
                        updateState(
                            GlobalAction.UpdateBaseAppScreenVmState(
                                BaseAppScreenAction.UpdateFooterBalance(
                                    allAccounts
                                )
                            ),
                            GlobalAction.UpdateBaseAppScreenVmState(
                                BaseAppScreenAction.UpdateFooterRest(
                                    allAccounts
                                )
                            ),
                            GlobalAction.UpdateBaseAppScreenVmState(
                                BaseAppScreenAction.UpdateAccounts(
                                    allAccounts
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}

