package com.piconemarc.viewmodel.viewModel

import androidx.lifecycle.ViewModel
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.StoreSubscriber
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.VMState
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState

abstract class BaseViewModel( private val store: DefaultStore<GlobalVmState>) : ViewModel(){

    private val subscriber: StoreSubscriber<GlobalVmState> = AppSubscriber().appStoreSubscriber

    init {store.add(subscriber)}

    override fun onCleared() {
        store.remove(subscriber)
        super.onCleared()
    }

    fun updateState(vararg action: GlobalAction) {
        action.forEach {
            store.dispatch(it)
        }
    }
    abstract fun dispatchAction(action : UiAction)
    abstract val uiState : VMState
}