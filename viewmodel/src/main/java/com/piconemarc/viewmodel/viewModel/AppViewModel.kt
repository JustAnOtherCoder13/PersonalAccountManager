package com.piconemarc.viewmodel.viewModel

import androidx.lifecycle.viewModelScope
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.StoreSubscriber
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.AddAccountPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.AddOperationPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.DeleteAccountPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.DeleteOperationPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.screen.BaseScreenActionDispatcher
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.baseAppScreenVmState_
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val store: DefaultStore<GlobalVmState>,
    private val baseScreenActionDispatcher: BaseScreenActionDispatcher,
    private val addOperationPopUpActionDispatcher: AddOperationPopUpActionDispatcher,
    private val deleteAccountPopUpActionDispatcher: DeleteAccountPopUpActionDispatcher,
    private val addAccountPopUpActionDispatcher: AddAccountPopUpActionDispatcher,
    private val deleteOperationPopUpActionDispatcher: DeleteOperationPopUpActionDispatcher,
) : BaseViewModel<UiAction, ViewModelInnerStates.BaseAppScreenVmState>(store,baseAppScreenVmState_) {

    private val subscriber: StoreSubscriber<GlobalVmState> = AppSubscriber().appStoreSubscriber

    private var baseAppScreenJob: Job? = null
    private var addOperationPopUpJob: Job? = null
    private var deleteAccountPopUpJob: Job? = null
    private var addAccountPopUpJob: Job? = null
    private var deleteOperationPopUpJob: Job? = null

    override fun dispatchAction(action: UiAction) {
        when (action) {
            //launch job for each screen when action for this screen is dispatched, cancel job on close
            is AppActions.BaseAppScreenAction -> {
                // subscribe to store on init app and remove subscriber when close app
                store.add(subscriber)
                baseAppScreenJob = viewModelScope.launch {
                    baseScreenActionDispatcher.dispatchAction(action, this)
                }
                if (action is AppActions.BaseAppScreenAction.CloseApp) {
                    baseAppScreenJob?.cancel()
                    store.remove(subscriber)
                }
            }

            is AppActions.AddOperationPopUpAction -> {
                addOperationPopUpJob = viewModelScope.launch {
                    addOperationPopUpActionDispatcher.dispatchAction(action, this)
                }
                if (action is AppActions.AddOperationPopUpAction.ClosePopUp) {
                    addOperationPopUpJob?.cancel()
                }
            }

            is AppActions.DeleteAccountAction -> {
                deleteAccountPopUpJob = viewModelScope.launch {
                    deleteAccountPopUpActionDispatcher.dispatchAction(action, this)
                }
                if (action is AppActions.DeleteAccountAction.ClosePopUp) deleteAccountPopUpJob?.cancel()
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
                if (action is AppActions.DeleteOperationPopUpAction.ClosePopUp) deleteOperationPopUpJob?.cancel()
            }
        }
    }

    //todo have to pass otherwise
    override fun onCleared() {
        super.onCleared()
        this.dispatchAction(
            GlobalAction.UpdateBaseAppScreenVmState(
                AppActions.BaseAppScreenAction.CloseApp
            )
        )
    }


}