package com.piconemarc.viewmodel.viewModel

abstract class BaseScreenModel() {
    protected abstract val currentState: PAMUiState
    protected abstract val getTargetState: (PAMUiState) -> Unit
    abstract fun getState(): PAMUiState

    abstract fun onUiEvent(event: PAMUiEvent,
                           runBefore: () -> Unit={},
                           runAfter: () -> Unit={})
}