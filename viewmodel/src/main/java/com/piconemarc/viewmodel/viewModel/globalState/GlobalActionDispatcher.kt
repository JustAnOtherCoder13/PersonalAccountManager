package com.piconemarc.viewmodel.viewModel.globalState

import com.piconemarc.viewmodel.viewModel.ActionDispatcher
import com.piconemarc.viewmodel.viewModel.DefaultStore
import com.piconemarc.viewmodel.viewModel.StoreSubscriber
import com.piconemarc.viewmodel.viewModel.UiAction
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpUtilsProvider
import javax.inject.Inject

class GlobalActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalUtilProvider.GlobalVmState>
) : ActionDispatcher<UiAction, GlobalUtilProvider.GlobalVmState> {

    override val subscriber: StoreSubscriber<GlobalUtilProvider.GlobalVmState> =
        GlobalUtilProvider().globalStoreSubscriber

    override fun dispatchAction(action: UiAction) {
        when (action) {
            is GlobalUtilProvider.GlobalAction.UpdateBaseAppScreenVmState -> updateBaseAppState(
                action
            )
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction -> {
                updateAddPopState(action)
            }
        }
    }

    private fun updateBaseAppState(action: UiAction) {
        store.add(subscriber)
        store.dispatch(
            GlobalUtilProvider.GlobalAction.UpdateBaseAppScreenVmState(action)
        )
    }

    private fun updateAddPopState(action: UiAction) {
        store.add(subscriber)
        store.dispatch(
            GlobalUtilProvider.GlobalAction.UpdateAddPopUpState(action)
        )
    }
}