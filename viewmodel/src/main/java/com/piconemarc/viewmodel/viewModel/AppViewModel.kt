package com.piconemarc.viewmodel.viewModel

import com.piconemarc.viewmodel.ActionDispatcher
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
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val baseScreenActionDispatcher: BaseScreenActionDispatcher,
    private val myAccountScreenActionDispatcher: MyAccountScreenActionDispatcher,
    private val myAccountDetailScreenActionDispatcher: MyAccountDetailScreenActionDispatcher,
    private val addOperationPopUpActionDispatcher: AddOperationPopUpActionDispatcher,
    private val deleteAccountPopUpActionDispatcher: DeleteAccountPopUpActionDispatcher,
    private val addAccountPopUpActionDispatcher: AddAccountPopUpActionDispatcher,
    private val deleteOperationPopUpActionDispatcher: DeleteOperationPopUpActionDispatcher
) : ActionDispatcher<UiAction, GlobalVmState>() {

    override val subscriber: StoreSubscriber<GlobalVmState> =
        AppSubscriber().appStoreSubscriber

    override fun dispatchAction(action: UiAction) {
        store.add(subscriber)
        when (action) {
            is AppActions.BaseAppScreenAction -> baseScreenActionDispatcher.dispatchAction(
                action,
                scope
            )
            is AppActions.MyAccountScreenAction -> myAccountScreenActionDispatcher.dispatchAction(
                action,
                scope
            )
            is AppActions.MyAccountDetailScreenAction -> myAccountDetailScreenActionDispatcher.dispatchAction(
                action,
                scope
            )
            is AppActions.AddOperationPopUpAction -> addOperationPopUpActionDispatcher.dispatchAction(
                action,
                scope
            )
            is AppActions.DeleteAccountAction -> deleteAccountPopUpActionDispatcher.dispatchAction(
                action,
                scope
            )
            is AppActions.AddAccountPopUpAction -> addAccountPopUpActionDispatcher.dispatchAction(
                action,
                scope
            )
            is AppActions.DeleteOperationPopUpAction -> deleteOperationPopUpActionDispatcher.dispatchAction(
                action,
                scope
            )
        }
    }
}