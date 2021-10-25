package com.piconemarc.model.entity

import java.util.*

data class TransferUiModel(
    val id: Long = 0,
    val name: String = "",
    val accountId: Long = 0,
    val amount: Double = 0.0,
    val categoryId: Long = 0,
    val emitDate: Date = Calendar.getInstance().time,
    val endDate: Date? = Date(),
    val distantAccountId: Long = 0,
    val distantOperationId: Long = 0,
    val isRecurrent: Boolean = false,
) {

    val senderAccountOperation: OperationUiModel = OperationUiModel(
        name = this.name,
        accountId = this.accountId,
        amount = this.amount,
        categoryId = this.categoryId,
        emitDate = this.emitDate,
        isAddOperation = false
    )
    val beneficiaryAccountOperation: OperationUiModel = OperationUiModel(
        name = this.name,
        accountId = this.distantAccountId,
        amount = this.amount,
        categoryId = this.categoryId,
        emitDate = this.emitDate,
        isAddOperation = true
    )
}