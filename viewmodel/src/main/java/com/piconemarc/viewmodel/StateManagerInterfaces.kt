package com.piconemarc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


interface UiDataAnimation

interface UiAction

interface VMState

interface UiState

typealias Reducer <S> = (S, UiAction) -> S
typealias StoreSubscriber <S> = (S) -> Unit

interface Store<S : VMState> {
    fun dispatch(action: UiAction)
    fun add(subscriber: StoreSubscriber<S>): Boolean
    fun remove(subscriber: StoreSubscriber<S>): Boolean
}

class DefaultStore<S : VMState>(
    initialState: S,
    private val reducer: Reducer<S>
) : Store<S> {
    private val subscribers = mutableSetOf<StoreSubscriber<S>>()
    private var state: S = initialState
        set(value) {
            field = value
            subscribers.forEach { it(value) }
        }
    override fun dispatch(action: UiAction) {
        state = reducer(state, action)
    }
    override fun add(subscriber: StoreSubscriber<S>): Boolean = subscribers.add(subscriber)
    override fun remove(subscriber: StoreSubscriber<S>): Boolean = subscribers.remove(subscriber)
}

abstract class ActionDispatcher<A : UiAction, S : VMState> : ViewModel(){

    val scope: CoroutineScope = viewModelScope
    abstract val store: DefaultStore<S>
    abstract fun dispatchAction(action: A)

    fun addSubscriber() = store.add(subscriber)
    fun removeSubscriber() = store.remove(subscriber)

    abstract val subscriber: StoreSubscriber<S>
}

interface ComponentActionDispatcher : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default
    val store: DefaultStore<GlobalVmState>
    val coroutineScope : CoroutineScope
        get() = CoroutineScope(coroutineContext)
    fun dispatchAction(action: UiAction, scope: CoroutineScope)
    fun updateState(vararg action: GlobalAction) {
        action.forEach {
            store.dispatch(it)
        }
    }

}