package com.piconemarc.model.entity

import java.util.*

open class PaymentUiModel(
    override val id: Long = 0,
    override val name: String = "",
    val operationId : Long = 0,
    override val accountId: Long = 0,
    open val endDate: Date? = null,
) : OperationUiModel() {

}