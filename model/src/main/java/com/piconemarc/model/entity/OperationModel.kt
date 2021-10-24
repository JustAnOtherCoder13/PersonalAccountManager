package com.piconemarc.model.entity

import java.util.*

data class OperationModel(
    override val id : Long = 0,
    override val name : String = "",
    val amount : Double = 0.0,
    val endDate : EndDate? = EndDate(),
    val isRecurrent : Boolean = false,
    val accountId : Long = 0,
    val categoryId : Long = 1,
    val emitDate : Date = Calendar.getInstance().time,
    val senderAccountId : Long? = null,
    val beneficiaryAccountId : Long? = null,
    val distantOperationIdRef : Long? = null
): BaseUiModel()
data class EndDate(
    val month : String? = null,
    val year : String? = null
)
