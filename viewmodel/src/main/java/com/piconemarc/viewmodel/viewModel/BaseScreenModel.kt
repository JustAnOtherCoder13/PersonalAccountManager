package com.piconemarc.viewmodel.viewModel

import com.piconemarc.core.domain.PAMUiState

abstract class BaseScreenModel() {

    abstract fun getState(): PAMUiState

    abstract fun onUiEvent(event: PAMUiEvent,
                           runBefore: () -> Unit={},
                           runAfter: () -> Unit={})
}