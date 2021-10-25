package com.piconemarc.model.entity

import java.util.*

open class OperationUiModel(
    override val id : Long = 0,
    open val accountId : Long = 0,
    override val name : String = "",
    open val amount : Double = 0.0,
    open val categoryId : Long = 1,
    open val emitDate : Date = Calendar.getInstance().time,
    open var isAddOperation : Boolean = false,
    var paymentId : Long? = null
): BaseUiModel(){
    fun deleteOperation() : OperationUiModel{
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
}

