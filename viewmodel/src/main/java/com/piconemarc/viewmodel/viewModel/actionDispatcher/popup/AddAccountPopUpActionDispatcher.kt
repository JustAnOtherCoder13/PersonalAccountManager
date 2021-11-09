package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.core.domain.interactor.account.AddNewAccountInteractor
import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.addAccountPoUpVmState_
import com.piconemarc.viewmodel.viewModel.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddAccountPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val addNewAccountInteractor: AddNewAccountInteractor
) : ActionDispatcher<ViewModelInnerStates.AddAccountPopUpVMState> {

    override val state: MutableStateFlow<ViewModelInnerStates.AddAccountPopUpVMState> =
        addAccountPoUpVmState_
    override val uiState: MutableState<ViewModelInnerStates.AddAccountPopUpVMState> =
        mutableStateOf(
            state.value
        )

    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(GlobalAction.UpdateAddAccountPopUpState(action))
        scope.launch { state.collectLatest {
            Log.i("TAG", "dispatchAction: $action  $it")
            uiState.value = it } }

        when (action) {
            is AppActions.AddAccountPopUpAction.AddNewAccount -> {
                if (!uiState.value.isNameError)
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
                                )
                            )
                        }
                    )
            }
            else -> updateState(GlobalAction.UpdateAddAccountPopUpState(action))
        }
    }
}