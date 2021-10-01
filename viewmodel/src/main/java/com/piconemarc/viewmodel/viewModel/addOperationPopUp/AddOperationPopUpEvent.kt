package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import com.piconemarc.viewmodel.viewModel.PAMUiEvent
import com.piconemarc.viewmodel.viewModel.PAMUiState

sealed class AddOperationPopUpEvent(
    override val source: PAMUiState,
    override val target: PAMUiState
) : PAMUiEvent {

    object OnExpand : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Idle,
        target = AddOperationPopUpState.Expand
    )

    object OnClose : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Expand,
        target = AddOperationPopUpState.Idle
    )

    sealed class AddOperationPopUpMenuIconEvent : PAMUiEvent {

        object OnPaymentOptionCollapse : AddOperationPopUpEvent(
            source = AddOperationPopUpState.AddOperationPopUpMenuIconState.Payment,
            target = AddOperationPopUpState.AddOperationPopUpMenuIconState.Operation
        )

        object OnPaymentOptionRequire : AddOperationPopUpEvent(
            source = AddOperationPopUpState.AddOperationPopUpMenuIconState.Operation,
            target = AddOperationPopUpState.AddOperationPopUpMenuIconState.Payment
        )

        object OnTransferOptionRequire : AddOperationPopUpEvent(
            source = AddOperationPopUpState.AddOperationPopUpMenuIconState.Payment,
            target = AddOperationPopUpState.AddOperationPopUpMenuIconState.Transfer
        )

        object OnTransferOptionCollapse : AddOperationPopUpEvent(
            source = AddOperationPopUpState.AddOperationPopUpMenuIconState.Transfer,
            target = AddOperationPopUpState.AddOperationPopUpMenuIconState.Payment
        )

        object OnCloseAllOptions : AddOperationPopUpEvent(
            source = AddOperationPopUpState.AddOperationPopUpMenuIconState.Transfer,
            target = AddOperationPopUpState.AddOperationPopUpMenuIconState.Operation
        )

        object OnOpenAllOptions : AddOperationPopUpEvent(
            source = AddOperationPopUpState.AddOperationPopUpMenuIconState.Operation,
            target = AddOperationPopUpState.AddOperationPopUpMenuIconState.Transfer
        )
    }
    sealed class RecurrentSwitchButtonEvent{

        object OnRecurrentOptionSelected : AddOperationPopUpEvent(
            source = AddOperationPopUpState.RecurrentSwitchButtonState.Punctual,
            target = AddOperationPopUpState.RecurrentSwitchButtonState.Recurrent
        )
        object OnPunctualOptionSelected : AddOperationPopUpEvent(
            source = AddOperationPopUpState.RecurrentSwitchButtonState.Recurrent,
            target = AddOperationPopUpState.RecurrentSwitchButtonState.Punctual
        )
    }
}