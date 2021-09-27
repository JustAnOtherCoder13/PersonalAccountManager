package com.piconemarc.personalaccountmanager.newUi.stateManager.deletePopUp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.personalaccountmanager.newUi.stateManager.PAMUiState

internal val deleteOperationPopUpState_: MutableState<DeleteOperationPopUpState> =
    mutableStateOf(DeleteOperationPopUpState.COLLAPSE)
internal val operationToDeleteName_: MutableState<String> =
    mutableStateOf("test")
internal val operationToDeleteAmount_ : MutableState<Double> =
    mutableStateOf(0.0)

object DeleteOperationPopUpStates : PAMUiState {
    val deleteOperationPopUpState by deleteOperationPopUpState_
    val operationToDeleteName by operationToDeleteName_
    val operationToDeleteAmount by operationToDeleteAmount_
}

enum class DeleteOperationPopUpState {
    EXPAND,
    COLLAPSE
}