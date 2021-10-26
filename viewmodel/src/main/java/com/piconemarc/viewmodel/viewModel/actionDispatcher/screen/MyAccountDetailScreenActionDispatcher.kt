package com.piconemarc.viewmodel.viewModel.actionDispatcher.screen

import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.operation.GetAllOperationsForAccountIdInteractor
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.launchCatchingError
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import java.util.*
import javax.inject.Inject

class MyAccountDetailScreenActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val getAccountForIdInteractor: GetAccountForIdInteractor,
    private val getAllOperationsForAccountIdInteractor: GetAllOperationsForAccountIdInteractor
) : ActionDispatcher {

    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {

        when (action) {
            is AppActions.MyAccountDetailScreenAction.InitScreen -> {
                updateState(
                    GlobalAction.UpdateBaseAppScreenVmState(
                        AppActions.BaseAppScreenAction.UpdateInterlayerTiTle(com.piconemarc.model.R.string.detail)
                    ),
                    //init selectedAccount
                    GlobalAction.UpdateMyAccountDetailScreenState(
                        AppActions.MyAccountDetailScreenAction.UpdateSelectedAccount(
                            action.selectedAccount
                        )
                    )
                )
                scope.launchCatchingError(
                    block = {
                        getAllOperationsForAccountIdInteractor.getAllOperationsForAccountId(
                            action.selectedAccount.id
                        ).collect { accountOperations ->
                            updateState(
                                GlobalAction.UpdateMyAccountDetailScreenState(
                                    AppActions.MyAccountDetailScreenAction.UpdateAccountMonthlyOperations(
                                        accountOperations.filter {
                                            it.emitDate.month.compareTo(
                                                Calendar.getInstance().get(Calendar.MONTH)
                                            ) == 0
                                        }
                                    )
                                ),
                            )
                        }
                    }
                )
                scope.launchCatchingError(
                    block = {
                        getAccountForIdInteractor.getAccountForIdFlow(action.selectedAccount.id)
                            .collect {
                                updateState(
                                    GlobalAction.UpdateMyAccountDetailScreenState(
                                        AppActions.MyAccountDetailScreenAction.UpdateSelectedAccount(
                                            it
                                        )
                                    )
                                )
                            }
                    }
                )
            }
        }
    }
}