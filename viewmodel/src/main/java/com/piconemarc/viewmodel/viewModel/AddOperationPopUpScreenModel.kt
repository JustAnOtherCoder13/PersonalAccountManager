package com.piconemarc.viewmodel.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.model.entity.OperationModel
import com.piconemarc.model.entity.testCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

private val addOperationPopUpState: MutableState<AddOperationPopUpState> =
    mutableStateOf(AddOperationPopUpState.Idle)
private val selectedCategory: MutableState<CategoryModel> =
    mutableStateOf(CategoryModel("Category"))

class AddOperationPopUpScreenModel : BaseScreenModel() {
    override val currentState: PAMUiState = getState()
    override val getTargetState: (PAMUiState) -> Unit =
        { addOperationPopUpState.value = it as AddOperationPopUpState }

    fun expand() {
        onUiEvent(
            AddOperationPopUpEvent.OnExpand,
        )
    }

    fun closeOption() {
        onUiEvent(
            event = if (addOperationPopUpState.value == AddOperationPopUpState.Transfer) {
                AddOperationPopUpEvent.OnCloseAllOptions
            } else {
                AddOperationPopUpEvent.OnPaymentOptionCollapse
            }
        )
    }

    fun openPaymentOption() {
        onUiEvent(
            event = if (addOperationPopUpState.value == AddOperationPopUpState.Transfer) {
                AddOperationPopUpEvent.OnTransferOptionCollapse
            } else {
                AddOperationPopUpEvent.OnPaymentOptionRequire
            }
        )
    }

    fun openTransferOption() {
        onUiEvent(
            event = if (addOperationPopUpState.value == AddOperationPopUpState.Operation) {
                AddOperationPopUpEvent.OnOpenAllOptions
            } else {
                AddOperationPopUpEvent.OnTransferOptionRequire
            }
        )
    }

    fun closeAddPopUp() {
        onUiEvent(
            event = when(getState()){
                AddOperationPopUpState.Transfer -> AddOperationPopUpEvent.CloseOnTransfer
                AddOperationPopUpState.Payment -> AddOperationPopUpEvent.CloseOnPayment
                else -> AddOperationPopUpEvent.OnClose
            } ,
            runBefore = { selectedCategory.value = CategoryModel("Category") }
        )
    }

    fun addOperation() {
        onUiEvent(
            event = when(getState()){
                AddOperationPopUpState.Transfer -> AddOperationPopUpEvent.CloseOnTransfer
                AddOperationPopUpState.Payment -> AddOperationPopUpEvent.CloseOnPayment
                else -> AddOperationPopUpEvent.OnClose
            } ,
            runBefore = {
                //todo add operation here
                selectedCategory.value = CategoryModel("Category")
                //Toast.makeText(MainActivity.applicationContext(),"Add operation : ${operation.category}",Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun selectCategory(categoryName: String) {
        selectedCategory.value = testCategory.filter { it.name == categoryName }[0]
    }

    override fun getState(): AddOperationPopUpState = addOperationPopUpState.value
}

//----------------------------STATE-------------------------------------------------------------------
private fun mapAllCategoriesToStringList(): List<String> {
    val allCategories: MutableList<String> = mutableListOf()
    testCategory.forEachIndexed { _, categoryModel -> allCategories.add(categoryModel.name) }
    return allCategories
}

sealed class AddOperationPopUpState : PAMUiState {
    open val isExpanded = false
    open val popUpTitle = "MainActivity.applicationContext().getString(R.string.category)"
    open val operation = OperationModel()
    open val category by selectedCategory
    val allCategories: List<String> = mapAllCategoriesToStringList()


    object Idle : AddOperationPopUpState() {
        override val category = CategoryModel()
    }

    object Operation : AddOperationPopUpState() {
        override val isExpanded: Boolean = true
    }

    object Payment : AddOperationPopUpState() {
        override val isExpanded: Boolean = true
        override val popUpTitle: String =
            "MainActivity.applicationContext().getString(R.string.payment)"
    }

    object Transfer : AddOperationPopUpState() {
        override val isExpanded: Boolean = true
        override val popUpTitle: String =
            "MainActivity.applicationContext().getString(R.string.transfer)"
    }

}

//------------------------------------EVENT----------------------------------------------------------

sealed class AddOperationPopUpEvent(
    override val source: PAMUiState,
    override val target: PAMUiState
) : PAMUiEvent {

    object OnExpand : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Idle,
        target = AddOperationPopUpState.Operation
    )

    object OnClose : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Operation,
        target = AddOperationPopUpState.Idle
    )

    object CloseOnTransfer : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Transfer,
        target = AddOperationPopUpState.Idle
    )

    object CloseOnPayment : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Payment,
        target = AddOperationPopUpState.Idle
    )

    object OnPaymentOptionCollapse : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Payment,
        target = AddOperationPopUpState.Operation
    )

    object OnPaymentOptionRequire : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Operation,
        target = AddOperationPopUpState.Payment
    )

    object OnTransferOptionRequire : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Payment,
        target = AddOperationPopUpState.Transfer
    )

    object OnTransferOptionCollapse : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Transfer,
        target = AddOperationPopUpState.Payment
    )

    object OnCloseAllOptions : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Transfer,
        target = AddOperationPopUpState.Operation
    )

    object OnOpenAllOptions : AddOperationPopUpEvent(
        source = AddOperationPopUpState.Operation,
        target = AddOperationPopUpState.Transfer
    )
}

