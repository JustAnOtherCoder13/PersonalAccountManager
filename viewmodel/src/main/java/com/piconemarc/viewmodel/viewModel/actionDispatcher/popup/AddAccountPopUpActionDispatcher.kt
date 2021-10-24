package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import com.piconemarc.core.domain.interactor.account.AddNewAccountInteractor
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.viewmodel.ComponentActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddAccountPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val addNewAccountInteractor: AddNewAccountInteractor

) : ComponentActionDispatcher {
    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(
            GlobalAction.UpdateAddAccountPopUpState(
                action
            )
        )
        when (action) {
            is AppActions.AddAccountPopUpAction.AddNewAccount -> {
                if (!AppSubscriber.AppUiState.addAccountPopUpUiState.isNameError)
                    scope.launch {
                        try {
                            addNewAccountInteractor.addNewAccount(
                                AccountModel(
                                    name = action.accountName.stringValue,
                                    accountBalance = try {
                                        action.accountBalance.stringValue.toDouble()
                                    } catch (e: NumberFormatException) {
                                        0.0
                                    },
                                    accountOverdraft = try {
                                        action.accountOverdraft.stringValue.toDouble()
                                    } catch (e: NumberFormatException) {
                                        0.0
                                    }
                                )
                            )
                            updateState(
                                GlobalAction.UpdateAddAccountPopUpState(
                                AppActions.AddAccountPopUpAction.ClosePopUp
                            ))
                        } catch (e: Exception) {
                            //todo catch error
                        }
                    }
            }
        }
    }
}