package com.piconemarc.personalaccountmanager.newUi.stateManager.deletePopUp

import com.piconemarc.personalaccountmanager.newUi.stateManager.PAMUiEvent

object DeleteOperationPopUpEvents : PAMUiEvent {
    object OnInit : PAMUiEvent
    object OnDeleteOperation : PAMUiEvent
    object OnDismiss : PAMUiEvent
}

fun deleteOperationEventHandler(event: PAMUiEvent) {
    when (event) {
        is DeleteOperationPopUpEvents.OnInit -> onInit()
        is DeleteOperationPopUpEvents.OnDismiss -> onDismiss()
        is DeleteOperationPopUpEvents.OnDeleteOperation -> onDeleteOperation()
    }
}

private fun onInit() {
    deleteOperationPopUpState_.value = DeleteOperationPopUpState.EXPAND
}
private fun onDismiss(){
    deleteOperationPopUpState_.value = DeleteOperationPopUpState.COLLAPSE
}
private fun onDeleteOperation(){
    deleteOperationPopUpState_.value = DeleteOperationPopUpState.COLLAPSE
}