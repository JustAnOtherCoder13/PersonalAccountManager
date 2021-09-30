package com.piconemarc.viewmodel.viewModel

abstract class BaseScreenModel( ) {
    protected abstract val currentState: PAMUiState
    protected abstract val getTargetState: (PAMUiState) -> Unit
    abstract fun getState(): PAMUiState

    protected fun onUiEvent(
        event: PAMUiEvent,
        runBefore: () -> Unit = {},
        runAfter: () -> Unit = {}
    ) {
        if (currentState == event.source) {
            runBefore()
            getTargetState(event.target)
            runAfter()
        }
    }
}