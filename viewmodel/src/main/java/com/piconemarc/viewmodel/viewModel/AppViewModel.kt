package com.piconemarc.viewmodel.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.StoreSubscriber
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.AddAccountPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.AddOperationPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.DeleteAccountPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.DeleteOperationPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.screen.BaseScreenActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.screen.MyAccountDetailScreenActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.screen.MyAccountScreenActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.screen.PaymentScreenActionDispatcher
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val store: DefaultStore<GlobalVmState>,
    private val baseScreenActionDispatcher: BaseScreenActionDispatcher,
    private val myAccountScreenActionDispatcher: MyAccountScreenActionDispatcher,
    private val myAccountDetailScreenActionDispatcher: MyAccountDetailScreenActionDispatcher,
    private val addOperationPopUpActionDispatcher: AddOperationPopUpActionDispatcher,
    private val deleteAccountPopUpActionDispatcher: DeleteAccountPopUpActionDispatcher,
    private val addAccountPopUpActionDispatcher: AddAccountPopUpActionDispatcher,
    private val deleteOperationPopUpActionDispatcher: DeleteOperationPopUpActionDispatcher,
    private val paymentScreenActionDispatcher: PaymentScreenActionDispatcher
) : ViewModel() {

    private val subscriber: StoreSubscriber<GlobalVmState> = AppSubscriber().appStoreSubscriber

    private var baseAppScreenJob: Job? = null
    private var myAccountScreenJob: Job? = null
    private var myAccountDetailScreenJob: Job? = null
    private var addOperationPopUpJob: Job? = null
    private var deleteAccountPopUpJob: Job? = null
    private var addAccountPopUpJob: Job? = null
    private var deleteOperationPopUpJob: Job? = null
    private var paymentScreenJob : Job? = null

    fun dispatchAction(action: UiAction) {
        when (action) {
            //launch job for each screen when action for this screen is dispatched, cancel job on close
            is AppActions.BaseAppScreenAction -> {
                store.add(subscriber)
                baseAppScreenJob = viewModelScope.launch {
                    baseScreenActionDispatcher.dispatchAction(action, this)
                }
                if (action is AppActions.BaseAppScreenAction.CloseApp) {
                    baseAppScreenJob?.cancel()
                    store.remove(subscriber)
                }
            }

            is AppActions.MyAccountScreenAction -> {
                myAccountScreenJob = viewModelScope.launch {
                    myAccountScreenActionDispatcher.dispatchAction(action, this)
                }
                if (action is AppActions.MyAccountScreenAction.CloseScreen) myAccountScreenJob?.cancel()
            }

            is AppActions.MyAccountDetailScreenAction -> {
                store.dispatch(GlobalAction.UpdateMyAccountDetailScreenState(action))
                myAccountDetailScreenJob = viewModelScope.launch {
                    myAccountDetailScreenActionDispatcher.dispatchAction(action, this)

                    if (action is AppActions.MyAccountDetailScreenAction.CloseScreen) {
                        store.dispatch(
                            GlobalAction.UpdateMyAccountScreenState(
                                AppActions.MyAccountScreenAction.InitScreen
                            ))
                        myAccountDetailScreenJob?.cancel()
                    }
                }
            }

            is AppActions.PaymentScreenAction -> {

                paymentScreenJob = viewModelScope.launch {
                    paymentScreenActionDispatcher.dispatchAction(action, this)
                }

                if (action is AppActions.PaymentScreenAction.CloseScreen)
                    paymentScreenJob?.cancel()

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
                if (action is AppActions.AddAccountPopUpAction.ClosePopUp) addAccountPopUpJob?.cancel()
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