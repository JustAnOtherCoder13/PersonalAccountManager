package com.piconemarc.core.domain

import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.CategoryModel

interface PAMUiState

data class AddOperationPopUpOperationOptionState(
    val isPopUpExpanded: Boolean = false,
    val operationCategories: List<CategoryModel> = listOf(),
    val selectedCategory: String = "",
    val operationName: String = "",
    val operationAmount: String = "",
) : PAMUiState

data class AddOperationPopUpPaymentOptionState(
    val isPaymentExpanded: Boolean = false,
    val isRecurrentOptionExpanded: Boolean = false,
    val enDateSelectedMonth: String = "",
    val endDateSelectedYear: String = ""
) : PAMUiState

data class AddOperationPopUpTransferOptionState(
    val isTransferExpanded: Boolean = false,
    val accountList: List<AccountModel> = listOf(),
    val senderAccount: String = "",
    val beneficiaryAccount: String = ""
) : PAMUiState

data class AddOperationPopUpGlobalState(
    val addOperationPopUpOperationOptionState: AddOperationPopUpOperationOptionState = AddOperationPopUpOperationOptionState(),
    val addOperationPopUpPaymentOptionState: AddOperationPopUpPaymentOptionState = AddOperationPopUpPaymentOptionState(),
    val addOperationPopUpTransferOptionState: AddOperationPopUpTransferOptionState = AddOperationPopUpTransferOptionState()
) : PAMUiState
