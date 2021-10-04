package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import androidx.compose.runtime.getValue
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.model.entity.OperationModel
import com.piconemarc.model.entity.testCategory
import com.piconemarc.viewmodel.viewModel.PAMUiState

sealed class AddOperationPopUpState : PAMUiState {
    open val isExpanded = false
    open val isPaymentExpanded = false
    open val isTransferExpanded = false
    open val isRecurrentOptionExpanded = false
    open val popUpTitle = "Operation"
    open val operation = OperationModel()
    open val category by selectedCategory_
    open val operationName by operationName_
    open val operationAmount by operationAmount_
    open val menuIconPopUpState by addOperationPopUpIconMenuState_
    open val recurrentSwitchButtonState by addOperationRecurrentOptionState_
    open val endDateMonth by endDateMonth_
    open val endDateYear by endDateYear_
    open val senderAccount by senderAccount_
    open val beneficiaryAccount by beneficiaryAccount_
    val allCategories: List<String> = mapAllCategoriesToStringList()
    open val menuIconSelectorPosition = 0f


    object Idle : AddOperationPopUpState() {
        override val category = CategoryModel()
        override val operationName = ""
        override val operationAmount = ""
        override val menuIconPopUpState = AddOperationPopUpMenuIconState.Operation
        override val recurrentSwitchButtonState = RecurrentSwitchButtonState.Punctual
        override val endDateMonth = ""
        override val endDateYear = ""
        override val senderAccount = ""
        override val beneficiaryAccount = ""
    }

    object Expand : AddOperationPopUpState() {
        override val isExpanded = true
    }

    sealed class AddOperationPopUpMenuIconState : AddOperationPopUpState() {
        override val isExpanded = true

        object Operation : AddOperationPopUpMenuIconState() {
        }

        object Payment : AddOperationPopUpMenuIconState() {
            override val popUpTitle: String = "Payment"
            override val isPaymentExpanded: Boolean = true
            override val menuIconSelectorPosition = 58f
        }

        object Transfer : AddOperationPopUpMenuIconState() {
            override val popUpTitle: String = "Transfer"
            override val isPaymentExpanded: Boolean = true
            override val isTransferExpanded: Boolean = true
            override val menuIconSelectorPosition = 116f
        }
    }

    sealed class RecurrentSwitchButtonState : AddOperationPopUpState() {
        override val isExpanded = true

        object Recurrent : RecurrentSwitchButtonState(){
            override val isRecurrentOptionExpanded = true
        }

        object Punctual : RecurrentSwitchButtonState()
    }
}

private fun mapAllCategoriesToStringList(): List<String> {
    val allCategories: MutableList<String> = mutableListOf()
    testCategory.forEachIndexed { _, categoryModel -> allCategories.add(categoryModel.name) }
    return allCategories
}
