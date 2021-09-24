package com.piconemarc.personalaccountmanager.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.personalaccountmanager.MainActivity
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.UiEvent
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.addOperationPopUp.AddOperationPopUpState

val popUpBeneficiarySelectedAccount: MutableState<String> =
    mutableStateOf(MainActivity.applicationContext().getString(R.string.beneficiaryAccount))
val popUpCategory: MutableState<String> = mutableStateOf(MainActivity.applicationContext().getString(R.string.category))
val popUpOperationAmount: MutableState<String> = mutableStateOf("")
val popUpOperationName: MutableState<String> = mutableStateOf("")
val popUpSenderSelectedAccount: MutableState<String> =
    mutableStateOf(MainActivity.applicationContext().getString(R.string.senderAccount))
val popUpIsRecurrent: MutableState<Boolean> = mutableStateOf(false)
val popUpOperationType: MutableState<Int> =
    mutableStateOf(IconButtons.OPERATION.getIconName())
val addOperationPopUpState: MutableState<AddOperationPopUpState> =
    mutableStateOf(AddOperationPopUpState.COLLAPSED)
val popUpSelectedMonth: MutableState<String> =
    mutableStateOf(MainActivity.applicationContext().getString(R.string.month))
val popUpSelectedYear : MutableState<String> =
    mutableStateOf(MainActivity.applicationContext().getString(R.string.year))

object AddPopUpUiEvent : UiEvent {
    object InitPopUp : UiEvent
    object OnDismiss : UiEvent
    object OnAddOperation : UiEvent
    data class OnLeftSideIconButtonClicked(val operationType: Int) : UiEvent
    data class OnCategorySelected(val selectedCategory: String) : UiEvent
    data class OnEnterOperationName(val operationName: String) : UiEvent
    data class OnEnterOperationAmount(val operationAmount: String) : UiEvent
    data class OnRecurrentOrPunctualSwitched(val isRecurrent: Boolean) : UiEvent
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
        is AddPopUpUiEvent.OnRecurrentOrPunctualSwitched -> {
            updateIsRecurrent(event.isRecurrent)
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
    addOperationPopUpState.value = AddOperationPopUpState.EXPANDED
    popUpBeneficiarySelectedAccount.value = MainActivity.applicationContext().getString(R.string.beneficiaryAccount)
    popUpCategory.value = MainActivity.applicationContext().getString(R.string.category)
    popUpOperationAmount.value = ""
    popUpOperationName.value = ""
    popUpSenderSelectedAccount.value = MainActivity.applicationContext().getString(R.string.senderAccount)
    popUpIsRecurrent.value = false
    popUpOperationType.value = IconButtons.OPERATION.getIconName()
    popUpSelectedMonth.value =  MainActivity.applicationContext().getString(R.string.month)
    popUpSelectedYear.value = MainActivity.applicationContext().getString(R.string.year)
}

private fun dismissPopUp() {
    addOperationPopUpState.value = AddOperationPopUpState.COLLAPSED
}

private fun addOperation() {
    addOperationPopUpState.value = AddOperationPopUpState.COLLAPSED
}

private fun updateLeftSideIconType(operationType: Int) {
    popUpOperationType.value = operationType
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

private fun updateIsRecurrent(isRecurrent: Boolean) {
    popUpIsRecurrent.value = isRecurrent
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