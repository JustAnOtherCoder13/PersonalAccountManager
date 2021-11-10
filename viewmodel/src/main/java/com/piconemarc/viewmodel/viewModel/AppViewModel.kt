package com.piconemarc.viewmodel.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.AddAccountPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.AddOperationPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.DeleteAccountPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.DeleteOperationPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.baseAppScreenVmState_
import com.piconemarc.viewmodel.viewModel.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    store: DefaultStore<GlobalVmState>,
    private val addOperationPopUpActionDispatcher: AddOperationPopUpActionDispatcher,
    private val deleteAccountPopUpActionDispatcher: DeleteAccountPopUpActionDispatcher,
    private val addAccountPopUpActionDispatcher: AddAccountPopUpActionDispatcher,
    private val deleteOperationPopUpActionDispatcher: DeleteOperationPopUpActionDispatcher,
    private val getAllAccountsInteractor: GetAllAccountsInteractor
) : BaseViewModel<UiAction, ViewModelInnerStates.BaseAppScreenVmState>(store,baseAppScreenVmState_) {

    private var addOperationPopUpJob: Job? = null
    private var deleteAccountPopUpJob: Job? = null
    private var addAccountPopUpJob: Job? = null
    private var deleteOperationPopUpJob: Job? = null

    val addOperationPopUpState by addOperationPopUpActionDispatcher.uiState
    val deleteOperationPopUpState by deleteOperationPopUpActionDispatcher.uiState
    val addAccountPopUpState by addAccountPopUpActionDispatcher.uiState
    val deleteAccountPopUpState by deleteAccountPopUpActionDispatcher.uiState

    init {
        //init state
        viewModelScope.launch(block = { state.collectLatest { uiState.value = it } })
        dispatchAction(AppActions.BaseAppScreenAction.InitScreen)
    }

    override fun dispatchAction(action: UiAction) {
        when (action) {
            is AppActions.BaseAppScreenAction -> {
                when(action){
                    is AppActions.BaseAppScreenAction.InitScreen -> viewModelScope.launchOnIOCatchingError(
                        block = {
                            getAllAccountsInteractor.getAllAccountsAsFlow(this).collect { allAccounts ->
                                dispatchAction(AppActions.BaseAppScreenAction.UpdateAccounts(allAccounts))
                            }
                        }
                    )
                    else -> { updateState(GlobalAction.UpdateBaseAppScreenVmState(action)) }
                }
            }

            //launch job for each pop up when action is dispatched, cancel job on close
            is AppActions.AddOpePopupAction -> {
                addOperationPopUpJob = viewModelScope.launch {
                    addOperationPopUpActionDispatcher.dispatchAction(action, this)
                }
                if (action is AppActions.AddOpePopupAction.ClosePopUp) {
                    addOperationPopUpJob?.cancel()
                }
            }

            is AppActions.DeleteAccountAction -> {
                deleteAccountPopUpJob = viewModelScope.launch {
                    deleteAccountPopUpActionDispatcher.dispatchAction(action, this)
                }
                if (action is AppActions.DeleteAccountAction.ClosePopUp)
                    deleteAccountPopUpJob?.cancel()
            }

            is AppActions.AddAccountPopUpAction -> {
                addAccountPopUpJob = viewModelScope.launch {
                    addAccountPopUpActionDispatcher.dispatchAction(action, this)
                }
                if (action is AppActions.AddAccountPopUpAction.ClosePopUp)
                    addAccountPopUpJob?.cancel()
            }

            is AppActions.DeleteOperationPopUpAction -> {
                deleteAccountPopUpJob = viewModelScope.launch {
                    deleteOperationPopUpActionDispatcher.dispatchAction(action, this)
                }
                if (action is AppActions.DeleteOperationPopUpAction.ClosePopUp)
                    deleteOperationPopUpJob?.cancel()
            }
        }
    }
}