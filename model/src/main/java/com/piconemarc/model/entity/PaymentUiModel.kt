package com.piconemarc.model.entity

import java.util.*

open class PaymentUiModel(
    override val id: Long = 0,
    override val name: String = "",
    override val accountId: Long = 0,
    override val amount: Double = 0.0,
    override val categoryId: Long = 0,
    override val emitDate: Date = Calendar.getInstance().time,
    override var isAddOperation: Boolean = true,
    open val endDate: Date? = Date(),
) : OperationUiModel() {

}