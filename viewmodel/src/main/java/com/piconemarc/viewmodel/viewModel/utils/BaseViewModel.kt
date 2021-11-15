package com.piconemarc.viewmodel.viewModel.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.appStoreSubscriber
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<A : UiAction, S : VMState>(
    private val store: DefaultStore<GlobalVmState>,
    val state: MutableStateFlow<S>
) : ViewModel() {

    private val subscriber: StoreSubscriber<GlobalVmState> = appStoreSubscriber

    init {
        store.add(subscriber)
    }


    fun updateState(vararg action: GlobalAction) {
        action.forEach {
            store.dispatch(it)
        }
    }

    abstract fun dispatchAction(action: A)

    val uiState : MutableState<S> = mutableStateOf(state.value)
}
