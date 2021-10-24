package com.piconemarc.viewmodel.viewModel.actionDispatcher.screen

import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyAccountScreenActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val getAllAccountsInteractor: GetAllAccountsInteractor
) : ActionDispatcher {

    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(GlobalAction.UpdateMyAccountScreenState(action))
        when (action) {
            is AppActions.MyAccountScreenAction.InitScreen -> {
                updateState(
                    GlobalAction.UpdateBaseAppScreenVmState(
                        AppActions.BaseAppScreenAction.UpdateInterlayerTiTle(
                            com.piconemarc.model.R.string.myAccountsInterLayerTitle
                        )
                    )
                )
                scope.launch {
                    getAllAccountsInteractor.getAllAccounts().collect {
                        updateState(
                            GlobalAction.UpdateMyAccountScreenState(
                                AppActions.MyAccountScreenAction.UpdateAccountList(it)
                            )
                        )
                    }
                }
            }
        }
    }
}