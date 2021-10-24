package com.piconemarc.viewmodel.viewModel.actionDispatcher.screen

import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.operation.GetAllOperationsForAccountIdInteractor
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MyAccountDetailScreenActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val getAccountForIdInteractor :GetAccountForIdInteractor,
    private val getAllOperationsForAccountIdInteractor : GetAllOperationsForAccountIdInteractor
): ActionDispatcher  {
    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        when (action) {
            is AppActions.MyAccountDetailScreenAction.InitScreen -> {
                updateState(
                    GlobalAction.UpdateBaseAppScreenVmState(
                    AppActions.BaseAppScreenAction.UpdateInterlayerTiTle(
                        com.piconemarc.model.R.string.detail
                    )
                ))
                scope.launch {
                    getAccountForIdInteractor.getAccountForIdFlow(action.selectedAccount.id)
                        .collect {
                            updateState(
                                GlobalAction.UpdateMyAccountDetailScreenState(
                                AppActions.MyAccountDetailScreenAction.UpdateAccountBalance(
                                    PresentationDataModel(
                                        it.accountBalance.toString(),
                                        it.id
                                    )
                                )),

                                GlobalAction.UpdateMyAccountDetailScreenState(
                                AppActions.MyAccountDetailScreenAction.UpdateAccountRest(
                                    PresentationDataModel(
                                        (it.accountOverdraft + it.accountBalance).toString(),
                                        it.id
                                    )
                                )
                            ))
                        }
                }
                scope.launch {
                    getAllOperationsForAccountIdInteractor.getAllOperationsForAccountId(
                        action.selectedAccount.id
                    ).collect {
                        //todo pass in dao
                        val filteredList = it.filter {
                            it.emitDate.month.compareTo(Calendar.getInstance().time.month) == 0
                        }
                        updateState(
                            GlobalAction.UpdateMyAccountDetailScreenState(
                            AppActions.MyAccountDetailScreenAction.UpdateAccountMonthlyOperations(
                                filteredList
                            )
                        ),
                            GlobalAction.UpdateMyAccountDetailScreenState(
                            AppActions.MyAccountDetailScreenAction.UpdateAccountBalance(
                                PresentationDataModel(action.selectedAccount.accountBalance.toString())
                            )
                        ),
                            GlobalAction.UpdateMyAccountDetailScreenState(
                            AppActions.MyAccountDetailScreenAction.UpdateAccountRest(
                                PresentationDataModel(
                                    (action.selectedAccount.accountOverdraft + action.selectedAccount.accountBalance).toString()
                                )
                            )),

                            GlobalAction.UpdateMyAccountDetailScreenState(
                            AppActions.MyAccountDetailScreenAction.UpdateAccountName(
                                PresentationDataModel(
                                    action.selectedAccount.name,
                                    action.selectedAccount.id
                                )
                            ))
                        )
                    }
                }
            }
        }
    }
}