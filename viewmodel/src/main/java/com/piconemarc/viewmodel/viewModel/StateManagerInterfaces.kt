package com.piconemarc.viewmodel.viewModel

import com.piconemarc.core.domain.PAMUiState


interface PAMUiDataAnimation

interface PAMUiAction

typealias Reducer <S> = (S, PAMUiAction) -> S
typealias StoreSubscriber <S> = (S) -> Unit

interface Store<S : PAMUiState> {
    fun dispatch(action: PAMUiAction)
    fun add(subscriber: StoreSubscriber<S>): Boolean
    fun remove(subscriber: StoreSubscriber<S>): Boolean
}

class DefaultStore<S : PAMUiState>(
    initialState: S,
    private val reducer: Reducer<S>
) : Store<S> {

    private val subscribers = mutableSetOf<StoreSubscriber<S>>()

    private var state: S = initialState
        set(value) {
            field = value
            subscribers.forEach { it(value) }
        }

    override fun dispatch(action: PAMUiAction) {
        state = reducer(state, action)
    }

    override fun add(subscriber: StoreSubscriber<S>): Boolean = subscribers.add(subscriber)

    override fun remove(subscriber: StoreSubscriber<S>): Boolean = subscribers.remove(subscriber)
}


interface PAMUiEvent {


}