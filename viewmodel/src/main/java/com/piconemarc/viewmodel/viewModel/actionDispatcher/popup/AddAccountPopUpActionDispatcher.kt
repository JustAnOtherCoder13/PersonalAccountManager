package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import com.piconemarc.core.domain.interactor.account.AddNewAccountInteractor
import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.viewmodel.viewModel.utils.ActionDispatcher
import com.piconemarc.viewmodel.viewModel.utils.DefaultStore
import com.piconemarc.viewmodel.viewModel.utils.UiAction
import com.piconemarc.viewmodel.viewModel.utils.launchOnIOCatchingError
import com.piconemarc.viewmodel.viewModel.utils.AppActions
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
        when (action) {
            is AppActions.AddAccountPopUpAction.AddNewAccount -> {
                updateState(GlobalAction.UpdateAddAccountPopUpState(action))
                if (!AppSubscriber.AppUiState.addAccountPopUpUiState.value.isNameError)
                    scope.launchOnIOCatchingError(
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
            else -> updateState(GlobalAction.UpdateAddAccountPopUpState(action))
        }
    }
}