package com.piconemarc.viewmodel.viewModel

import com.piconemarc.core.domain.PAMUiState


interface PAMUiDataAnimation

interface UiAction

typealias Reducer <S> = (S, UiAction) -> S
typealias StoreSubscriber <S> = (S) -> Unit

interface Store<S : PAMUiState> {
    fun dispatch(action: UiAction)
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

    override fun dispatch(action: UiAction) {
        state = reducer(state, action)
    }

    override fun add(subscriber: StoreSubscriber<S>): Boolean = subscribers.add(subscriber)

    override fun remove(subscriber: StoreSubscriber<S>): Boolean = subscribers.remove(subscriber)
}


interface PAMUiEvent {


}