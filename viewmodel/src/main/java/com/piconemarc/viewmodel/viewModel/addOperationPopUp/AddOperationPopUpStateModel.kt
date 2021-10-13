package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import com.piconemarc.core.domain.Constants.OPERATION_MODEL
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.viewModel.UiState


data class AddOperationPopUpGlobalState(
    val isPopUpExpanded: Boolean = false,
    val isPaymentExpanded: Boolean = false,
    val isRecurrentOptionExpanded: Boolean = false,
    val isTransferExpanded: Boolean = false,
    val allCategories: List<PresentationDataModel> = listOf(),
    val allAccounts: List<PresentationDataModel> = listOf(),
    val selectableEndDateYears : List<PresentationDataModel> = listOf(),
    val selectableEndDateMonths : List<PresentationDataModel> = listOf(),
    val selectedCategory: PresentationDataModel = PresentationDataModel(),
    val operationName: PresentationDataModel = PresentationDataModel(),
    val operationAmount: PresentationDataModel = PresentationDataModel(),
    val enDateSelectedMonth: PresentationDataModel = PresentationDataModel(),
    val endDateSelectedYear: PresentationDataModel = PresentationDataModel(),
    val senderAccount: PresentationDataModel = PresentationDataModel(),
    val beneficiaryAccount: PresentationDataModel = PresentationDataModel(),
    val addPopUpTitle : PresentationDataModel =  OPERATION_MODEL
    ) : UiState
