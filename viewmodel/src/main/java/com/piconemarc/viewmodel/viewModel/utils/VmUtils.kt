package com.piconemarc.viewmodel.viewModel.utils

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.compose.runtime.MutableState
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

interface UiDataAnimation

interface UiAction

interface VMState

interface PopUpVmState : VMState {
    val isVisible : Boolean
}

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

interface ActionDispatcher<S:VMState> {

    val store: DefaultStore<GlobalVmState>
    val state : MutableStateFlow<S>
    val uiState : MutableState<S>

    fun dispatchAction(action: UiAction, scope: CoroutineScope)

    fun updateState(vararg action: GlobalAction) {
        action.forEach {
            store.dispatch(it)
        }
    }
}

fun CoroutineScope.launchUnconfinedCatchingError(
    block : suspend CoroutineScope.() -> Unit,
    doOnSuccess : ()-> Unit = {},
    doOnError : ()-> Unit = {},
) : Job {
    return this.launch(Dispatchers.Unconfined) {
        try {
            block()
        }catch (e : SQLiteException){
            Log.e(this::class.java.simpleName, "dispatchEvent: ", e)
            doOnError()
        }
        doOnSuccess()
    }
}

fun CoroutineScope.launchOnIOCatchingError(
    block : suspend CoroutineScope.() -> Unit,
    doOnSuccess : ()-> Unit = {},
    doOnError : ()-> Unit = {},
) : Job {
    return this.launch(Dispatchers.IO) {
        try {
            block()
        }catch (e : SQLiteException){
            Log.e(this::class.java.simpleName, "dispatchEvent: ", e)
            doOnError()
        }
        doOnSuccess()
    }
}