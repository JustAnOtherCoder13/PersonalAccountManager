package com.piconemarc.personalaccountmanager.newUi.stateManager

interface PAMUiState

interface PAMUiDataAnimation

interface PAMUiEvent{
    val source : PAMUiState
    val target : PAMUiState
}

fun  onUiEvent(
    currentState : PAMUiState,
    getMutableState : (PAMUiState)->Unit,
    event_: PAMUiEvent,
    runBefore: () -> Unit = {},
    runAfter: () -> Unit = {}
) {
    if (currentState == event_.source) {
        runBefore()
        getMutableState(event_.target)
        runAfter()
    }
}