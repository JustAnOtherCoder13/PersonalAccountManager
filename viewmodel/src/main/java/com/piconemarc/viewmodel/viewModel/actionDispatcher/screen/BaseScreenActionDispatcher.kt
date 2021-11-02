package com.piconemarc.viewmodel.viewModel.actionDispatcher.screen

import android.util.Log
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.launchOnIOCatchingError
import com.piconemarc.viewmodel.viewModel.AppActions.BaseAppScreenAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class BaseScreenActionDispatcher @Inject constructor(
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
    override val store: DefaultStore<GlobalVmState>
) : ActionDispatcher {

    private var getAllAccountsJob: Job? = null

    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        when (action) {
            is BaseAppScreenAction.InitScreen -> {
                getAllAccountsJob = scope.launchOnIOCatchingError(
                    block = {
                        getAllAccountsInteractor.getAllAccountsAsFlow(scope).collect { allAccounts ->
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
                )
            }
            else->updateState(GlobalAction.UpdateBaseAppScreenVmState(action))
        }
    }
}