package com.piconemarc.model.entity

import java.util.*

data class OperationModel(
    override val id : Long = 0,
    override val name : String = "",
    var amount_ : Double = 0.0,
    val endDate : EndDate? = EndDate(),
    val isRecurrent : Boolean = false,
    val accountId : Long = 0,
    val categoryId : Long = 1,
    val emitDate : Date = Calendar.getInstance().time,
    val senderAccountId : Long? = null,
    val beneficiaryAccountId : Long? = null,
    val distantOperationIdRef : Long? = null,
    var isAddOperation : Boolean = false
): BaseUiModel(){
    val updatedAmount = if (isAddOperation)amount_ else amount_*-1
}


data class EndDate(
    val month : String? = null,
    val year : String? = null
)
