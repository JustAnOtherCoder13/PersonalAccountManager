package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.model.entity.testCategory
import com.piconemarc.viewmodel.viewModel.BaseScreenModel
import com.piconemarc.viewmodel.viewModel.PAMUiEvent
import com.piconemarc.viewmodel.viewModel.PAMUiState

internal val addOperationPopUpState_: MutableState<AddOperationPopUpState> =
    mutableStateOf(AddOperationPopUpState.Idle)

internal val addOperationPopUpIconMenuState_: MutableState<AddOperationPopUpState.AddOperationPopUpMenuIconState> =
    mutableStateOf(AddOperationPopUpState.AddOperationPopUpMenuIconState.Operation)

internal val addOperationRecurrentOptionState_: MutableState<AddOperationPopUpState.RecurrentSwitchButtonState> =
    mutableStateOf(AddOperationPopUpState.RecurrentSwitchButtonState.Punctual)


internal val selectedCategory_: MutableState<CategoryModel> =
    mutableStateOf(CategoryModel("Category"))

internal val operationName_: MutableState<String> =
    mutableStateOf("")

internal val operationAmount_: MutableState<String> =
    mutableStateOf("")

internal val endDateMonth_ : MutableState<String> =
    mutableStateOf("Month")

internal val endDateYear_ : MutableState<String> =
    mutableStateOf("Year")

internal val senderAccount_ : MutableState<String> =
    mutableStateOf("Sender Account")

internal val beneficiaryAccount_ : MutableState<String> =
    mutableStateOf("Beneficiary Account")


class AddOperationPopUpScreenModel : BaseScreenModel() {
    override val currentState: PAMUiState = getState()
    override val getTargetState: (PAMUiState) -> Unit =
        { addOperationPopUpState_.value = it as AddOperationPopUpState }

    //------------------------Event handler------------------------------------
    override fun onUiEvent(
        event: PAMUiEvent,
        runBefore: () -> Unit,
        runAfter: () -> Unit
    ) {
        //recurrentOptionState
        if (getState().recurrentSwitchButtonState == event.source) {
            runBefore()
            addOperationRecurrentOptionState_.value =
                event.target as AddOperationPopUpState.RecurrentSwitchButtonState
            runAfter()
        }
        //menuIconState
        if (getState().menuIconPopUpState == event.source) {
            runBefore()
            addOperationPopUpIconMenuState_.value =
                event.target as AddOperationPopUpState.AddOperationPopUpMenuIconState
            runAfter()
        }
        //popUpState
        if (getState() == event.source) {
            runBefore()
            addOperationPopUpState_.value =
                event.target as AddOperationPopUpState
            runAfter()
        }
    }

    //---------------------------------------popUp action--------------------------------------
    fun expand() {
        onUiEvent(AddOperationPopUpEvent.OnExpand)
    }

    fun closeAddPopUp() {
        onUiEvent(
            event = AddOperationPopUpEvent.OnClose,
            runBefore = {
                resetStates()
            }
        )
    }

    fun addOperation() {
        onUiEvent(
            event = AddOperationPopUpEvent.OnClose,
            runBefore = {
                resetStates()
            }
        )
    }

    //-------------------------------icon menu option action-------------------------------------
    fun closeOption() {
        onUiEvent(
            runBefore = {
                addOperationRecurrentOptionState_.value =
                    AddOperationPopUpState.RecurrentSwitchButtonState.Punctual
            },
            event = if (getState().menuIconPopUpState == AddOperationPopUpState.AddOperationPopUpMenuIconState.Transfer) {
                AddOperationPopUpEvent.AddOperationPopUpMenuIconEvent.OnCloseAllOptions
            } else {
                AddOperationPopUpEvent.AddOperationPopUpMenuIconEvent.OnPaymentOptionCollapse
            }
        )
    }

    fun openPaymentOption() {
        onUiEvent(
            event = if (getState().menuIconPopUpState == AddOperationPopUpState.AddOperationPopUpMenuIconState.Transfer) {
                AddOperationPopUpEvent.AddOperationPopUpMenuIconEvent.OnTransferOptionCollapse
            } else {
                AddOperationPopUpEvent.AddOperationPopUpMenuIconEvent.OnPaymentOptionRequire
            }
        )
    }

    fun openTransferOption() {
        onUiEvent(
            event = if (getState().menuIconPopUpState == AddOperationPopUpState.AddOperationPopUpMenuIconState.Operation) {
                AddOperationPopUpEvent.AddOperationPopUpMenuIconEvent.OnOpenAllOptions
            } else {
                AddOperationPopUpEvent.AddOperationPopUpMenuIconEvent.OnTransferOptionRequire
            }
        )
    }

    //------------------------------------basic operation action-----------------------
    fun selectCategory(categoryName: String) {
        selectedCategory_.value = testCategory.filter { it.name == categoryName }[0]
    }

    fun enterOperationName(name: String) {
        operationName_.value = name
    }

    fun enterAmountValue(amount: String) {
        operationAmount_.value = amount
    }

    //---------------------------------payment option actions----------------------------
    fun selectPunctualOption() {
        onUiEvent(
            event = AddOperationPopUpEvent.RecurrentSwitchButtonEvent.OnPunctualOptionSelected
        )
    }

    fun selectRecurrentOption() {
        onUiEvent(
            event = AddOperationPopUpEvent.RecurrentSwitchButtonEvent.OnRecurrentOptionSelected
        )
    }

    fun selectEndDateMonth(month : String) {
        endDateMonth_.value = month
    }

    fun selectEndDateYear(year : String) {
        endDateYear_.value = year
    }

    //---------------------------Transfer option action-------------------------------------

    fun selectSenderAccount(senderAccount : String) {
        senderAccount_.value = senderAccount
    }

    fun selectBeneficiaryAccount(beneficiaryAccount :  String) {
        beneficiaryAccount_.value = beneficiaryAccount
    }

    override fun getState(): AddOperationPopUpState = addOperationPopUpState_.value
}

//---------------------------------------Helpers-----------------------------------
private fun resetStates() {
    selectedCategory_.value = CategoryModel("Category")
    operationName_.value = ""
    operationAmount_.value = ""
    addOperationPopUpIconMenuState_.value =
        AddOperationPopUpState.AddOperationPopUpMenuIconState.Operation
    addOperationRecurrentOptionState_.value =
        AddOperationPopUpState.RecurrentSwitchButtonState.Punctual
    endDateMonth_.value = "Month"
    endDateYear_.value = "Year"
    senderAccount_.value = "Sender Account"
    beneficiaryAccount_.value = "Beneficiary Account"
}