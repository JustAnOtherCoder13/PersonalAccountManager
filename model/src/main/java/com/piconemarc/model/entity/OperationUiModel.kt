package com.piconemarc.model.entity

import java.util.*

open class OperationUiModel(
    override val id : Long = 0,
    open val accountId : Long = 0,
    override val name : String = "",
    open val amount : Double = 0.0,
    open val categoryId : Long = 1,
    open val emitDate : Date = Calendar.getInstance().time,
    open var isAddOperation : Boolean = false
): BaseUiModel(){

    open fun updateAccountBalance(account : AccountUiModel) : Double{
      return  account.accountBalance.plus(if (isAddOperation)amount else amount*-1)
    }

}


data class EndDate(
    val month : String? = null,
    val year : String? = null
)
