package com.piconemarc.viewmodel.viewModel

abstract class BaseScreenModel() {

    abstract fun getState(): PAMUiState

    abstract fun onUiEvent(event: PAMUiEvent,
                           runBefore: () -> Unit={},
                           runAfter: () -> Unit={})
}