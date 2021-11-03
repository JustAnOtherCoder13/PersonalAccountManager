package com.piconemarc.model.entity

import java.util.*

data class PaymentUiModel(
    override val id: Long = 0,
    override val name: String = "",
    val operationId: Long? = 0,
    override val amount : Double = 0.0,
    val accountId: Long = 0,
    val endDate: Date? = null,
):BaseOperation()