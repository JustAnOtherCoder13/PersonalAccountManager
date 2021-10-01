package com.piconemarc.viewmodel.viewModel

interface PAMUiState

interface PAMUiDataAnimation

interface PAMUiEvent {
    val source: PAMUiState
    val target: PAMUiState
}