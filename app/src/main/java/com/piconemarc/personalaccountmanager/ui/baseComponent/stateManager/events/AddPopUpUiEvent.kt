package com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.events

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.personalaccountmanager.MainActivity
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.UiEvent
import com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.UiState
import com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.states.UiStates

val popUpBeneficiarySelectedAccount: MutableState<String> =
    mutableStateOf(MainActivity.applicationContext().getString(R.string.beneficiaryAccount))
val popUpCategory: MutableState<String> = mutableStateOf(MainActivity.applicationContext().getString(R.string.category))
val popUpOperationAmount: MutableState<String> = mutableStateOf("")
val popUpOperationName: MutableState<String> = mutableStateOf("")
val popUpSenderSelectedAccount: MutableState<String> =
    mutableStateOf(MainActivity.applicationContext().getString(R.string.senderAccount))
val recurrentSwitchButtonState: MutableState<UiStates.SwitchButtonState> = mutableStateOf(UiStates.SwitchButtonState.PUNCTUAL)
val popUpOperationType: MutableState<Int> =
    mutableStateOf(UiStates.AddOperationPopUpLeftSideIconState.OPERATION.getIcon().getIconName())
val addOperationPopUpState: MutableState<UiStates.AddOperationPopUpState> =
    mutableStateOf(UiStates.AddOperationPopUpState.COLLAPSED)
val popUpSelectedMonth: MutableState<String> =
    mutableStateOf(MainActivity.applicationContext().getString(R.string.month))
val popUpSelectedYear : MutableState<String> =
    mutableStateOf(MainActivity.applicationContext().getString(R.string.year))
val leftSideIconState : MutableState<UiStates.AddOperationPopUpLeftSideIconState> =
    mutableStateOf(UiStates.AddOperationPopUpLeftSideIconState.OPERATION)


var popUpTitle : MutableState<String> = mutableStateOf("")

object PopUpTitleStates : UiState {
    class PopUpTitle{
        val addOperationPopUpTitleState by popUpTitle
    }
}


object AddPopUpUiEvent : UiEvent {
    object InitPopUp : UiEvent
    object OnDismiss : UiEvent
    object OnAddOperation : UiEvent
    data class OnLeftSideIconButtonClicked(val operationType: UiStates.AddOperationPopUpLeftSideIconState) :
        UiEvent
    data class OnCategorySelected(val selectedCategory: String) : UiEvent
    data class OnEnterOperationName(val operationName: String) : UiEvent
    data class OnEnterOperationAmount(val operationAmount: String) : UiEvent
    data class RecurrentSwitchButtonState(val recurrentSwitchButtonState: UiStates.SwitchButtonState) :
        UiEvent
    data class OnMonthSelected(val selectedMonth: String) : UiEvent
    data class OnYearSelected(val selectedYear: String) : UiEvent
    data class OnSenderAccountSelected(val senderAccount: String) : UiEvent
    data class OnBeneficiaryAccountSelected(val beneficiaryAccount: String) : UiEvent
}

fun popUpEventHandler(event: UiEvent) {
    when (event) {
        is AddPopUpUiEvent.InitPopUp -> {
            initPopUp()
        }
        is AddPopUpUiEvent.OnDismiss -> {
            dismissPopUp()
        }
        is AddPopUpUiEvent.OnAddOperation -> {
            addOperation()
        }
        is AddPopUpUiEvent.OnLeftSideIconButtonClicked -> {
            updateLeftSideIconType(event.operationType)
        }
        is AddPopUpUiEvent.OnCategorySelected -> {
            updateSelectedCategory(event.selectedCategory)
        }
        is AddPopUpUiEvent.OnEnterOperationName -> {
            updateOperationName(event.operationName)
        }
        is AddPopUpUiEvent.OnEnterOperationAmount -> {
            updateOperationAmount(event.operationAmount)
        }
        is AddPopUpUiEvent.RecurrentSwitchButtonState -> {
            updateIsRecurrent(event.recurrentSwitchButtonState)
        }
        is AddPopUpUiEvent.OnMonthSelected -> {
            updateMonth(event.selectedMonth)
        }
        is AddPopUpUiEvent.OnYearSelected -> {
            updateYear(event.selectedYear)
        }
        is AddPopUpUiEvent.OnSenderAccountSelected -> {
            updateSenderAccount(event.senderAccount)
        }
        is AddPopUpUiEvent.OnBeneficiaryAccountSelected -> {
            updateBeneficiaryAccount(event.beneficiaryAccount)
        }
    }
}

private fun initPopUp() {
    addOperationPopUpState.value = UiStates.AddOperationPopUpState.EXPANDED
    popUpBeneficiarySelectedAccount.value = MainActivity.applicationContext().getString(R.string.beneficiaryAccount)
    popUpCategory.value = MainActivity.applicationContext().getString(R.string.category)
    popUpOperationAmount.value = ""
    popUpOperationName.value = ""
    popUpSenderSelectedAccount.value = MainActivity.applicationContext().getString(R.string.senderAccount)
    recurrentSwitchButtonState.value = UiStates.SwitchButtonState.PUNCTUAL
    popUpOperationType.value = UiStates.AddOperationPopUpLeftSideIconState.OPERATION.getIcon().getIconName()
    popUpSelectedMonth.value =  MainActivity.applicationContext().getString(R.string.month)
    popUpSelectedYear.value = MainActivity.applicationContext().getString(R.string.year)
    popUpTitle.value = MainActivity.applicationContext().getString(UiStates.AddOperationPopUpLeftSideIconState.OPERATION.getIcon().getIconName())
}

private fun dismissPopUp() {
    addOperationPopUpState.value = UiStates.AddOperationPopUpState.COLLAPSED
}

private fun addOperation() {
    addOperationPopUpState.value = UiStates.AddOperationPopUpState.COLLAPSED
}

private fun updateLeftSideIconType(operationType: UiStates.AddOperationPopUpLeftSideIconState) {
    popUpOperationType.value = operationType.getIcon().getIconName()
    leftSideIconState.value = operationType
    popUpTitle.value = MainActivity.applicationContext().getString(operationType.getIcon().getIconName())
}

private fun updateSelectedCategory(selectedCategory: String) {
    popUpCategory.value = selectedCategory
}

private fun updateOperationName(operationName: String) {
    popUpOperationName.value = operationName
}

private fun updateOperationAmount(operationAmount: String) {
    popUpOperationAmount.value = operationAmount
}

private fun updateIsRecurrent(recurrentSwitchButtonState_: UiStates.SwitchButtonState) {
    recurrentSwitchButtonState.value = recurrentSwitchButtonState_
}

private fun updateMonth(selectedMonth: String) {
    popUpSelectedMonth.value = selectedMonth
}

private fun updateYear(selectedYear: String) {
    popUpSelectedYear.value = selectedYear
}

private fun updateSenderAccount(senderAccount: String) {
    popUpSenderSelectedAccount.value = senderAccount
}

private fun updateBeneficiaryAccount(beneficiaryAccount: String) {
    popUpBeneficiarySelectedAccount.value = beneficiaryAccount
}