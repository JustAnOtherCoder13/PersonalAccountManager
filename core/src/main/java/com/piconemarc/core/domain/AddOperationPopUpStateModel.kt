package com.piconemarc.core.domain

import com.piconemarc.model.entity.PresentationDataModel

interface PAMUiState

data class AddOperationPopUpGlobalState(
    val isPopUpExpanded: Boolean = false,
    val operationCategories: List<PresentationDataModel> = listOf(),
    val selectedCategory: PresentationDataModel = PresentationDataModel(),
    val operationName: PresentationDataModel = PresentationDataModel(),
    val operationAmount: PresentationDataModel = PresentationDataModel(),
    val isPaymentExpanded: Boolean = false,
    val isRecurrentOptionExpanded: Boolean = false,
    val enDateSelectedMonth: String = "",
    val endDateSelectedYear: String = "",
    val isTransferExpanded: Boolean = false,
    val accountList: List<PresentationDataModel> = listOf(),
    val senderAccount: String = "",
    val beneficiaryAccount: String = ""
    ) : PAMUiState
