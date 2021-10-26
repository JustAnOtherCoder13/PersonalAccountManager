package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import com.piconemarc.core.domain.interactor.account.AddNewAccountInteractor
import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.launchCatchingError
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class AddAccountPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val addNewAccountInteractor: AddNewAccountInteractor
) : ActionDispatcher {
    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(GlobalAction.UpdateAddAccountPopUpState(action))
        when (action) {
            is AppActions.AddAccountPopUpAction.AddNewAccount -> {
                if (!AppSubscriber.AppUiState.addAccountPopUpUiState.isNameError)
                    scope.launchCatchingError(
                        block = {
                            addNewAccountInteractor.addNewAccount(
                                AccountUiModel(
                                    name = action.accountName,
                                    accountBalance = try {
                                        action.accountBalance.toDouble()
                                    } catch (e: NumberFormatException) {
                                        0.0
                                    },
                                    accountOverdraft = try {
                                        action.accountOverdraft.toDouble()
                                    } catch (e: NumberFormatException) {
                                        0.0
                                    }
                                )
                            )
                        },
                        doOnSuccess = {
                            updateState(
                                GlobalAction.UpdateAddAccountPopUpState(
                                    AppActions.AddAccountPopUpAction.ClosePopUp
                                ))
                        }
                    )
            }
        }
    }
}