package com.piconemarc.viewmodel.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


interface UiDataAnimation

interface UiAction

interface UiState

typealias Reducer <S> = (S, UiAction) -> S
typealias StoreSubscriber <S> = (S) -> Unit

interface Store<S : UiState> {
    fun dispatch(action: UiAction)
    fun add(subscriber: StoreSubscriber<S>): Boolean
    fun remove(subscriber: StoreSubscriber<S>): Boolean
}

class DefaultStore<S : UiState>(
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


interface ActionDispatcher<A : UiAction, S : UiState> : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default
    val scope: CoroutineScope
        get() = CoroutineScope(coroutineContext)
    val store: DefaultStore<S>

    fun dispatchAction(action: A)

    fun addSubscriber() = store.add(subscriber)
    fun removeSubscriber() = store.remove(subscriber)

    val subscriber: StoreSubscriber<S>
}

interface ReducerModule<S : UiState>{
    val provideStateReducer : Reducer<S>
    fun provideState () : S
    fun provideStore (state : S): DefaultStore<S>
}

abstract class BaseScreenViewModel : ViewModel(){
    abstract fun dispatchAction(action : UiAction)
}