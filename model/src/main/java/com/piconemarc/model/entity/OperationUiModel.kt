package com.piconemarc.model.entity

import java.util.*

data class OperationUiModel(
    override val id: Long = 0,
    val accountId: Long = 0,
    override val name: String = "",
    var amount: Double = 0.0,
    val categoryId: Long = 1,
    val emitDate: Date = Calendar.getInstance().time,
    var isAddOperation: Boolean = true,
    val paymentId: Long? = null,
    val transferId: Long? = null
) : BaseUiModel() {

    fun deleteOperation(): OperationUiModel {
        return OperationUiModel(
            id = this.id,
            accountId = this.accountId,
            name = this.name,
            categoryId = this.categoryId,
            emitDate = this.emitDate,
            isAddOperation = this.isAddOperation,
            amount = this.amount * -1
        )
    }

    val beneficiaryAmount = amount
    val senderAmount = amount * -1
}

